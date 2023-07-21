package project.controller;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
import java.util.List;
import java.util.UUID;

@Controller
public class NewsController {
    private String uploadPath = "/Users/Anastassia/IdeaProjects/Kino-CMS_admin/uploads";
    private final NewsService newsService;
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    private Integer n = 5;
    private String l = "news";
    private Logger logger = LogManager.getLogger("serviceLogger");
    @GetMapping("/news")
    public String getUsersList(Model model){
        model.addAttribute("newsList", newsService.getAllNews());
        logger.info("Got all news");
        model.addAttribute("pagen", n);
        return "newsPage/news";
    }

    @GetMapping("/news/edit/{id}")
    public String editUser(@PathVariable Long id, Model model){
        String ln = "news";
        model.addAttribute("object",newsService.getNewById(id));
        logger.info("Got news by id "+id+" for editing");
        model.addAttribute("lin",l);
        model.addAttribute("pageNm", n);
        return "newsPage/edit_news";
    }
    @PostMapping("/news/{id}")
    public String editNews(@PathVariable("id") Long id,@Valid @ModelAttribute("object") News news, BindingResult bindingResult,
                           @RequestParam("mainImage")MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                           @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                           @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                           @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                           @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                           @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                           Model model) throws IOException {
        News newsInDB = newsService.getNewById(id);
        news.setImageGallery(newsInDB.getImageGallery());
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageNm", n);
            return "newsPage/edit_news";
        }
        //News newsInDB = newsService.getNewById(id);
        logger.info(mainImageName);
        editImage(mainImage,"mainImage", newsInDB, mainImageName);
        editImage(image1,"image1", newsInDB, image1Name);
        editImage(image2,"image2", newsInDB, image2Name);
        editImage(image3,"image3", newsInDB, image3Name);
        editImage(image4,"image4", newsInDB, image4Name);
        editImage(image5,"image5", newsInDB, image5Name);

        newsInDB.setName(news.getName());
        newsInDB.setDescription(news.getDescription());
        newsInDB.setVideoLink(news.getVideoLink());
        newsInDB.setPublicationDate(news.getPublicationDate());
        newsInDB.getSeoBlock().setUrl(news.getSeoBlock().getUrl());
        newsInDB.getSeoBlock().setTitle(news.getSeoBlock().getTitle());
        newsInDB.getSeoBlock().setKeywords(news.getSeoBlock().getKeywords());
        newsInDB.getSeoBlock().setDescription(news.getSeoBlock().getDescription());
        newsService.updateNews(newsInDB);
        return "redirect:/news";
    }


    @GetMapping("/news/new")
    public String createNews(Model model){
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
        logger.info("file name: |"+image.getOriginalFilename()+"|");
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

    private void editImage(MultipartFile image,String fileName, News news, String name){

        switch(fileName){
            case "mainImage":
                if(!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    news.getImageGallery().setMainImage(uniqueName);
                    logger.info("Added mainImage to gallery. Image name: " + uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                } else if(image.getOriginalFilename().equals("") && name.equals("")){
                    news.getImageGallery().setMainImage(null);
                }
                break;
            case "image1":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    news.getImageGallery().setImage1(uniqueName);
                    logger.info("Added image1 to gallery. Image name: " + uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    news.getImageGallery().setImage1(null);
                }
                break;
            case "image2":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    news.getImageGallery().setImage2(uniqueName);
                    logger.info("Added image2 to gallery. Image name: " + uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    news.getImageGallery().setImage2(null);
                }
                break;
            case "image3":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    news.getImageGallery().setImage3(uniqueName);
                    logger.info("Added image3 to gallery. Image name: " + uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    news.getImageGallery().setImage3(null);
                }
                break;
            case "image4":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    news.getImageGallery().setImage4(uniqueName);
                    logger.info("Added image4 to gallery. Image name: " + uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    news.getImageGallery().setImage4(null);
                }
                break;
            case "image5":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    news.getImageGallery().setImage5(uniqueName);
                    logger.info("Added image5 to gallery. Image name: " + uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
                else if(image.getOriginalFilename().equals("") && name.equals("")){
                    news.getImageGallery().setImage5(null);
                }
                break;
        }
    }
}
