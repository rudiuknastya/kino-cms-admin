package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.service.StatisticService;

import java.util.List;

@Controller
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    private Integer n = 1;
    @GetMapping("/admin/statistics")
    public String statistic(Model model) {
        model.addAttribute("pagenmb", n);
        model.addAttribute("users", statisticService.usersCount());
        model.addAttribute("releasedFilms", statisticService.filmsCount());
        model.addAttribute("cinemas", statisticService.cinemasCount());
        model.addAttribute("filmSessionsInMonth", statisticService.getFilmSessionsInMonth());
        model.addAttribute("filmSessionsPriceInMonth", statisticService.getFilmSessionsPriceInMonth());
        model.addAttribute("usersLanguage", statisticService.getUsersLanguage());
        return "statistic/statistics";
    }
}
