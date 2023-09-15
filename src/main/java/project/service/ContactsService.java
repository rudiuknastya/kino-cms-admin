package project.service;

import project.entity.Contact;

import java.util.List;

public interface ContactsService {
    List<Contact> getAllContacts();
    List<Contact> getEnabledContacts();
    Contact saveContact(Contact contacts);
    Contact getContact();
    void deleteContactById(Long id);
    Long getContactsCount();
}
