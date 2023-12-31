package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.entity.AboutCinema;
import project.service.*;

@Controller
public class PublicAboutCinemaController {
    private final AboutCinemaService aboutCinemaService;
    private final MainPageService mainPageService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicAboutCinemaController(AboutCinemaService aboutCinemaService, MainPageService mainPageService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.aboutCinemaService = aboutCinemaService;
        this.mainPageService = mainPageService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    private Integer n = 7;
    @GetMapping("/about_cinema")
    public String showAboutCinema(Model model){
        AboutCinema aboutCinema = aboutCinemaService.getAboutCinema();
        if(aboutCinema.getStatus() == true) {
            model.addAttribute("object", aboutCinema);
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
        } else {
            return "redirect:/";
        }
    }
}
