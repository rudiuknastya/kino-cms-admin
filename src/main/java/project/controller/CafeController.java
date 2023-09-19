package project.controller;

import jakarta.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.entity.Cafe;
import project.entity.Gallery;
import project.entity.SeoBlock;
import project.service.CafeService;
import project.service.MainPageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class CafeController {
    private final CafeService cafeService;
    public CafeController(CafeService cafeService, MainPageService mainPageService) {
        this.cafeService = cafeService;
    }
    @Value("${upload.path}")
    private String uploadPath;
    private Integer n = 7;

    @GetMapping("/admin/pages/edit/cafe")
    public String editCafePage(Model model){
        String l = "edit/cafe";
        model.addAttribute("object",cafeService.getCafe());
        model.addAttribute("pageNum", n);
        model.addAttribute("link",l);
        return "page/common_page";
    }

    @PostMapping("/admin/pages/edit/cafe")
    public String updateCafePage(@Valid @ModelAttribute("object") Cafe cafe, BindingResult bindingResult,
                                        @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                                        @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                                        @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                                        @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                                        @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                                        @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                                        Model model) {
        Cafe cafeInDb = cafeService.getCafe();
        if(isSupportedExtension(FilenameUtils.getExtension(
                mainImage.getOriginalFilename()))) {
            saveImage(mainImage, "mainImage", cafeInDb, mainImageName);
        } else if(!mainImage.getOriginalFilename().equals("")){
            model.addAttribute("mainWarning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image1.getOriginalFilename()))) {
            saveImage(image1, "image1", cafeInDb, image1Name);
        } else if(!image1.getOriginalFilename().equals("")){
            model.addAttribute("image1Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image2.getOriginalFilename()))) {
            saveImage(image2, "image2", cafeInDb, image2Name);
        } else if(!image2.getOriginalFilename().equals("")){
            model.addAttribute("image2Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image3.getOriginalFilename()))) {
            saveImage(image3, "image3", cafeInDb, image3Name);
        } else if(!image3.getOriginalFilename().equals("")){
            model.addAttribute("image3Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image4.getOriginalFilename()))) {
            saveImage(image4, "image4", cafeInDb, image4Name);
        } else if(!image4.getOriginalFilename().equals("")){
            model.addAttribute("image4Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image5.getOriginalFilename()))) {
            saveImage(image5, "image5", cafeInDb, image5Name);
        } else if(!image5.getOriginalFilename().equals("")){
            model.addAttribute("image5Warning", "Некоректний тип файлу");
        }
        cafe.setImageGallery(cafeInDb.getImageGallery());
        if (bindingResult.hasErrors()) {
            String l = "edit/cafe";
            model.addAttribute("object",cafe);
            model.addAttribute("pageNum", n);
            model.addAttribute("link",l);
            return "page/common_page";
        }
        if((!mainImage.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(mainImage.getOriginalFilename()))) ||
                (!image1.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image1.getOriginalFilename()))) ||
                (!image2.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image2.getOriginalFilename()))) ||
                (!image3.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image3.getOriginalFilename()))) ||
                (!image4.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image4.getOriginalFilename()))) ||
                (!image5.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image5.getOriginalFilename())))
        ){
            String l = "edit/cafe";
            model.addAttribute("object",cafe);
            model.addAttribute("pageNum", n);
            model.addAttribute("link",l);
            return "page/common_page";
        }
        cafeInDb.setStatus(cafe.getStatus());
        cafeInDb.setName(cafe.getName());
        cafeInDb.setDescription(cafe.getDescription());
        cafeInDb.getSeoBlock().setUrl(cafe.getSeoBlock().getUrl());
        cafeInDb.getSeoBlock().setTitle(cafe.getSeoBlock().getTitle());
        cafeInDb.getSeoBlock().setKeywords(cafe.getSeoBlock().getKeywords());
        cafeInDb.getSeoBlock().setDescription(cafe.getSeoBlock().getDescription());
        cafeService.saveCafe(cafeInDb);
        return "redirect:/admin/pages";
    }

    private boolean isSupportedExtension(String extension) {
        return extension != null && (
                extension.equals("png")
                        || extension.equals("jpg")
                        || extension.equals("jpeg"));
    }
    private void saveImage(MultipartFile image, String fileName, Cafe cafe, String name){

        switch(fileName){
            case "mainImage":
                if(!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cafe.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ cafe.getImageGallery().getMainImage());
                    file.delete();
                    cafe.getImageGallery().setMainImage(null);
                }
                else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cafe.getImageGallery().setMainImage(uniqueName);
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
                    cafe.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ cafe.getImageGallery().getImage1());
                    file.delete();
                    cafe.getImageGallery().setImage1(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cafe.getImageGallery().setImage1(uniqueName);
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
                    cafe.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ cafe.getImageGallery().getImage2());
                    file.delete();
                    cafe.getImageGallery().setImage2(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cafe.getImageGallery().setImage2(uniqueName);
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
                    cafe.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ cafe.getImageGallery().getImage3());
                    file.delete();
                    cafe.getImageGallery().setImage3(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cafe.getImageGallery().setImage3(uniqueName);
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
                    cafe.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ cafe.getImageGallery().getImage4());
                    file.delete();
                    cafe.getImageGallery().setImage4(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cafe.getImageGallery().setImage4(uniqueName);
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
                    cafe.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }
                else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ cafe.getImageGallery().getImage5());
                    file.delete();
                    cafe.getImageGallery().setImage5(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cafe.getImageGallery().setImage5(uniqueName);
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
