package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.entity.NewPage;
import project.service.*;

@Controller
public class PublicNewPageController {
    private final MainPageService mainPageService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicNewPageController(MainPageService mainPageService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.mainPageService = mainPageService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    private Integer n = 7;
    @GetMapping("/newPage/{id}")
    public String showNewPage(@PathVariable("id") Long id, Model model){
        NewPage newPage = newPageService.getNewPageById(id);
        if(newPage.getStatus() == true) {
            model.addAttribute("object", newPage);
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
