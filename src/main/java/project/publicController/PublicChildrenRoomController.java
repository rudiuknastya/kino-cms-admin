package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.entity.ChildrenRoom;
import project.service.*;

@Controller
public class PublicChildrenRoomController {
    private final ChildrenRoomService childrenRoomService;
    private final MainPageService mainPageService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicChildrenRoomController(ChildrenRoomService childrenRoomService, MainPageService mainPageService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, NewPageService newPageService, ContactsService contactsService) {
        this.childrenRoomService = childrenRoomService;
        this.mainPageService = mainPageService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    private Integer n = 7;
    @GetMapping("/children_room")
    public String showChildrenRoom(Model model){
        ChildrenRoom childrenRoom = childrenRoomService.getChildrenRoom();
        if(childrenRoom.getStatus() == true) {
            model.addAttribute("object", childrenRoom);
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
