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
import project.entity.ChildrenRoom;
import project.entity.Gallery;
import project.entity.SeoBlock;
import project.service.ChildrenRoomService;
import project.service.MainPageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class ChildrenRoomController {
    private final ChildrenRoomService childrenRoomService;

    public ChildrenRoomController(ChildrenRoomService childrenRoomService) {
        this.childrenRoomService = childrenRoomService;
    }

    private String uploadPath = "/Users/Anastassia/IdeaProjects/Kino-CMS_admin/uploads";
    private Integer n = 7;

    @GetMapping("/admin/pages/edit/children_room")
    public String editChildrenRoomPage(Model model){
        String l = "edit/children_room";
        model.addAttribute("object",childrenRoomService.getChildrenRoom());
        model.addAttribute("pageNum", n);
        model.addAttribute("link",l);
        return "page/common_page";
    }
    @PostMapping("/admin/pages/edit/children_room")
    public String updateChildrenRoomPage(@Valid @ModelAttribute("object") ChildrenRoom childrenRoom, BindingResult bindingResult,
                                        @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                                        @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                                        @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                                        @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                                        @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                                        @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                                        Model model) {
        ChildrenRoom childrenRoomInDb = childrenRoomService.getChildrenRoom();
        saveImage(mainImage,"mainImage", childrenRoomInDb, mainImageName);
        saveImage(image1,"image1", childrenRoomInDb, image1Name);
        saveImage(image2,"image2", childrenRoomInDb, image2Name);
        saveImage(image3,"image3", childrenRoomInDb, image3Name);
        saveImage(image4,"image4", childrenRoomInDb, image4Name);
        saveImage(image5,"image5", childrenRoomInDb, image5Name);
        childrenRoom.setImageGallery(childrenRoomInDb.getImageGallery());
        if (bindingResult.hasErrors()) {
            String l = "edit/children_room";
            model.addAttribute("object", childrenRoom);
            model.addAttribute("pageNum", n);
            model.addAttribute("link",l);
            return "page/common_page";
        }
        childrenRoomInDb.setStatus(childrenRoom.getStatus());
        childrenRoomInDb.setName(childrenRoom.getName());
        childrenRoomInDb.setDescription(childrenRoom.getDescription());
        childrenRoomInDb.getSeoBlock().setUrl(childrenRoom.getSeoBlock().getUrl());
        childrenRoomInDb.getSeoBlock().setTitle(childrenRoom.getSeoBlock().getTitle());
        childrenRoomInDb.getSeoBlock().setKeywords(childrenRoom.getSeoBlock().getKeywords());
        childrenRoomInDb.getSeoBlock().setDescription(childrenRoom.getSeoBlock().getDescription());
        childrenRoomService.saveChildrenRoom(childrenRoomInDb);
        return "redirect:/admin/pages";
    }
    private void saveImage(MultipartFile image, String fileName, ChildrenRoom childrenRoom, String name){

        switch(fileName){
            case "mainImage":
                if(!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    childrenRoom.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ childrenRoom.getImageGallery().getMainImage());
                    file.delete();
                    childrenRoom.getImageGallery().setMainImage(null);
                }
                else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    childrenRoom.getImageGallery().setMainImage(uniqueName);
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
                    childrenRoom.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ childrenRoom.getImageGallery().getImage1());
                    file.delete();
                    childrenRoom.getImageGallery().setImage1(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    childrenRoom.getImageGallery().setImage1(uniqueName);
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
                    childrenRoom.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ childrenRoom.getImageGallery().getImage2());
                    file.delete();
                    childrenRoom.getImageGallery().setImage2(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    childrenRoom.getImageGallery().setImage2(uniqueName);
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
                    childrenRoom.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ childrenRoom.getImageGallery().getImage3());
                    file.delete();
                    childrenRoom.getImageGallery().setImage3(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    childrenRoom.getImageGallery().setImage3(uniqueName);
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
                    childrenRoom.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ childrenRoom.getImageGallery().getImage4());
                    file.delete();
                    childrenRoom.getImageGallery().setImage4(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    childrenRoom.getImageGallery().setImage4(uniqueName);
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
                    childrenRoom.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }
                else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+ childrenRoom.getImageGallery().getImage5());
                    file.delete();
                    childrenRoom.getImageGallery().setImage5(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    childrenRoom.getImageGallery().setImage5(uniqueName);
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
