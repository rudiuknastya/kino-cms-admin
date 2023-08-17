package project.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.entity.*;
import project.service.FilmService;
import project.service.FilmSessionService;
import project.service.HallService;
import project.service.MainPageService;

@Controller
public class FilmSessionController {
    private final FilmSessionService filmSessionService;
    private final FilmService filmService;
    private final HallService hallService;
    private final MainPageService mainPageService;

    public FilmSessionController(FilmSessionService filmSessionService, FilmService filmService, HallService hallService, MainPageService mainPageService) {
        this.filmSessionService = filmSessionService;
        this.filmService = filmService;
        this.hallService = hallService;
        this.mainPageService = mainPageService;
    }

    private Integer n = 10;
    @GetMapping("/admin/sessions")
    public String getFilmSessionList(Model model){
        model.addAttribute("sessions", filmSessionService.getAllFilmSessions());
        model.addAttribute("pageNumb", n);
        return "filmSession/sessions";
    }
    @GetMapping("/admin/sessions/delete/{id}")
    public String deleteFilmSession(@PathVariable Long id){
        filmSessionService.deleteFilmSessionById(id);
        return "redirect:/admin/sessions";
    }
    @GetMapping("/admin/sessions/new")
    public String createFilmSession(Model model){
        FilmSession filmSession = new FilmSession();
        filmSession.setFilm(new Film());
        filmSession.setHall(new Hall());
        String l ="sessions/new";
        model.addAttribute("films", filmService.getAllFilms());
        model.addAttribute("halls", hallService.getAllHalls());
        model.addAttribute("object", filmSession);
        model.addAttribute("link", l);
        model.addAttribute("pageNumb", n);
        return "filmSession/film_session";
    }
    @PostMapping("/admin/sessions/new")
    public String saveFilmSession(@Valid @ModelAttribute("object") FilmSession filmSession, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            String l ="sessions/new";
            model.addAttribute("link", l);
            model.addAttribute("pageNumb", n);
            return "filmSession/film_session";
        }
        System.out.println(filmSession.getFilm().getId());
        System.out.println(filmSession.getHall().getId());
        System.out.println(filmSession.getSessionTime());
        Hall hall = hallService.getHallById(filmSession.getHall().getId());
        filmSession.setHall(hall);
        Film film = filmService.getFilmById(filmSession.getFilm().getId());
        filmSession.setFilm(film);
        filmSessionService.saveFilmSession(filmSession);
        return "redirect:/admin/sessions";
    }

    @GetMapping("/admin/sessions/edit/{id}")
    public String editFilmSession(@PathVariable("id") Long id,Model model){
        FilmSession filmSession = filmSessionService.getFilmSessionById(id);
        String l ="sessions/edit/"+id;
        model.addAttribute("films", filmService.getAllFilms());
        model.addAttribute("halls", hallService.getAllHalls());
        model.addAttribute("object", filmSession);
        model.addAttribute("link", l);
        model.addAttribute("pageNumb", n);
        return "filmSession/film_session";
    }
    @PostMapping("/admin/sessions/edit/{id}")
    public String updateFilmSession(@PathVariable("id") Long id,@Valid @ModelAttribute("object") FilmSession filmSession, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            String l ="sessions/edit/"+id;
            model.addAttribute("link", l);
            model.addAttribute("pageNumb", n);
            return "filmSession/film_session";
        }
        FilmSession filmSessionInDb = filmSessionService.getFilmSessionById(id);
        Hall hall = hallService.getHallById(filmSession.getHall().getId());
        filmSessionInDb.setHall(hall);
        Film film = filmService.getFilmById(filmSession.getFilm().getId());
        filmSessionInDb.setFilm(film);
        filmSessionInDb.setSessionDate(filmSession.getSessionDate());
        filmSessionInDb.setSessionTime(filmSession.getSessionTime());
        filmSessionInDb.setPrice(filmSession.getPrice());
        filmSessionService.saveFilmSession(filmSessionInDb);
        return "redirect:/admin/sessions";
    }
    @GetMapping("/film_session/{id}")
    public String getFilmSession(@PathVariable("id") Long id,Model model){
        model.addAttribute("filmSession", filmSessionService.getFilmSessionById(id));
        model.addAttribute("pageNumb", n);
        model.addAttribute("mainPage",mainPageService.getMainPage());
        return "filmSession/public_film_session";
    }



}
