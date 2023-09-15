package project.publicController;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.entity.Share;
import project.service.*;

@Controller
public class PublicShareController {
    private final ShareService shareService;
    private final MainPageService mainPageService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicShareController(ShareService shareService, MainPageService mainPageService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.shareService = shareService;
        this.mainPageService = mainPageService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    private Integer n = 6;

    @GetMapping("/shares/{pageNumber}")
    public String getShares(@PathVariable int pageNumber,Model model){
        int pageSize = 2;
        String link = "shares/share";
        String pageLink = "shares";
        Page<Share> page = shareService.getSharesWithPagination(pageNumber,pageSize);
        System.out.println(page);
        model.addAttribute("shares", page.getContent());
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("currentPage",pageNumber);
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("pagenm", n);
        model.addAttribute("link",link);
        model.addAttribute("pageLink",pageLink);
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "share/public_shares";
    }
    @GetMapping("/shares/share/{id}")
    public String showShare(@PathVariable Long id, Model model){
        model.addAttribute("share", shareService.getShareById(id));
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("pagenm", n);
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "share/public_share";
    }
}
