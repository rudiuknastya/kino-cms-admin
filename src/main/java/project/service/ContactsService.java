package project.service;

import project.entity.Contacts;

import java.util.List;

public interface ContactsService {
    List<Contacts> getAllContacts();
    Contacts saveContact(Contacts contacts);
    Contacts getContact();
    void deleteContactById(Long id);
}
