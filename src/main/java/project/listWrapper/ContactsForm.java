package project.listWrapper;

import jakarta.validation.Valid;
import project.entity.Contact;

import java.util.List;

public class ContactsForm {
    @Valid
    private List<Contact> contactsList;

    public List<Contact> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<Contact> contactsList) {
        this.contactsList = contactsList;
    }
}
