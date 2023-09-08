package project.service;

import project.entity.Contact;

import java.util.List;

public interface ContactsService {
    List<Contact> getAllContacts();
    Contact saveContact(Contact contacts);
    Contact getContact();
    void deleteContactById(Long id);
}
