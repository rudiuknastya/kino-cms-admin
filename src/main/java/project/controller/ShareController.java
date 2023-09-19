package project.controller;

import jakarta.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.entity.Gallery;
import project.entity.Share;
import project.service.BannerService;
import project.service.CinemaService;
import project.service.MainPageService;
import project.service.ShareService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class ShareController {
    @Value("${upload.path}")
    private String uploadPath;
    private final ShareService shareService;
    private final CinemaService cinemaService;

    public ShareController(ShareService shareService, CinemaService cinemaService) {
        this.shareService = shareService;
        this.cinemaService = cinemaService;
    }

    private Integer n = 6;

    @GetMapping("/admin/shares")
    public String getSharesList(Model model){
        model.addAttribute("shares", shareService.getAllShares());
        model.addAttribute("pagenm", n);
        return "share/shares";
    }
    @GetMapping("/admin/shares/delete/{id}")
    public String deleteShare(@PathVariable Long id){
        Share share = shareService.getShareById(id);
        deleteImages(share);
        shareService.deleteShareById(id);
        return "redirect:/admin/shares";
    }
    @GetMapping("/admin/shares/new")
    public String createShare(Model model){
        Share share = new Share();
        share.setImageGallery(new Gallery());
        String l ="shares/new";
        model.addAttribute("object", share);
        model.addAttribute("lin", l);
        model.addAttribute("pagenm", n);
        model.addAttribute("keyCinemas",cinemaService.getAllCinemas());
        return "share/add_share";
    }

    @PostMapping("/admin/shares/new")
    public String saveShare(@Valid @ModelAttribute("object") Share share, BindingResult bindingResult,
                            @RequestParam("mainImage")MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                            @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                            @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                            @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                            @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                            @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                            @RequestParam(name="cinemas", required = false) String [] cinemas, Model model) throws IOException {
        share.setImageGallery(new Gallery());
        if(isSupportedExtension(FilenameUtils.getExtension(
                mainImage.getOriginalFilename()))) {
            saveImage(mainImage, "mainImage", share, mainImageName);
        } else if(!mainImage.getOriginalFilename().equals("")){
            model.addAttribute("mainWarning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image1.getOriginalFilename()))) {
            saveImage(image1, "image1", share, image1Name);
        } else if(!image1.getOriginalFilename().equals("")){
            model.addAttribute("image1Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image2.getOriginalFilename()))) {
            saveImage(image2, "image2", share, image2Name);
        } else if(!image2.getOriginalFilename().equals("")){
            model.addAttribute("image2Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image3.getOriginalFilename()))) {
            saveImage(image3, "image3", share, image3Name);
        } else if(!image3.getOriginalFilename().equals("")){
            model.addAttribute("image3Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image4.getOriginalFilename()))) {
            saveImage(image4, "image4", share, image4Name);
        } else if(!image4.getOriginalFilename().equals("")){
            model.addAttribute("image4Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image5.getOriginalFilename()))) {
            saveImage(image5, "image5", share, image5Name);
        } else if(!image5.getOriginalFilename().equals("")){
            model.addAttribute("image5Warning", "Некоректний тип файлу");
        }
        if(cinemas != null) {
            String keywordCinemas = "";
            for (String c : cinemas) {
                keywordCinemas += c + ",";
            }
            share.setCinemas(keywordCinemas);
        }
        if (bindingResult.hasErrors()) {
            String l ="shares/new";
            model.addAttribute("object", share);
            model.addAttribute("pagenm", n);
            model.addAttribute("lin", l);
            model.addAttribute("keyCinemas",cinemaService.getAllCinemas());
            return "share/add_share";
        }
        if((!mainImage.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(mainImage.getOriginalFilename()))) ||
                (!image1.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image1.getOriginalFilename()))) ||
                (!image2.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image2.getOriginalFilename()))) ||
                (!image3.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image3.getOriginalFilename()))) ||
                (!image4.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image4.getOriginalFilename()))) ||
                (!image5.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image5.getOriginalFilename())))
        ){
            String l ="shares/new";
            model.addAttribute("object", share);
            model.addAttribute("pagenm", n);
            model.addAttribute("lin", l);
            model.addAttribute("keyCinemas",cinemaService.getAllCinemas());
            return "share/add_share";
        }
        share.setVideoLink(share.getVideoLink().substring(share.getVideoLink().lastIndexOf("=") + 1));
        shareService.saveShare(share);
        return "redirect:/admin/shares";
    }

    @GetMapping("/admin/shares/edit/{id}")
    public String editShare(@PathVariable Long id, Model model){
        model.addAttribute("object",shareService.getShareById(id));
        String l ="shares/"+id;
        model.addAttribute("lin",l);
        model.addAttribute("pagenm", n);
        model.addAttribute("keyCinemas",cinemaService.getAllCinemas());
        return "share/edit_share";
    }
    @PostMapping("/admin/shares/{id}")
    public String updateShare(@PathVariable("id") Long id,@Valid @ModelAttribute("object") Share share, BindingResult bindingResult,
                             @RequestParam("mainImage")MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                             @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                             @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                             @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                             @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                             @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                              @RequestParam(name="cinemas", required = false) String [] cinemas, Model model) throws IOException {

        Share shareInDB = shareService.getShareById(id);
        if(isSupportedExtension(FilenameUtils.getExtension(
                mainImage.getOriginalFilename()))) {
            saveImage(mainImage, "mainImage", shareInDB, mainImageName);
        } else if(!mainImage.getOriginalFilename().equals("")){
            model.addAttribute("mainWarning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image1.getOriginalFilename()))) {
            saveImage(image1, "image1", shareInDB, image1Name);
        } else if(!image1.getOriginalFilename().equals("")){
            model.addAttribute("image1Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image2.getOriginalFilename()))) {
            saveImage(image2, "image2", shareInDB, image2Name);
        }else if(!image2.getOriginalFilename().equals("")){
            model.addAttribute("image2Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image3.getOriginalFilename()))) {
            saveImage(image3, "image3", shareInDB, image3Name);
        } else if(!image3.getOriginalFilename().equals("")){
            model.addAttribute("image3Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image4.getOriginalFilename()))) {
            saveImage(image4, "image4", shareInDB, image4Name);
        } else if(!image4.getOriginalFilename().equals("")){
            model.addAttribute("image4Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image5.getOriginalFilename()))) {
            saveImage(image5, "image5", shareInDB, image5Name);
        } else if(!image5.getOriginalFilename().equals("")){
            model.addAttribute("image5Warning", "Некоректний тип файлу");
        }
        share.setImageGallery(shareInDB.getImageGallery());
        if(cinemas != null) {
            String keywordCinemas = "";
            for (String c : cinemas) {
                keywordCinemas += c + ",";
            }
            share.setCinemas(keywordCinemas);
        } else {
            share.setCinemas(null);
        }
        if (bindingResult.hasErrors()) {
            String l ="shares/"+id;
            model.addAttribute("object", share);
            model.addAttribute("pagenm", n);
            model.addAttribute("lin",l);
            model.addAttribute("keyCinemas",cinemaService.getAllCinemas());
            return "share/edit_share";
        }
        if((!mainImage.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(mainImage.getOriginalFilename()))) ||
                (!image1.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image1.getOriginalFilename()))) ||
                (!image2.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image2.getOriginalFilename()))) ||
                (!image3.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image3.getOriginalFilename()))) ||
                (!image4.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image4.getOriginalFilename()))) ||
                (!image5.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image5.getOriginalFilename())))
        ){
            String l ="shares/"+id;
            model.addAttribute("object", share);
            model.addAttribute("pagenm", n);
            model.addAttribute("lin",l);
            model.addAttribute("keyCinemas",cinemaService.getAllCinemas());
            return "share/edit_share";
        }
        shareInDB.setCinemas(share.getCinemas());
        shareInDB.setStatus(share.getStatus());
        shareInDB.setName(share.getName());
        shareInDB.setDescription(share.getDescription());
        share.setVideoLink(share.getVideoLink().substring(share.getVideoLink().lastIndexOf("=") + 1));
        shareInDB.setVideoLink(share.getVideoLink());
        shareInDB.setPublicationDate(share.getPublicationDate());
        shareInDB.getSeoBlock().setUrl(share.getSeoBlock().getUrl());
        shareInDB.getSeoBlock().setTitle(share.getSeoBlock().getTitle());
        shareInDB.getSeoBlock().setKeywords(share.getSeoBlock().getKeywords());
        shareInDB.getSeoBlock().setDescription(share.getSeoBlock().getDescription());
        shareService.saveShare(shareInDB);
        return "redirect:/admin/shares";
    }

    private boolean isSupportedExtension(String extension) {
        return extension != null && (
                extension.equals("png")
                        || extension.equals("jpg")
                        || extension.equals("jpeg"));
    }
    private void saveImage(MultipartFile image,String fileName, Share share, String name){

        switch(fileName){
            case "mainImage":
                if(!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    share.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+share.getImageGallery().getMainImage());
                    file.delete();
                    share.getImageGallery().setMainImage(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    share.getImageGallery().setMainImage(uniqueName);
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
                    share.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+share.getImageGallery().getImage1());
                    file.delete();
                    share.getImageGallery().setImage1(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    share.getImageGallery().setImage1(uniqueName);
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
                    share.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+share.getImageGallery().getImage2());
                    file.delete();
                    share.getImageGallery().setImage2(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    share.getImageGallery().setImage2(uniqueName);
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
                    share.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+share.getImageGallery().getImage3());
                    file.delete();
                    share.getImageGallery().setImage3(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    share.getImageGallery().setImage3(uniqueName);
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
                    share.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+share.getImageGallery().getImage4());
                    file.delete();
                    share.getImageGallery().setImage4(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    share.getImageGallery().setImage4(uniqueName);
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
                    share.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }
                else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+share.getImageGallery().getImage5());
                    file.delete();
                    share.getImageGallery().setImage5(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    share.getImageGallery().setImage5(uniqueName);
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
    private void deleteImages(Share share){
        if(share.getImageGallery().getMainImage() != null){
            File file = new File(uploadPath+"/"+ share.getImageGallery().getMainImage());
            file.delete();
        }
        if(share.getImageGallery().getImage1() != null){
            File file = new File(uploadPath+"/"+ share.getImageGallery().getImage1());
            file.delete();
        }
        if(share.getImageGallery().getImage2() != null){
            File file = new File(uploadPath+"/"+ share.getImageGallery().getImage2());
            file.delete();
        }
        if(share.getImageGallery().getImage3() != null){
            File file = new File(uploadPath+"/"+ share.getImageGallery().getImage3());
            file.delete();
        }
        if(share.getImageGallery().getImage4() != null){
            File file = new File(uploadPath+"/"+ share.getImageGallery().getImage4());
            file.delete();
        }
        if(share.getImageGallery().getImage5() != null){
            File file = new File(uploadPath+"/"+ share.getImageGallery().getImage5());
            file.delete();
        }
    }
}

