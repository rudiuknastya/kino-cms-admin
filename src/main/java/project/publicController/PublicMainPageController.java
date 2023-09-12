package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.entity.*;
import project.service.*;

import java.time.LocalDate;

@Controller
public class PublicMainPageController {
    private final MainPageService mainPageService;
    private final BannerService bannerService;
    private final FilmService filmService;
    private final FilmSessionService filmSessionService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicMainPageController(MainPageService mainPageService, BannerService bannerService, FilmService filmService, FilmSessionService filmSessionService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.mainPageService = mainPageService;
        this.bannerService = bannerService;
        this.filmService = filmService;
        this.filmSessionService = filmSessionService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    @GetMapping("/main_page")
    public String getMainPage(Model model){
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("mainBanners", bannerService.getAllMainBanners());
        model.addAttribute("newsBanners", bannerService.getAllNewsBanners());
        model.addAttribute("todayFilms", filmSessionService.getFilmSessionsForToday());
        model.addAttribute("soonFilms", filmService.getSoonFilms());
        model.addAttribute("backgroundImage",bannerService.getBackgroundImage());
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "mainPage/main_page";
    }
}
