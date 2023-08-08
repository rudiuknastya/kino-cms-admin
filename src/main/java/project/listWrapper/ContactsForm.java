package project.listWrapper;

import jakarta.validation.Valid;
import project.entity.Contacts;

import java.util.List;

public class ContactsForm {
    @Valid
    private List<Contacts> contactsList;

    public List<Contacts> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }
}
