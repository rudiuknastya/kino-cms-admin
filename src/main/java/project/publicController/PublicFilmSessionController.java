package project.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.service.*;

import java.time.LocalDate;

@Controller
public class PublicFilmSessionController {
    private final FilmSessionService filmSessionService;
    private final FilmService filmService;
    private final HallService hallService;
    private final MainPageService mainPageService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;

    public PublicFilmSessionController(FilmSessionService filmSessionService, FilmService filmService, HallService hallService, MainPageService mainPageService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.filmSessionService = filmSessionService;
        this.filmService = filmService;
        this.hallService = hallService;
        this.mainPageService = mainPageService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    private Integer n = 10;
    @GetMapping("/poster")
    public String showPoster(Model model){
        Integer num = 1;
        model.addAttribute("posterFilms", filmSessionService.getFilmSessionsForToday());
        model.addAttribute("pagenumb", 5);
        model.addAttribute("title", "Сьогодні в прокаті");
        model.addAttribute("number", num);
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "posterPage/poster_page";
    }

    @GetMapping("/film_session/{id}")
    public String getFilmSession(@PathVariable("id") Long id, Model model){
        model.addAttribute("filmSession", filmSessionService.getFilmSessionById(id));
        model.addAttribute("pageNumb", n);
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "filmSession/public_film_session";
    }
    @GetMapping("/film_sessions")
    public String showFilmSessions(Model model){
        model.addAttribute("todaySessions", filmSessionService.getFilmSessionsForToday());
        model.addAttribute("tomorrowSessions",filmSessionService.getFilmSessionsForTomorrow());
        model.addAttribute("pageNumb", n);
        model.addAttribute("films", filmService.getAllFilms());
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("todayDate", LocalDate.now());
        model.addAttribute("tomorrowDate",LocalDate.now().plusDays(1));
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "filmSession/public_sessions";
    }

    @RequestMapping("/filter")
    public String getSessionsByCriteria(@RequestParam(name="check",required = false) String[] checks,
                                        @RequestParam(name="selectFilm",required = false) String film,
                                        @RequestParam(name="sessionDate",required = false) LocalDate sessionDate,
                                        Model model){
        System.out.println(film);
        if(sessionDate ==  null) {
            model.addAttribute("todaySessions", filmSessionService.getFilmSessionsByCriteriaForToday(checks, film));
            model.addAttribute("tomorrowSessions", filmSessionService.getFilmSessionsByCriteriaForTomorrow(checks, film));
            model.addAttribute("todayDate",LocalDate.now());
            model.addAttribute("tomorrowDate",LocalDate.now().plusDays(1));
        } else {
            model.addAttribute("todayDate",sessionDate);
            model.addAttribute("todaySessions", filmSessionService.getFilmSessionsByCriteriaForDate(checks, film, sessionDate));
        }
        model.addAttribute("pageNumb", n);
        model.addAttribute("films", filmService.getAllFilms());
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("sessionDate",sessionDate);
        model.addAttribute("film",film);
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        int i = 0;
        if(checks != null) {
            model.addAttribute("check3D", "3D");
            i++;
            if(i < checks.length) {
                model.addAttribute("check2D", "2D");
            }
            i++;
            if(i < checks.length) {
                model.addAttribute("checkImax", "IMAX");
            }
        }
        return "filmSession/public_sessions";
    }
}
