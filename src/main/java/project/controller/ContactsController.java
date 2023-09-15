package project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.entity.Contact;
import project.listWrapper.ContactsForm;
import project.service.BannerService;
import project.service.ContactsService;
import project.service.MainPageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class ContactsController {
    private final ContactsService contactsService;
    private final MainPageService mainPageService;
    private final BannerService bannerService;
    private boolean update = true;

    public ContactsController(ContactsService contactsService, MainPageService mainPageService, BannerService bannerService) {
        this.contactsService = contactsService;
        this.mainPageService = mainPageService;
        this.bannerService = bannerService;
    }
    @Value("${upload.path}")
    private String uploadPath;
    private Integer n = 7;
    private List<Contact> contacts;

    @GetMapping("/admin/pages/edit/contacts/delete/{id}")
    public String deleteContact(@PathVariable Long id,  Model model) {
        Long c = contactsService.getContactsCount();
        if(c < contacts.size()){
            contacts.remove(Math.toIntExact(id));
        } else{
            contactsService.deleteContactById(contacts.get(Math.toIntExact(id)).getId());
        }
            return "redirect:/admin/pages/edit/contacts";
    }

    @GetMapping("/admin/pages/edit/contacts/new")
    public String createContacts(Model model) {
        //ContactsForm contactsForm = new ContactsForm();
        contacts.add(new Contact());
//        contactsForm.setContactsList(contacts);
//        model.addAttribute("contacts", contactsForm);
//        model.addAttribute("pagenuM", n);
        update = false;
        return "redirect:/admin/pages/edit/contacts";
    }

    @GetMapping("/admin/pages/edit/contacts")
    public String editContacts(Model model) {
        ContactsForm contactsForm = new ContactsForm();
        if(update == true) {
            contacts = contactsService.getAllContacts();
        }
        update = true;
        contactsForm.setContactsList(contacts);
        model.addAttribute("contacts", contactsForm);
        model.addAttribute("pagenuM", n);
        return "page/contacts_page";
    }

    @PostMapping("/admin/pages/edit/contacts")
    public String updateContacts(@Valid @ModelAttribute("contacts") ContactsForm contactsForm, BindingResult bindingResult,
                                 @RequestParam("logoImage") MultipartFile[] logoImages, Model model) {
        List<Contact> contactsInDb = contactsService.getAllContacts();
        saveImages(contactsInDb, contactsForm.getContactsList(), logoImages);
        System.out.println(logoImages.length);
        System.out.println(logoImages[0].getOriginalFilename());

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors().get(0));
            model.addAttribute("pagenuM", n);
            model.addAttribute("contacts", contactsForm);
            return "page/contacts_page";
        }
        int i = 0;
        for(Contact contact: contactsForm.getContactsList()){
            if(i<contactsInDb.size()) {
                if (i == 0) {
                    contactsInDb.get(i).setPageStatus(contact.getPageStatus());
                    contactsInDb.get(i).getSeoBlock().setUrl(contact.getSeoBlock().getUrl());
                    contactsInDb.get(i).getSeoBlock().setTitle(contact.getSeoBlock().getTitle());
                    contactsInDb.get(i).getSeoBlock().setKeywords(contact.getSeoBlock().getKeywords());
                    contactsInDb.get(i).getSeoBlock().setDescription(contact.getSeoBlock().getDescription());
                }
                contactsInDb.get(i).setStatus(contact.getStatus());
                contactsInDb.get(i).setLogo(contact.getLogo());
                contactsInDb.get(i).setCinemaName(contact.getCinemaName());
                contactsInDb.get(i).setAddress(contact.getAddress());
                contactsInDb.get(i).setCoordinates(contact.getCoordinates());
                contactsService.saveContact(contactsInDb.get(i));
            } else{
                contactsService.saveContact(contact);
            }
            i++;
        }

        return "redirect:/admin/pages";
    }

    private void saveImages(List<Contact> contactsInDb, List<Contact> contactsList, MultipartFile[] logoImages) {
        //int i=0;
        for (int i=0; i<logoImages.length; i++) {
            if(i<contactsInDb.size() && !logoImages[i].getOriginalFilename().equals("")) {
                System.out.println("1 if here i: "+i);
                String uuidFile = UUID.randomUUID().toString();
                String uniqueName = uuidFile + "." + logoImages[i].getOriginalFilename();
                contactsList.get(i).setLogo(uniqueName);
                Path path = Paths.get(uploadPath + "/" + uniqueName);
                try {
                    logoImages[i].transferTo(new File(path.toUri()));
                } catch (IOException e) {
                }
                File file = new File(uploadPath+"/"+contactsInDb.get(i).getLogo());
                if(file.exists()) {
                    file.delete();
                }
            } else if (i >= contactsInDb.size() && !logoImages[i].getOriginalFilename().equals("")){
                System.out.println("2 if here i: "+i);
                String uuidFile = UUID.randomUUID().toString();
                String uniqueName = uuidFile + "." + logoImages[i].getOriginalFilename();
                contactsList.get(i).setLogo(uniqueName);
                Path path = Paths.get(uploadPath + "/" + uniqueName);
                try {
                    logoImages[i].transferTo(new File(path.toUri()));
                } catch (IOException e) {
                }
            }
        }

    }
}
