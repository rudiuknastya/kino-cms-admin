package project.controller;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.entity.Gallery;
import project.entity.News;
import project.entity.SeoBlock;
import project.entity.User;
import project.service.NewsService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class NewsController {
    private String uploadPath = "/Users/Anastassia/IdeaProjects/Kino-CMS_admin/uploads";
    private final NewsService newsService;
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    private Integer n = 5;
    private Logger logger = LogManager.getLogger("serviceLogger");
    @GetMapping("/news")
    public String getUsersList(Model model){
        model.addAttribute("newsList", newsService.getAllNews());
        logger.info("Got all news");
        model.addAttribute("pagen", n);
        return "newsPage/news";
    }
    @GetMapping("/news/new")
    public String createNews(Model model){
        String l = "news";
        News news = new News();
        logger.info("Created new empty news");
        model.addAttribute("object", news);
        model.addAttribute("lin", l);
        model.addAttribute("pageN", n);
        return "newsPage/add_news";
    }
    @PostMapping("/news/new")
    public String saveNews(@Valid @ModelAttribute("object") News news, BindingResult bindingResult,
                           @RequestParam("mainImage")MultipartFile mainImage, @RequestParam("image1")MultipartFile image1,
                           @RequestParam("image2")MultipartFile image2,@RequestParam("image3")MultipartFile image3,
                           @RequestParam("image4")MultipartFile image4,@RequestParam("image5")MultipartFile image5, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageN", n);
            return "newsPage/add_news";
        }
        news.setImageGallery(new Gallery());
        logger.info("Created new empty gallery");
        addImage(mainImage,"mainImage", news);
        addImage(image1,"image1", news);
        addImage(image2,"image2", news);
        addImage(image3,"image3", news);
        addImage(image4,"image4", news);
        addImage(image5,"image5", news);
        newsService.saveNews(news);
        return "redirect:/news";
    }

    private void addImage(MultipartFile image,String fileName, News news) {
        if(image != null && !image.getOriginalFilename().equals("")){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String uniqueName = uuidFile+"."+image.getOriginalFilename();
            switch(fileName){
                case "mainImage":
                    news.getImageGallery().setMainImage(uniqueName);
                    logger.info("Added mainImage to gallery. Image name: "+uniqueName);
                    break;
                case "image1":
                    news.getImageGallery().setImage1(uniqueName);
                    logger.info("Added image1 to gallery. Image name: "+uniqueName);
                    break;
                case "image2":
                    news.getImageGallery().setImage2(uniqueName);
                    logger.info("Added image2 to gallery. Image name: "+uniqueName);
                    break;
                case "image3":
                    news.getImageGallery().setImage3(uniqueName);
                    logger.info("Added image3 to gallery. Image name: "+uniqueName);
                    break;
                case "image4":
                    news.getImageGallery().setImage4(uniqueName);
                    logger.info("Added image4 to gallery. Image name: "+uniqueName);
                    break;
                case "image5":
                    news.getImageGallery().setImage5(uniqueName);
                    logger.info("Added image5 to gallery. Image name: "+uniqueName);
                    break;
            }
            Path path = Paths.get(uploadPath+"/"+uniqueName);

            try {
                image.transferTo(new File(path.toUri()));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
