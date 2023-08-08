package project.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.entity.Gallery;
import project.entity.NewPage;
import project.service.NewPageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class NewPageController {
    private final NewPageService newPageService;

    public NewPageController(NewPageService newPageService) {
        this.newPageService = newPageService;
    }

    private String uploadPath = "/Users/Anastassia/IdeaProjects/Kino-CMS_admin/uploads";
    private Integer n = 7;
    @GetMapping("/admin/pages/new_page/delete/{id}")
    public String deleteNewPage(@PathVariable Long id){
        NewPage newPage = newPageService.getNewPageById(id);
        deleteImages(newPage);
        newPageService.deleteNewPageById(id);
        return "redirect:/admin/pages";
    }
    @GetMapping("/admin/pages/new/new_page")
    public String createNewPage(Model model){
        NewPage newPage = new NewPage();
        String l ="new/new_page";
        model.addAttribute("object", newPage);
        model.addAttribute("link", l);
        model.addAttribute("pageNum", n);
        return "page/common_page";
    }
    @PostMapping("/admin/pages/new/new_page")
    public String saveNewPage(@Valid @ModelAttribute("object") NewPage newPage, BindingResult bindingResult,
                           @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                           @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                           @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                           @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                           @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                           @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                           Model model) throws IOException {
        newPage.setImageGallery(new Gallery());
        saveImage(mainImage,"mainImage", newPage, mainImageName);
        saveImage(image1,"image1", newPage, image1Name);
        saveImage(image2,"image2", newPage, image2Name);
        saveImage(image3,"image3", newPage, image3Name);
        saveImage(image4,"image4", newPage, image4Name);
        saveImage(image5,"image5", newPage, image5Name);
        if (bindingResult.hasErrors()) {
            String l ="new/new_page";
            model.addAttribute("pageNum", n);
            model.addAttribute("link", l);
            model.addAttribute("object", newPage);
            return "page/common_page";
        }
        newPage.setCreationDate(LocalDate.now());
        newPageService.saveNewPage(newPage);
        return "redirect:/admin/pages";
    }

    @GetMapping("/admin/pages/edit/new_page/{id}")
    public String editNewPage(@PathVariable Long id,Model model){
        String l = "edit/new_page/"+id;
        model.addAttribute("object",newPageService.getNewPageById(id));
        model.addAttribute("pageNum", n);
        model.addAttribute("link",l);
        return "page/common_page";
    }

    @PostMapping("/admin/pages/edit/new_page/{id}")
    public String updateNewPage(@PathVariable("id") Long id,@Valid @ModelAttribute("object") NewPage newPage, BindingResult bindingResult,
                             @RequestParam("mainImage")MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                             @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                             @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                             @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                             @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                             @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                             Model model) {
        NewPage newPageInDB = newPageService.getNewPageById(id);
        saveImage(mainImage,"mainImage", newPageInDB, mainImageName);
        saveImage(image1,"image1", newPageInDB, image1Name);
        saveImage(image2,"image2", newPageInDB, image2Name);
        saveImage(image3,"image3", newPageInDB, image3Name);
        saveImage(image4,"image4", newPageInDB, image4Name);
        saveImage(image5,"image5", newPageInDB, image5Name);
        newPage.setImageGallery(newPageInDB.getImageGallery());
        if (bindingResult.hasErrors()) {
            String l ="edit/new_page/"+id;
            model.addAttribute("object", newPage);
            model.addAttribute("pageNum", n);
            model.addAttribute("link",l);
            return "newsPage/edit_news";
        }
        newPageInDB.setStatus(newPage.getStatus());
        newPageInDB.setName(newPage.getName());
        newPageInDB.setDescription(newPage.getDescription());
        newPageInDB.getSeoBlock().setUrl(newPage.getSeoBlock().getUrl());
        newPageInDB.getSeoBlock().setTitle(newPage.getSeoBlock().getTitle());
        newPageInDB.getSeoBlock().setKeywords(newPage.getSeoBlock().getKeywords());
        newPageInDB.getSeoBlock().setDescription(newPage.getSeoBlock().getDescription());
        newPageService.saveNewPage(newPageInDB);
        return "redirect:/admin/pages";
    }
    private void saveImage(MultipartFile image, String fileName, NewPage newPage, String name) {

        switch (fileName) {
            case "mainImage":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + newPage.getImageGallery().getMainImage());
                    file.delete();
                    newPage.getImageGallery().setMainImage(null);
                } else if (!image.getOriginalFilename().equals(name) && !image.getOriginalFilename().equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath + "/" + name);
                    file.delete();
                }
                break;
            case "image1":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + newPage.getImageGallery().getImage1());
                    file.delete();
                    newPage.getImageGallery().setImage1(null);
                } else if (!image.getOriginalFilename().equals(name) && !image.getOriginalFilename().equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath + "/" + name);
                    file.delete();
                }
                break;
            case "image2":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + newPage.getImageGallery().getImage2());
                    file.delete();
                    newPage.getImageGallery().setImage2(null);
                } else if (!image.getOriginalFilename().equals(name) && !image.getOriginalFilename().equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath + "/" + name);
                    file.delete();
                }
                break;
            case "image3":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + newPage.getImageGallery().getImage3());
                    file.delete();
                    newPage.getImageGallery().setImage3(null);
                } else if (!image.getOriginalFilename().equals(name) && !image.getOriginalFilename().equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath + "/" + name);
                    file.delete();
                }
                break;
            case "image4":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + newPage.getImageGallery().getImage4());
                    file.delete();
                    newPage.getImageGallery().setImage4(null);
                } else if (!image.getOriginalFilename().equals(name) && !image.getOriginalFilename().equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath + "/" + name);
                    file.delete();
                }
                break;
            case "image5":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + newPage.getImageGallery().getImage5());
                    file.delete();
                    newPage.getImageGallery().setImage5(null);
                } else if (!image.getOriginalFilename().equals(name) && !image.getOriginalFilename().equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    newPage.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath + "/" + name);
                    file.delete();
                }
                break;
        }
    }
    private void deleteImages(NewPage newPage){
        if(newPage.getImageGallery().getMainImage() != null){
            File file = new File(uploadPath+"/"+ newPage.getImageGallery().getMainImage());
            file.delete();
        }
        if(newPage.getImageGallery().getImage1() != null){
            File file = new File(uploadPath+"/"+ newPage.getImageGallery().getImage1());
            file.delete();
        }
        if(newPage.getImageGallery().getImage2() != null){
            File file = new File(uploadPath+"/"+ newPage.getImageGallery().getImage2());
            file.delete();
        }
        if(newPage.getImageGallery().getImage3() != null){
            File file = new File(uploadPath+"/"+ newPage.getImageGallery().getImage3());
            file.delete();
        }
        if(newPage.getImageGallery().getImage4() != null){
            File file = new File(uploadPath+"/"+ newPage.getImageGallery().getImage4());
            file.delete();
        }
        if(newPage.getImageGallery().getImage5() != null){
            File file = new File(uploadPath+"/"+ newPage.getImageGallery().getImage5());
            file.delete();
        }
    }


}
