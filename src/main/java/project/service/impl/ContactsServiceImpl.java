package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.Contact;
import project.repository.ContactsRepository;
import project.service.ContactsService;

import java.util.List;
@Service
public class ContactsServiceImpl implements ContactsService {
    private final ContactsRepository contactsRepository;

    public ContactsServiceImpl(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");

    @Override
    public List<Contact> getAllContacts() {
        logger.info("getAllContacts() - Finding all contacts");
        List<Contact> contacts = contactsRepository.findAll();
        logger.info("getAllContacts() - All contacts were found");
        return contacts;
    }

    @Override
    public Contact saveContact(Contact contacts) {
        logger.info("saveContact() - Saving contact");
        Contact contact = contactsRepository.save(contacts);
        logger.info("saveContact() - Contact was saved");
        return contact;
    }

    @Override
    public Contact getContact() {
        logger.info("getContact() - getting contact");
        Contact contact = contactsRepository.findById(1L).get();
        logger.info("getContact() - Contact was got");
        return contact;
    }

    @Override
    public void deleteContactById(Long id) {
        logger.info("deleteContactById() - Deleting contact");
        contactsRepository.deleteById(id);
        logger.info("deleteContactById() - Contact was deleted");
    }

    @Override
    public Long getContactsCount() {
        logger.info("getContactsCount() - Finding contacts count");
        Long c = contactsRepository.count();
        logger.info("getContactsCount() - Contacts count was found");
        return c;
    }

    @Override
    public List<Contact> getEnabledContacts() {
        logger.info("getEnabledContacts() - Finding enabled contacts");
        List<Contact> contacts = contactsRepository.findByStatusNot(false);
        logger.info("getEnabledContacts() - Enabled contacts were found");
        return contacts;
    }
}
