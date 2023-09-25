package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.service.*;

@Controller
public class PublicFilmController {
    private final FilmService filmService;
    private final MainPageService mainPageService;
    private final FilmSessionService filmSessionService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicFilmController(FilmService filmService, MainPageService mainPageService, FilmSessionService filmSessionService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.filmService = filmService;
        this.mainPageService = mainPageService;
        this.filmSessionService = filmSessionService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    private Integer n = 3;
    @GetMapping("/film/{id}")
    public String showFilm(@PathVariable Long id, Model model){
        model.addAttribute("film", filmService.getFilmById(id));
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("filmSessions", filmSessionService.getFilmSessionsForFilm(id));
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "film/public_film";
    }

    @GetMapping("/soon")
    public String getSoonFilms(Model model){
        model.addAttribute("soonFilms", filmService.getSoonFilms());
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("pagenUm", n);
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "soonPage/soon_page";
    }
    @GetMapping("/poster_soon")
    public String getPosterSoonFilms(Model model){
        model.addAttribute("posterFilms", filmService.getSoonFilms());
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("pagenumb", 5);
        model.addAttribute("number", 2);
        model.addAttribute("title", "Скоро");
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "posterPage/poster_soon";
    }
}
