package project.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.entity.*;
import project.service.AboutCinemaService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class AboutCinemaController {
    private final AboutCinemaService aboutCinemaService;

    public AboutCinemaController(AboutCinemaService aboutCinemaService) {
        this.aboutCinemaService = aboutCinemaService;
    }
    private String uploadPath = "/Users/Anastassia/IdeaProjects/Kino-CMS_admin/uploads";
    private Integer n = 7;
    @GetMapping("/admin/pages/edit/about_cinema")
    public String editAboutCinemaPage(Model model){
        String l = "edit/about_cinema";
        model.addAttribute("object",aboutCinemaService.getAboutCinema());
        model.addAttribute("pageNum", n);
        model.addAttribute("link",l);
        return "page/common_page";
    }
    @PostMapping("/admin/pages/edit/about_cinema")
    public String updateAboutCinemaPage(@Valid @ModelAttribute("object") AboutCinema aboutCinema, BindingResult bindingResult,
                                        @RequestParam("mainImage")MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                                        @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                                        @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                                        @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                                        @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                                        @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                                        Model model) {
        AboutCinema aboutCinemaInDb = aboutCinemaService.getAboutCinema();
        saveImage(mainImage,"mainImage", aboutCinemaInDb, mainImageName);
        saveImage(image1,"image1", aboutCinemaInDb, image1Name);
        saveImage(image2,"image2", aboutCinemaInDb, image2Name);
        saveImage(image3,"image3", aboutCinemaInDb, image3Name);
        saveImage(image4,"image4", aboutCinemaInDb, image4Name);
        saveImage(image5,"image5", aboutCinemaInDb, image5Name);
        aboutCinema.setImageGallery(aboutCinemaInDb.getImageGallery());
        if (bindingResult.hasErrors()) {
            String l = "edit/about_cinema";
            model.addAttribute("object",aboutCinema);
            model.addAttribute("pageNum", n);
            model.addAttribute("link",l);
            return "page/common_page";
        }
        aboutCinemaInDb.setStatus(aboutCinema.getStatus());
        aboutCinemaInDb.setName(aboutCinema.getName());
        aboutCinemaInDb.setDescription(aboutCinema.getDescription());
        aboutCinemaInDb.getSeoBlock().setUrl(aboutCinema.getSeoBlock().getUrl());
        aboutCinemaInDb.getSeoBlock().setTitle(aboutCinema.getSeoBlock().getTitle());
        aboutCinemaInDb.getSeoBlock().setKeywords(aboutCinema.getSeoBlock().getKeywords());
        aboutCinemaInDb.getSeoBlock().setDescription(aboutCinema.getSeoBlock().getDescription());
        aboutCinemaService.saveAboutCinema(aboutCinemaInDb);
        return "redirect:/admin/pages";
    }
    private void saveImage(MultipartFile image, String fileName, AboutCinema aboutCinema, String name){

        switch(fileName){
            case "mainImage":
                if(!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    aboutCinema.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+aboutCinema.getImageGallery().getMainImage());
                    file.delete();
                    aboutCinema.getImageGallery().setMainImage(null);
                }
                else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    aboutCinema.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }
                break;
            case "image1":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    aboutCinema.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+aboutCinema.getImageGallery().getImage1());
                    file.delete();
                    aboutCinema.getImageGallery().setImage1(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    aboutCinema.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }
                break;
            case "image2":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    aboutCinema.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+aboutCinema.getImageGallery().getImage2());
                    file.delete();
                    aboutCinema.getImageGallery().setImage2(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    aboutCinema.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }
                break;
            case "image3":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    aboutCinema.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+aboutCinema.getImageGallery().getImage3());
                    file.delete();
                    aboutCinema.getImageGallery().setImage3(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    aboutCinema.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }
                break;
            case "image4":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    aboutCinema.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+aboutCinema.getImageGallery().getImage4());
                    file.delete();
                    aboutCinema.getImageGallery().setImage4(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    aboutCinema.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }
                break;
            case "image5":
                if(!image.getOriginalFilename().equals("")&& name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    aboutCinema.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }
                else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+aboutCinema.getImageGallery().getImage5());
                    file.delete();
                    aboutCinema.getImageGallery().setImage5(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    aboutCinema.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }
                break;
        }
    }
}
