package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.entity.Advertisement;
import project.service.*;

@Controller
public class PublicAdvertisementController {
    private final AdvertisementService advertisementService;
    private final MainPageService mainPageService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicAdvertisementController(AdvertisementService advertisementService, MainPageService mainPageService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.advertisementService = advertisementService;
        this.mainPageService = mainPageService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    private Integer n = 7;
    @GetMapping("/advertisement")
    public String showAd(Model model){
        Advertisement ad = advertisementService.getAd();
        if(ad.getStatus() == true) {
            model.addAttribute("object", ad);
            model.addAttribute("mainPage", mainPageService.getMainPage());
            model.addAttribute("pageNum", n);
            model.addAttribute("newPages", newPageService.getEnabledNewPages());
            model.addAttribute("aboutCinemaPage", aboutCinemaService.getAboutCinema());
            model.addAttribute("cafePage", cafeService.getCafe());
            model.addAttribute("vipHallPage", vipHallService.getVipHall());
            model.addAttribute("adPage", advertisementService.getAd());
            model.addAttribute("childrenRoomPage", childrenRoomService.getChildrenRoom());
            model.addAttribute("contactPage", contactsService.getContact());
            return "page/public_page";
        } else{
            return "redirect:/";
        }
    }
}
