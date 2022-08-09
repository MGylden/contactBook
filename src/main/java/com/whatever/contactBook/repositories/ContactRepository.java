package com.whatever.contactBook.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatever.contactBook.models.Contact;

@Repository
public interface ContactRepository extends JpaRepository <Contact, Long>{
	public Contact getContactBycontactEmail(String Email);

	
}
