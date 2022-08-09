package com.whatever.contactBook.services;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.whatever.contactBook.models.*;
import com.whatever.contactBook.repositories.*;

@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepository;

	public ResponseEntity<List<Contact>> getContacts() {
		List<Contact> contactList = null;
		HttpStatus httpStatus = null;

		try {
			contactList = contactRepository.findAll();

			if (contactList.isEmpty()) {
				httpStatus = HttpStatus.NOT_FOUND;
			} else {
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			System.out.println("The house is on fire...");
			System.out.println(e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(contactList, httpStatus);
	}

	public ResponseEntity<Contact> getContactById(Long id){
		Contact returnContact = null;
		HttpStatus httpStatus = null;
		try {
			if ((returnContact = contactRepository.getReferenceById(id)) != null) {
				httpStatus = HttpStatus.OK;
			}else {
				httpStatus = HttpStatus.NOT_FOUND;
			}	
		} catch (Exception e) {
			System.out.println("The house is on fire...");
			System.out.println(e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(returnContact, httpStatus);
	}
	
	
	public ResponseEntity<Contact> addContact(Contact newContact) {
		Contact returnContact = null;
		HttpStatus httpStatus = null;
		try {
			if ((returnContact = contactRepository.getContactBycontactEmail(newContact.getContactEmail())) == null) {
				returnContact = contactRepository.saveAndFlush(newContact);
				httpStatus = HttpStatus.OK;

			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			System.out.println("The house is on fire...");
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(returnContact, httpStatus);

	}

	public ResponseEntity<Contact> deleteContact(Long id) {
		Contact returnContact = null;
		HttpStatus httpStatus = null;
		try {
			if ((returnContact = contactRepository.getReferenceById(id)) != null) {
				contactRepository.deleteById(id);
				httpStatus = HttpStatus.OK;
			}else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			System.out.println("The house is on fire...");
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(returnContact, httpStatus);
	}

	public ResponseEntity<Contact> updateContact(Contact newContact, Long id) {
		Contact returnContact = null;
		HttpStatus httpStatus = null;
		try {
			if ((returnContact = contactRepository.getReferenceById(id)) != null) {
				newContact = (Contact) HelperService.partialUpdate(returnContact, newContact);
				returnContact = contactRepository.saveAndFlush(newContact);
				httpStatus = HttpStatus.OK;
			}else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (BeansException e) {
			System.out.printf("Failed to copy values into user object...\nPrinting message...");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("The house is on fire...");
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>(returnContact, httpStatus);
	}
}
