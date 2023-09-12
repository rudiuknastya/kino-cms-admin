package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.service.*;

@Controller
public class PublicCinemaController {
    private final MainPageService mainPageService;
    private final BannerService bannerService;
    private final FilmSessionService filmSessionService;
    private final HallService hallService;
    private final CinemaService cinemaService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicCinemaController(MainPageService mainPageService, BannerService bannerService, FilmSessionService filmSessionService, HallService hallService, CinemaService cinemaService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.mainPageService = mainPageService;
        this.bannerService = bannerService;
        this.filmSessionService = filmSessionService;
        this.hallService = hallService;
        this.cinemaService = cinemaService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    private Integer n = 4;
    @GetMapping("/cinemas")
    public String getCinemas(Model model) {
        model.addAttribute("cinemas", cinemaService.getAllCinemas());
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("backgroundImage",bannerService.getBackgroundImage());
        model.addAttribute("pageM", n);
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "cinema/public_cinemas";
    }

    @GetMapping("/cinemas/cinema/{id}")
    public String showCinema(@PathVariable Long id, Model model) {
        model.addAttribute("cinema", cinemaService.getCinemaById(id));
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("films",filmSessionService.getFilmsForTodayForCinema(id));
        model.addAttribute("pageM", n);
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "cinema/cinema_page";
    }
    @GetMapping("/cinemas/cinema/hall/{id}")
    public String showHall(@PathVariable Long id,Model model) {
        model.addAttribute("hall", hallService.getHallById(id));
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("pageM", n);
        model.addAttribute("films",filmSessionService.getFilmsForTodayForHall(id));
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "hall/hall_page";
    }
}
