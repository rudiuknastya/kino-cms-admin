package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.entity.Contact;
import project.service.*;

@Controller
public class PublicContactController {
    private final ContactsService contactsService;
    private final MainPageService mainPageService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;

    public PublicContactController(ContactsService contactsService, MainPageService mainPageService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService) {
        this.contactsService = contactsService;
        this.mainPageService = mainPageService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
    }

    @GetMapping("/contacts")
    public String showAllContacts(Model model){
        Contact contact = contactsService.getContact();
        if(contact.getPageStatus() == true) {
            model.addAttribute("contacts", contactsService.getEnabledContacts());
            model.addAttribute("mainPage", mainPageService.getMainPage());
            model.addAttribute("newPages", newPageService.getEnabledNewPages());
            model.addAttribute("aboutCinemaPage", aboutCinemaService.getAboutCinema());
            model.addAttribute("cafePage", cafeService.getCafe());
            model.addAttribute("vipHallPage", vipHallService.getVipHall());
            model.addAttribute("adPage", advertisementService.getAd());
            model.addAttribute("childrenRoomPage", childrenRoomService.getChildrenRoom());
            model.addAttribute("contactPage", contact);
            return "page/public_contacts";
        } else {
            return "redirect:/";
        }
    }
}
