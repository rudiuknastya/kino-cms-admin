package project.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.entity.News;
import project.entity.User;
import project.service.NewsService;

@Controller
public class NewsController {
    private final NewsService newsService;
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    @GetMapping("/news")
    public String getUsersList(Model model){
        Integer b = 5;
        model.addAttribute("news", newsService.getAllNews());
        model.addAttribute("pagen", b);
        return "news";
    }
}
