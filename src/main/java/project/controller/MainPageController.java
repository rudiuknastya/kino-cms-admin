package project.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.entity.MainPage;
import project.entity.News;
import project.service.BannerService;
import project.service.FilmService;
import project.service.FilmSessionService;
import project.service.MainPageService;

import java.io.IOException;

@Controller
public class MainPageController {
    private final MainPageService mainPageService;

    public MainPageController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    private Integer n = 7;
    @GetMapping("/admin/pages/edit/main")
    public String editMainPage(Model model){
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("pagenuM", n);
        return "page/main_page";
    }
    @PostMapping("/admin/pages/edit")
    public String updateMainPage( @Valid @ModelAttribute("mainPage") MainPage mainPage, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("pagenuM", n);
            return "page/main_page";
        }
        MainPage mainPageInDb = mainPageService.getMainPage();
        mainPageInDb.setStatus(mainPage.getStatus());
        mainPageInDb.setPhone1(mainPage.getPhone1());
        mainPageInDb.setPhone2(mainPage.getPhone2());
        mainPageInDb.setSeoText(mainPage.getSeoText());
        mainPageInDb.getSeoBlock().setUrl(mainPage.getSeoBlock().getUrl());
        mainPageInDb.getSeoBlock().setTitle(mainPage.getSeoBlock().getTitle());
        mainPageInDb.getSeoBlock().setKeywords(mainPage.getSeoBlock().getKeywords());
        mainPageInDb.getSeoBlock().setDescription(mainPage.getSeoBlock().getDescription());
        mainPageService.saveMainPage(mainPageInDb);
        return "redirect:/admin/pages";
    }


}
