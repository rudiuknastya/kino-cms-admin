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
import project.entity.Advertisement;
import project.entity.Gallery;
import project.entity.SeoBlock;
import project.service.AdvertisementService;
import project.service.MainPageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final MainPageService mainPageService;

    public AdvertisementController(AdvertisementService advertisementService, MainPageService mainPageService) {
        this.advertisementService = advertisementService;
        this.mainPageService = mainPageService;
    }

    private String uploadPath = "/Users/Anastassia/IdeaProjects/Kino-CMS_admin/uploads";
    private Integer n = 7;
    @GetMapping("/advertisement")
    public String showAd(Model model){
        model.addAttribute("object", advertisementService.getAd());
        model.addAttribute("mainPage",mainPageService.getMainPage());
        model.addAttribute("pageNum", n);
        return "page/public_page";
    }
    @GetMapping("/admin/pages/edit/advertisement")
    public String editAdPage(Model model){
        String l = "edit/advertisement";
        model.addAttribute("object",advertisementService.getAd());
        model.addAttribute("pageNum", n);
        model.addAttribute("link",l);
        return "page/common_page";
    }

    @PostMapping("/admin/pages/edit/advertisement")
    public String updateAdPage(@Valid @ModelAttribute("object") Advertisement advertisement, BindingResult bindingResult,
                                        @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                                        @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                                        @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                                        @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                                        @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                                        @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                                        Model model) {
        Advertisement adInDb = advertisementService.getAd();
        saveImage(mainImage,"mainImage", adInDb, mainImageName);
        saveImage(image1,"image1", adInDb, image1Name);
        saveImage(image2,"image2", adInDb, image2Name);
        saveImage(image3,"image3", adInDb, image3Name);
        saveImage(image4,"image4", adInDb, image4Name);
        saveImage(image5,"image5", adInDb, image5Name);
        advertisement.setImageGallery(adInDb.getImageGallery());
        if (bindingResult.hasErrors()) {
            String l = "edit/advertisement";
            model.addAttribute("object", advertisement);
            model.addAttribute("pageNum", n);
            model.addAttribute("link",l);
            return "page/common_page";
        }
        adInDb.setStatus(advertisement.getStatus());
        adInDb.setName(advertisement.getName());
        adInDb.setDescription(advertisement.getDescription());
        adInDb.getSeoBlock().setUrl(advertisement.getSeoBlock().getUrl());
        adInDb.getSeoBlock().setTitle(advertisement.getSeoBlock().getTitle());
        adInDb.getSeoBlock().setKeywords(advertisement.getSeoBlock().getKeywords());
        adInDb.getSeoBlock().setDescription(advertisement.getSeoBlock().getDescription());
        advertisementService.saveAd(adInDb);
        return "redirect:/admin/pages";
    }
    private void saveImage(MultipartFile image, String fileName, Advertisement advertisement, String name){

        switch(fileName){
            case "mainImage":
                if(!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    advertisement.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ advertisement.getImageGallery().getMainImage());
                    file.delete();
                    advertisement.getImageGallery().setMainImage(null);
                }
                else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    advertisement.getImageGallery().setMainImage(uniqueName);
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
                    advertisement.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ advertisement.getImageGallery().getImage1());
                    file.delete();
                    advertisement.getImageGallery().setImage1(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    advertisement.getImageGallery().setImage1(uniqueName);
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
                    advertisement.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ advertisement.getImageGallery().getImage2());
                    file.delete();
                    advertisement.getImageGallery().setImage2(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    advertisement.getImageGallery().setImage2(uniqueName);
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
                    advertisement.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ advertisement.getImageGallery().getImage3());
                    file.delete();
                    advertisement.getImageGallery().setImage3(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    advertisement.getImageGallery().setImage3(uniqueName);
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
                    advertisement.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ advertisement.getImageGallery().getImage4());
                    file.delete();
                    advertisement.getImageGallery().setImage4(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    advertisement.getImageGallery().setImage4(uniqueName);
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
                    advertisement.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }
                else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ advertisement.getImageGallery().getImage5());
                    file.delete();
                    advertisement.getImageGallery().setImage5(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    advertisement.getImageGallery().setImage5(uniqueName);
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
