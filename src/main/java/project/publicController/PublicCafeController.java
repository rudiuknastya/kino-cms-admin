package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.entity.Cafe;
import project.service.*;

@Controller
public class PublicCafeController {
    private final CafeService cafeService;
    private final MainPageService mainPageService;
    private final AboutCinemaService aboutCinemaService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicCafeController(CafeService cafeService, MainPageService mainPageService, AboutCinemaService aboutCinemaService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.cafeService = cafeService;
        this.mainPageService = mainPageService;
        this.aboutCinemaService = aboutCinemaService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    private Integer n = 7;

    @GetMapping("/cafe")
    public String showCafe(Model model){
        Cafe cafe = cafeService.getCafe();
        if(cafe.getStatus() == true) {
            model.addAttribute("object", cafe);
            model.addAttribute("mainPage", mainPageService.getMainPage());
            model.addAttribute("pageNum", n);
            model.addAttribute("newPages", newPageService.getEnabledNewPages());
            model.addAttribute("aboutCinemaPage", aboutCinemaService.getAboutCinema());
            model.addAttribute("cafePage", cafeService.getCafe());
            model.addAttribute("vipHallPage", vipHallService.getVipHall());
            model.addAttribute("adPage", advertisementService.getAd());
            model.addAttribute("childrenRoomPage", childrenRoomService.getChildrenRoom());
            model.addAttribute("contactPage", contactsService.getContact());
            return "page/cafe_page";
        } else {
            return "redirect:/";
        }
    }
}
