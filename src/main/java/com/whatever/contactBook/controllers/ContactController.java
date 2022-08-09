package com.whatever.contactBook.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.whatever.contactBook.models.Contact;
import com.whatever.contactBook.services.ContactService;

@Controller
@RequestMapping(value = "api/v1/contacts")
public class ContactController {
	@Autowired
	private ContactService contactService;

	@GetMapping
	public ResponseEntity<List<Contact>> getContacts() {
		return contactService.getContacts();
	}

	@PostMapping
	public ResponseEntity<Contact> createContact(@RequestBody Contact newContact){
		return contactService.addContact(newContact);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Contact> deleteContact(@PathVariable(value="id")Long id){
		return contactService.deleteContact(id);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Contact>updateContact(@PathVariable(value="id")Long id, @Validated @RequestBody Contact newContact){
		return contactService.updateContact(newContact, id);
	}
}
