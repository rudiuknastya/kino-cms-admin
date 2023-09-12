package project.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.entity.MainPage;
import project.entity.VipHall;
import project.service.*;

@Controller
public class PagesController {
    private final MainPageService mainPageService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final ContactsService contactsService;
    private final NewPageService newPageService;

    public PagesController(MainPageService mainPageService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, ContactsService contactsService, NewPageService newPageService) {
        this.mainPageService = mainPageService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.contactsService = contactsService;
        this.newPageService = newPageService;
    }

    private Integer n = 7;
    @GetMapping("/admin/pages")
    public String getAllPages(Model model){
        model.addAttribute("mainPage", mainPageService.getMainPage());
        model.addAttribute("aboutCinema", aboutCinemaService.getAboutCinema());
        model.addAttribute("cafe", cafeService.getCafe());
        model.addAttribute("vipHall", vipHallService.getVipHall());
        model.addAttribute("advertisement", advertisementService.getAd());
        model.addAttribute("childrenRoom", childrenRoomService.getChildrenRoom());
        model.addAttribute("contactsPage", contactsService.getContact());
        model.addAttribute("newPages", newPageService.getAllNewPages());
        model.addAttribute("pagenuM", n);
        return "page/pages";
    }

}
