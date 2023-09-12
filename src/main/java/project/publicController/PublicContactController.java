package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.service.*;

@Controller
public class PublicContactController {
    private final ContactsService contactsService;
    private final MainPageService mainPageService;
    private final BannerService bannerService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;

    public PublicContactController(ContactsService contactsService, MainPageService mainPageService, BannerService bannerService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService) {
        this.contactsService = contactsService;
        this.mainPageService = mainPageService;
        this.bannerService = bannerService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
    }

    @GetMapping("/contacts")
    public String showAllContacts(Model model){
        model.addAttribute("contacts", contactsService.getAllContacts());
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("backgroundImage",bannerService.getBackgroundImage());
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "page/public_contacts";
    }
}
