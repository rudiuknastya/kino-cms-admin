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
    //private String l = "news";
    @GetMapping("/admin/news")
    public String getNewsList(Model model){
        model.addAttribute("newsList", newsService.getAllNews());
        model.addAttribute("pagen", n);
        return "newsPage/news";
    }
    @GetMapping("/admin/news/delete/{id}")
    public String deleteNews(@PathVariable Long id){
        News news = newsService.getNewById(id);
        deleteImages(news);
        newsService.deleteNewsById(id);
        return "redirect:/admin/news";
    }

    @GetMapping("/admin/news/edit/{id}")
    public String editNews(@PathVariable Long id, Model model){
        String l ="news/"+id;
        model.addAttribute("object",newsService.getNewById(id));
        model.addAttribute("lin",l);
        model.addAttribute("pageNm", n);
        return "newsPage/edit_news";
    }
    @PostMapping("/admin/news/{id}")
    public String updateNews(@PathVariable("id") Long id,@Valid @ModelAttribute("object") News news, BindingResult bindingResult,
                           @RequestParam("mainImage")MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                           @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                           @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                           @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                           @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                           @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                           Model model) throws IOException {
        News newsInDB = newsService.getNewById(id);
        saveImage(mainImage,"mainImage", newsInDB, mainImageName);
        saveImage(image1,"image1", newsInDB, image1Name);
        saveImage(image2,"image2", newsInDB, image2Name);
        saveImage(image3,"image3", newsInDB, image3Name);
        saveImage(image4,"image4", newsInDB, image4Name);
        saveImage(image5,"image5", newsInDB, image5Name);
        news.setImageGallery(newsInDB.getImageGallery());
        if (bindingResult.hasErrors()) {
            String l ="news/"+id;
            model.addAttribute("object",news);
            model.addAttribute("pageNm", n);
            model.addAttribute("lin",l);
            return "newsPage/edit_news";
        }
        newsInDB.setName(news.getName());
        newsInDB.setDescription(news.getDescription());
        newsInDB.setVideoLink(news.getVideoLink());
        newsInDB.setPublicationDate(news.getPublicationDate());
        newsInDB.getSeoBlock().setUrl(news.getSeoBlock().getUrl());
        newsInDB.getSeoBlock().setTitle(news.getSeoBlock().getTitle());
        newsInDB.getSeoBlock().setKeywords(news.getSeoBlock().getKeywords());
        newsInDB.getSeoBlock().setDescription(news.getSeoBlock().getDescription());
        newsService.updateNews(newsInDB);
        return "redirect:/admin/news";
    }


    @GetMapping("/admin/news/new")
    public String createNews(Model model){
        News news = new News();
        news.setImageGallery(new Gallery());
        String l ="news/new";
        model.addAttribute("object", news);
        model.addAttribute("lin", l);
        model.addAttribute("pageN", n);
        return "newsPage/add_news";
    }
    @PostMapping("/admin/news/new")
    public String saveNews(@Valid @ModelAttribute("object") News news, BindingResult bindingResult,
                           @RequestParam("mainImage")MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                           @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                           @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                           @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                           @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                           @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                           Model model) throws IOException {
        news.setImageGallery(new Gallery());
        saveImage(mainImage,"mainImage", news, mainImageName);
        saveImage(image1,"image1", news, image1Name);
        saveImage(image2,"image2", news, image2Name);
        saveImage(image3,"image3", news, image3Name);
        saveImage(image4,"image4", news, image4Name);
        saveImage(image5,"image5", news, image5Name);
        if (bindingResult.hasErrors()) {
            String l ="news/new";
            model.addAttribute("pageN", n);
            model.addAttribute("lin", l);
            return "newsPage/add_news";
        }
        newsService.saveNews(news);
        return "redirect:/admin/news";
    }
    private void saveImage(MultipartFile image,String fileName, News news, String name){

        switch(fileName){
            case "mainImage":
                if(!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    news.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+news.getImageGallery().getMainImage());
                    file.delete();
                    news.getImageGallery().setMainImage(null);
                }
                break;
            case "image1":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    news.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+news.getImageGallery().getImage1());
                    file.delete();
                    news.getImageGallery().setImage1(null);
                }
                break;
            case "image2":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    news.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+news.getImageGallery().getImage2());
                    file.delete();
                    news.getImageGallery().setImage2(null);

                }
                break;
            case "image3":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    news.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+news.getImageGallery().getImage3());
                    file.delete();
                    news.getImageGallery().setImage3(null);
                }
                break;
            case "image4":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    news.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+news.getImageGallery().getImage4());
                    file.delete();
                    news.getImageGallery().setImage4(null);
                }
                break;
            case "image5":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    news.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }
                else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+news.getImageGallery().getImage5());
                    file.delete();
                    news.getImageGallery().setImage5(null);
                }
                break;
        }
    }
    private void deleteImages(News news){
        if(news.getImageGallery().getMainImage() != null){
            File file = new File(uploadPath+"/"+news.getImageGallery().getMainImage());
            file.delete();
        }
        if(news.getImageGallery().getImage1() != null){
            File file = new File(uploadPath+"/"+news.getImageGallery().getImage1());
            file.delete();
        }
        if(news.getImageGallery().getImage2() != null){
            File file = new File(uploadPath+"/"+news.getImageGallery().getImage2());
            file.delete();
        }
        if(news.getImageGallery().getImage3() != null){
            File file = new File(uploadPath+"/"+news.getImageGallery().getImage3());
            file.delete();
        }
        if(news.getImageGallery().getImage4() != null){
            File file = new File(uploadPath+"/"+news.getImageGallery().getImage4());
            file.delete();
        }
        if(news.getImageGallery().getImage5() != null){
            File file = new File(uploadPath+"/"+news.getImageGallery().getImage5());
            file.delete();
        }
    }
}
