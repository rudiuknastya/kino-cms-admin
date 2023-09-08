package project.publicController;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.entity.News;
import project.service.*;

@Controller
public class PublicNewsController {
    private final NewsService newsService;
    private final MainPageService mainPageService;
    private final BannerService bannerService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicNewsController(NewsService newsService, MainPageService mainPageService, BannerService bannerService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.newsService = newsService;
        this.mainPageService = mainPageService;
        this.bannerService = bannerService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    private Integer n = 5;
    @GetMapping("/news/{pageNumber}")
    public String showAllNews(@PathVariable int pageNumber,Model model){
        String link = "news/new";
        String pageLink = "news";
        int pageSize = 1;
        Page<News> page = newsService.getNewsWithPagination(pageNumber,pageSize);
        model.addAttribute("shares", page.getContent());
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("currentPage",pageNumber);
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("backgroundImage",bannerService.getBackgroundImage());
        model.addAttribute("pagenm", 0);
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
    @GetMapping("/news/new/{id}")
    public String getNews(@PathVariable Long id, Model model){
        model.addAttribute("share", newsService.getNewById(id));
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("backgroundImage",bannerService.getBackgroundImage());
        model.addAttribute("pagenm", 0);
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
