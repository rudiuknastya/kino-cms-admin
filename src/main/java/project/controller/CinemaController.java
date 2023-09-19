package project.controller;

import jakarta.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.entity.Cinema;
import project.entity.Gallery;
import project.entity.Hall;
import project.entity.News;
import project.service.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }
    @Value("${upload.path}")
    private String uploadPath;
    private Integer n = 4;
    private List<Hall> hallList = new ArrayList<>();
    private List<Hall> editHallList = new ArrayList<>();
    private List<Integer> hallsToDelete = new ArrayList<>();
    private Long cinemaId;
    private int count = 0;
    private boolean update = true;


    @GetMapping("/admin/cinemas")
    public String getCinemasList(Model model) {
        model.addAttribute("cinemas", cinemaService.getAllCinemas());
        model.addAttribute("pageM", n);
        update = true;
        return "cinema/cinemas";
    }

    @GetMapping("/admin/cinemas/new/hall/delete/{id}")
    public String deleteNewCinemaHall(@PathVariable Integer id) {
        Hall hall = hallList.get(id);
        deleteHallImages(hall);
        hallList.remove(hall);
        update = false;
        return "redirect:/admin/cinemas/new";
    }
    @GetMapping("/admin/cinemas/edit/hall/delete/{id}")
    public String deleteEditCinemaHall(@PathVariable Integer id) {
        Hall hall = editHallList.get(id);
        deleteHallImages(hall);
        hallsToDelete.add(id);
        //hall.setSchemaImage("-1");
        editHallList.remove(hall);
        update = false;
        return "redirect:/admin/cinemas/edit/" + cinemaId;
    }
    @GetMapping("/admin/cinemas/new/hall/new")
    public String createNewCinemaHall(Model model) {
        Hall hall = new Hall();
        String l = "hall/new";
        String r = "cinemas/new";
        model.addAttribute("object", hall);
        model.addAttribute("pageM", n);
        model.addAttribute("lin",l);
        model.addAttribute("redirect",r);
        return "hall/add_hall";
    }
    @GetMapping("/admin/cinemas/edit/hall/new")
    public String createEditCinemaHall(Model model) {
        Hall hall = new Hall();
        String l = "hall/new";
        String r = "cinemas/edit/"+cinemaId;
        model.addAttribute("object", hall);
        model.addAttribute("pageM", n);
        model.addAttribute("lin",l);
        model.addAttribute("redirect",r);
        return "hall/add_hall";
    }

    @PostMapping("/admin/hall/new")
    public String saveHall(@Valid @ModelAttribute("object") Hall hall, BindingResult bindingResult,
                           @RequestParam("schemaImg") MultipartFile schemaImg, @RequestParam("schemaImageName") String schemaImageName,
                           @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("mainImageName") String mainImageName,
                           @RequestParam("image1") MultipartFile image1, @RequestParam("image1Name") String image1Name,
                           @RequestParam("image2") MultipartFile image2, @RequestParam("image2Name") String image2Name,
                           @RequestParam("image3") MultipartFile image3, @RequestParam("image3Name") String image3Name,
                           @RequestParam("image4") MultipartFile image4, @RequestParam("image4Name") String image4Name,
                           @RequestParam("image5") MultipartFile image5, @RequestParam("image5Name") String image5Name,
                           @RequestParam("redirect") String redirect, Model model) {
        hall.setImageGallery(new Gallery());
        if(isSupportedExtension(FilenameUtils.getExtension(
                schemaImg.getOriginalFilename()))) {
            saveHallImage(schemaImg, "schemaImg", hall, schemaImageName);
        } else if(!schemaImg.getOriginalFilename().equals("")){
            model.addAttribute("schemaWarning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                mainImage.getOriginalFilename()))) {
            saveHallImage(mainImage, "mainImage", hall, mainImageName);
        } else if(!mainImage.getOriginalFilename().equals("")){
            model.addAttribute("mainWarning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image1.getOriginalFilename()))) {
            saveHallImage(image1, "image1", hall, image1Name);
        } else if(!image1.getOriginalFilename().equals("")){
            model.addAttribute("image1Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image2.getOriginalFilename()))) {
            saveHallImage(image2, "image2", hall, image2Name);
        } else if(!image2.getOriginalFilename().equals("")){
            model.addAttribute("image2Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image3.getOriginalFilename()))) {
            saveHallImage(image3, "image3", hall, image3Name);
        } else if(!image3.getOriginalFilename().equals("")){
            model.addAttribute("image3Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image4.getOriginalFilename()))) {
            saveHallImage(image4, "image4", hall, image4Name);
        } else if(!image4.getOriginalFilename().equals("")){
            model.addAttribute("image4Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image5.getOriginalFilename()))) {
            saveHallImage(image5, "image5", hall, image5Name);
        } else if(!image5.getOriginalFilename().equals("")){
            model.addAttribute("image5Warning", "Некоректний тип файлу");
        }
        if (bindingResult.hasErrors()) {
            String l = "hall/new";
            model.addAttribute("object", hall);
            model.addAttribute("lin",l);
            model.addAttribute("pageM", n);
            model.addAttribute("redirect", redirect);
            return "hall/add_hall";
        }
        if((!mainImage.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(mainImage.getOriginalFilename()))) ||
                (!image1.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image1.getOriginalFilename()))) ||
                (!image2.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image2.getOriginalFilename()))) ||
                (!image3.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image3.getOriginalFilename()))) ||
                (!image4.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image4.getOriginalFilename()))) ||
                (!image5.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image5.getOriginalFilename()))) ||
                (!schemaImg.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(schemaImg.getOriginalFilename())))
        ){
            String l = "hall/new";
            model.addAttribute("object", hall);
            model.addAttribute("lin",l);
            model.addAttribute("pageM", n);
            model.addAttribute("redirect", redirect);
            return "hall/add_hall";
        }

        hall.setCreationDate(LocalDate.now());
        if(redirect.contains("new")){
            hallList.add(hall);
            System.out.println("Added to hall list "+hallList.size());

        } else if(redirect.contains("edit")){
            editHallList.add(hall);
        }
        update = false;
        return "redirect:/admin/"+redirect;
    }

    @GetMapping("/admin/cinemas/new/hall/edit/{id}")
    public String editNewCinemaHall(@PathVariable Integer id,Model model) {
        String l = "hall/edit/"+id;
        String r = "cinemas/new";
        model.addAttribute("lin",l);
        model.addAttribute("redirect",r);
        model.addAttribute("object", hallList.get(id));
        model.addAttribute("pageM", n);
        return "hall/edit_hall";
    }

    @GetMapping("/admin/cinemas/edit/hall/edit/{id}")
    public String editEditCinemaHall(@PathVariable Integer id, Model model) {
        String l = "hall/edit/"+id;
        String r = "cinemas/edit/"+cinemaId;
        model.addAttribute("lin",l);
        model.addAttribute("redirect",r);
        model.addAttribute("object", editHallList.get(id));
        model.addAttribute("pageM", n);
        return "hall/edit_hall";
    }

    @PostMapping("/admin/hall/edit/{id}")
    public String updateHall(@PathVariable("id") Integer id, @Valid @ModelAttribute("object") Hall hall, BindingResult bindingResult,
                             @RequestParam("schemaImg") MultipartFile schemaImg, @RequestParam("schemaImageName") String schemaImageName,
                             @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("mainImageName") String mainImageName,
                             @RequestParam("image1") MultipartFile image1, @RequestParam("image1Name") String image1Name,
                             @RequestParam("image2") MultipartFile image2, @RequestParam("image2Name") String image2Name,
                             @RequestParam("image3") MultipartFile image3, @RequestParam("image3Name") String image3Name,
                             @RequestParam("image4") MultipartFile image4, @RequestParam("image4Name") String image4Name,
                             @RequestParam("image5") MultipartFile image5, @RequestParam("image5Name") String image5Name,
                             @RequestParam("redirect") String redirect, Model model) throws IOException {
        Hall hallInDB = null;
        if(redirect.contains("new")){
            hallInDB = hallList.get(id);
        } else if(redirect.contains("edit")){
            hallInDB = editHallList.get(id);
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                schemaImg.getOriginalFilename()))) {
            saveHallImage(schemaImg, "schemaImg", hallInDB, schemaImageName);
        } else if(!schemaImg.getOriginalFilename().equals("")){
            model.addAttribute("schemaWarning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image1.getOriginalFilename()))) {
            saveHallImage(mainImage, "mainImage", hallInDB, mainImageName);
        } else if(!image1.getOriginalFilename().equals("")){
            model.addAttribute("image1Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image2.getOriginalFilename()))) {
            saveHallImage(image1, "image1", hallInDB, image1Name);
        } else if(!image2.getOriginalFilename().equals("")){
            model.addAttribute("image2Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image3.getOriginalFilename()))) {
            saveHallImage(image2, "image2", hallInDB, image2Name);
        } else if(!image3.getOriginalFilename().equals("")){
            model.addAttribute("image3Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image4.getOriginalFilename()))) {
            saveHallImage(image3, "image3", hallInDB, image3Name);
        } else if(!image4.getOriginalFilename().equals("")){
            model.addAttribute("image4Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image4.getOriginalFilename()))) {
            saveHallImage(image4, "image4", hallInDB, image4Name);
        } else if(!image4.getOriginalFilename().equals("")){
            model.addAttribute("image4Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image5.getOriginalFilename()))) {
            saveHallImage(image5, "image5", hallInDB, image5Name);
        } else if(!image5.getOriginalFilename().equals("")){
            model.addAttribute("image5Warning", "Некоректний тип файлу");
        }
        hall.setImageGallery(hallInDB.getImageGallery());
        hall.setSchemaImage(hallInDB.getSchemaImage());
        if (bindingResult.hasErrors()) {
            String l = "hall/edit/"+id;
            model.addAttribute("lin",l);
            model.addAttribute("object", hall);
            model.addAttribute("pageM", n);
            model.addAttribute("redirect", redirect);
            return "hall/edit_hall";
        }
        if((!mainImage.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(mainImage.getOriginalFilename()))) ||
                (!image1.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image1.getOriginalFilename()))) ||
                (!image2.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image2.getOriginalFilename()))) ||
                (!image3.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image3.getOriginalFilename()))) ||
                (!image4.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image4.getOriginalFilename()))) ||
                (!image5.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image5.getOriginalFilename()))) ||
                (!schemaImg.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(schemaImg.getOriginalFilename())))
        ){
            String l = "hall/edit/"+id;
            model.addAttribute("lin",l);
            model.addAttribute("object", hall);
            model.addAttribute("pageM", n);
            model.addAttribute("redirect", redirect);
            return "hall/edit_hall";
        }
        hallInDB.setNumber(hall.getNumber());
        hallInDB.setDescription(hall.getDescription());
        hallInDB.getSeoBlock().setUrl(hall.getSeoBlock().getUrl());
        hallInDB.getSeoBlock().setTitle(hall.getSeoBlock().getTitle());
        hallInDB.getSeoBlock().setKeywords(hall.getSeoBlock().getKeywords());
        hallInDB.getSeoBlock().setDescription(hall.getSeoBlock().getDescription());
        update = false;
        return "redirect:/admin/"+redirect;
    }

    @GetMapping("/admin/cinemas/delete/{id}")
    public String deleteCinema(@PathVariable Long id) {
        Cinema cinema = cinemaService.getCinemaById(id);
        deleteCinemaImages(cinema);
        cinemaService.deleteCinemaById(id);
        return "redirect:/admin/cinemas";
    }

    @GetMapping("/admin/cinemas/new")
    public String createCinema(Model model) {
        Cinema cinema = new Cinema();
        if(update == true){
            hallList.clear();
        }
        update = true;
        String l = "cinemas/new";
        String editLink = "cinemas/new/hall/edit";
        String deleteLink = "cinemas/new/hall/delete";
        String createLink = "cinemas/new/hall/new";
        model.addAttribute("object", cinema);
        model.addAttribute("hallList", hallList);
        model.addAttribute("pageNUM", n);
        model.addAttribute("lin", l);
        model.addAttribute("editLink",editLink);
        model.addAttribute("deleteLink",deleteLink);
        model.addAttribute("createLink",createLink);
        return "cinema/add_cinema";
    }

    @PostMapping("/admin/cinemas/new")
    public String saveCinema(@Valid @ModelAttribute("object") Cinema cinema, BindingResult bindingResult,
                             @RequestParam("logoImage") MultipartFile logoImage, @RequestParam("logoImageName") String logoImageName,
                             @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("mainImageName") String mainImageName,
                             @RequestParam("image1") MultipartFile image1, @RequestParam("image1Name") String image1Name,
                             @RequestParam("image2") MultipartFile image2, @RequestParam("image2Name") String image2Name,
                             @RequestParam("image3") MultipartFile image3, @RequestParam("image3Name") String image3Name,
                             @RequestParam("image4") MultipartFile image4, @RequestParam("image4Name") String image4Name,
                             @RequestParam("image5") MultipartFile image5, @RequestParam("image5Name") String image5Name, Model model) throws IOException {
        cinema.setImageGallery(new Gallery());
        if(isSupportedExtension(FilenameUtils.getExtension(
                logoImage.getOriginalFilename()))) {
            saveCinemaImage(logoImage, "logoImage", cinema, logoImageName);
        } else if(!logoImage.getOriginalFilename().equals("")){
            model.addAttribute("logoWarning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                mainImage.getOriginalFilename()))) {
            saveCinemaImage(mainImage, "mainImage", cinema, mainImageName);
        } else if(!mainImage.getOriginalFilename().equals("")){
            model.addAttribute("mainWarning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image1.getOriginalFilename()))) {
            saveCinemaImage(image1, "image1", cinema, image1Name);
        } else if(!image1.getOriginalFilename().equals("")){
            model.addAttribute("image1Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image2.getOriginalFilename()))) {
            saveCinemaImage(image2, "image2", cinema, image2Name);
        } else if(!image2.getOriginalFilename().equals("")){
            model.addAttribute("image2Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image3.getOriginalFilename()))) {
            saveCinemaImage(image3, "image3", cinema, image3Name);
        } else if(!image3.getOriginalFilename().equals("")){
            model.addAttribute("image3Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image4.getOriginalFilename()))) {
            saveCinemaImage(image4, "image4", cinema, image4Name);
        } else if(!image4.getOriginalFilename().equals("")){
            model.addAttribute("image4Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image5.getOriginalFilename()))) {
            saveCinemaImage(image5, "image5", cinema, image5Name);
        } else if(!image5.getOriginalFilename().equals("")){
            model.addAttribute("image5Warning", "Некоректний тип файлу");
        }
        if (bindingResult.hasErrors()) {
            String l = "cinemas/new";
            String editLink = "cinemas/new/hall/edit";
            String deleteLink = "cinemas/new/hall/delete";
            String createLink = "cinemas/new/hall/new";
            model.addAttribute("pageNUM", n);
            model.addAttribute("object", cinema);
            model.addAttribute("lin",l);
            model.addAttribute("hallList", hallList);
            model.addAttribute("editLink",editLink);
            model.addAttribute("deleteLink",deleteLink);
            model.addAttribute("createLink",createLink);
            return "cinema/add_cinema";
        }
        if((!mainImage.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(mainImage.getOriginalFilename()))) ||
                (!image1.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image1.getOriginalFilename()))) ||
                (!image2.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image2.getOriginalFilename()))) ||
                (!image3.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image3.getOriginalFilename()))) ||
                (!image4.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image4.getOriginalFilename()))) ||
                (!image5.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image5.getOriginalFilename()))) ||
                (!logoImage.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(logoImage.getOriginalFilename())))
        ){
            String l = "cinemas/new";
            String editLink = "cinemas/new/hall/edit";
            String deleteLink = "cinemas/new/hall/delete";
            String createLink = "cinemas/new/hall/new";
            model.addAttribute("pageNUM", n);
            model.addAttribute("object", cinema);
            model.addAttribute("lin",l);
            model.addAttribute("hallList", hallList);
            model.addAttribute("editLink",editLink);
            model.addAttribute("deleteLink",deleteLink);
            model.addAttribute("createLink",createLink);
            return "cinema/add_cinema";
        }
        for (Hall hall : hallList) {
            hall.setCinema(cinema);
        }
        cinema.setHalls(hallList);
        cinemaService.saveCinema(cinema);
        hallList.clear();
        return "redirect:/admin/cinemas";
    }


    @GetMapping("/admin/cinemas/edit/{id}")
    public String editCinema(@PathVariable Long id, Model model) {
        Cinema cinema = cinemaService.getCinemaById(id);
        cinemaId = id;
        if (update == true) {
            editHallList = cinema.getHalls();
            count++;
        }
        update = true;
        String l = "cinemas/edit/"+id;
        String editLink = "cinemas/edit/hall/edit";
        String deleteLink = "cinemas/edit/hall/delete";
        String createLink = "cinemas/edit/hall/new";
        model.addAttribute("object", cinema);
        model.addAttribute("hallList", editHallList);
        model.addAttribute("pageM", n);
        model.addAttribute("lin",l);
        model.addAttribute("editLink",editLink);
        model.addAttribute("deleteLink",deleteLink);
        model.addAttribute("createLink",createLink);
        return "cinema/edit_cinema";
    }

    @PostMapping("/admin/cinemas/edit/{id}")
    public String updateCinema(@PathVariable("id") Long id, @Valid @ModelAttribute("object") Cinema cinema, BindingResult bindingResult,
                               @RequestParam("logoImage") MultipartFile logoImage, @RequestParam("logoImageName") String logoImageName,
                               @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("mainImageName") String mainImageName,
                               @RequestParam("image1") MultipartFile image1, @RequestParam("image1Name") String image1Name,
                               @RequestParam("image2") MultipartFile image2, @RequestParam("image2Name") String image2Name,
                               @RequestParam("image3") MultipartFile image3, @RequestParam("image3Name") String image3Name,
                               @RequestParam("image4") MultipartFile image4, @RequestParam("image4Name") String image4Name,
                               @RequestParam("image5") MultipartFile image5, @RequestParam("image5Name") String image5Name, Model model) throws IOException {
        Cinema cinemaInDb = cinemaService.getCinemaById(id);
        if(isSupportedExtension(FilenameUtils.getExtension(
                logoImage.getOriginalFilename()))) {
            saveCinemaImage(logoImage, "logoImage", cinemaInDb, logoImageName);
        } else if(!logoImage.getOriginalFilename().equals("")){
            model.addAttribute("logoWarning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                mainImage.getOriginalFilename()))) {
            saveCinemaImage(mainImage, "mainImage", cinemaInDb, mainImageName);
        } else if(!mainImage.getOriginalFilename().equals("")){
            model.addAttribute("mainWarning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image1.getOriginalFilename()))) {
            saveCinemaImage(image1, "image1", cinemaInDb, image1Name);
        } else if(!image1.getOriginalFilename().equals("")){
            model.addAttribute("image1Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image2.getOriginalFilename()))) {
            saveCinemaImage(image2, "image2", cinemaInDb, image2Name);
        } else if(!image2.getOriginalFilename().equals("")){
            model.addAttribute("image2Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image3.getOriginalFilename()))) {
            saveCinemaImage(image3, "image3", cinemaInDb, image3Name);
        } else if(!image3.getOriginalFilename().equals("")){
            model.addAttribute("image3Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image4.getOriginalFilename()))) {
            saveCinemaImage(image4, "image4", cinemaInDb, image4Name);
        } else if(!image4.getOriginalFilename().equals("")){
            model.addAttribute("image4Warning", "Некоректний тип файлу");
        }
        if(isSupportedExtension(FilenameUtils.getExtension(
                image5.getOriginalFilename()))) {
            saveCinemaImage(image5, "image5", cinemaInDb, image5Name);
        } else if(!image5.getOriginalFilename().equals("")){
            model.addAttribute("image5Warning", "Некоректний тип файлу");
        }
        cinema.setImageGallery(cinemaInDb.getImageGallery());
        cinema.setLogo(cinemaInDb.getLogo());
        if (bindingResult.hasErrors()) {
            String l = "cinemas/edit/"+id;
            String editLink = "cinemas/edit/hall/edit";
            String deleteLink = "cinemas/edit/hall/delete";
            String createLink = "cinemas/edit/hall/new";
            model.addAttribute("object", cinema);
            model.addAttribute("lin",l);
            model.addAttribute("pageM", n);
            model.addAttribute("hallList", editHallList);
            model.addAttribute("editLink",editLink);
            model.addAttribute("deleteLink",deleteLink);
            model.addAttribute("createLink",createLink);
            return "cinema/edit_cinema";
        }

        if((!mainImage.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(mainImage.getOriginalFilename()))) ||
                (!image1.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image1.getOriginalFilename()))) ||
                (!image2.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image2.getOriginalFilename()))) ||
                (!image3.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image3.getOriginalFilename()))) ||
                (!image4.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image4.getOriginalFilename()))) ||
                (!image5.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(image5.getOriginalFilename()))) ||
                (!logoImage.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(logoImage.getOriginalFilename())))
        ){
            String l = "cinemas/edit/"+id;
            String editLink = "cinemas/edit/hall/edit";
            String deleteLink = "cinemas/edit/hall/delete";
            String createLink = "cinemas/edit/hall/new";
            model.addAttribute("object", cinema);
            model.addAttribute("lin",l);
            model.addAttribute("pageM", n);
            model.addAttribute("hallList", editHallList);
            model.addAttribute("editLink",editLink);
            model.addAttribute("deleteLink",deleteLink);
            model.addAttribute("createLink",createLink);
            return "cinema/edit_cinema";
        }

        int i = 0;
        for(Hall hall:cinemaInDb.getHalls()){
            if(i < editHallList.size()) {
                hall.setNumber(editHallList.get(i).getNumber());
                hall.setDescription(editHallList.get(i).getDescription());
                hall.setSchemaImage(editHallList.get(i).getSchemaImage());
                hall.getImageGallery().setMainImage(editHallList.get(i).getImageGallery().getMainImage());
                hall.getImageGallery().setImage1(editHallList.get(i).getImageGallery().getImage1());
                hall.getImageGallery().setImage2(editHallList.get(i).getImageGallery().getImage2());
                hall.getImageGallery().setImage3(editHallList.get(i).getImageGallery().getImage3());
                hall.getImageGallery().setImage4(editHallList.get(i).getImageGallery().getImage4());
                hall.getImageGallery().setImage5(editHallList.get(i).getImageGallery().getImage5());
                hall.getSeoBlock().setUrl(editHallList.get(i).getSeoBlock().getUrl());
                hall.getSeoBlock().setTitle(editHallList.get(i).getSeoBlock().getTitle());
                hall.getSeoBlock().setKeywords(editHallList.get(i).getSeoBlock().getKeywords());
                hall.getSeoBlock().setDescription(editHallList.get(i).getSeoBlock().getDescription());
            }
            i++;
        }
        System.out.println(cinemaInDb.getHalls().size());
//        System.out.println(hallsToDelete.size());
        for(int in :hallsToDelete){
            if(in < cinemaInDb.getHalls().size()) {
                cinemaInDb.getHalls().remove(in);
            }
        }
        int s = editHallList.size() - cinemaInDb.getHalls().size();
        int n = editHallList.size()-s;
        if(s > 0) {
            for (int i1 = 0; i1 < s; i1++){
                editHallList.get(n).setCinema(cinemaInDb);
                cinemaInDb.getHalls().add(editHallList.get(n));
                n++;
            }
        }
        cinemaInDb.setName(cinema.getName());
        cinemaInDb.setDescription(cinema.getDescription());
        cinemaInDb.setFacilities(cinema.getFacilities());
        cinemaInDb.getSeoBlock().setUrl(cinema.getSeoBlock().getUrl());
        cinemaInDb.getSeoBlock().setTitle(cinema.getSeoBlock().getTitle());
        cinemaInDb.getSeoBlock().setKeywords(cinema.getSeoBlock().getKeywords());
        cinemaInDb.getSeoBlock().setDescription(cinema.getSeoBlock().getDescription());
        cinemaService.saveCinema(cinemaInDb);
        count = 0;
        return "redirect:/admin/cinemas";
    }
    private boolean isSupportedExtension(String extension) {
        return extension != null && (
                extension.equals("png")
                        || extension.equals("jpg")
                        || extension.equals("jpeg"));
    }

    private void saveCinemaImage(MultipartFile image, String fileName, Cinema cinema, String name) {

        switch (fileName) {
            case "mainImage":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    cinema.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + cinema.getImageGallery().getMainImage());
                    file.delete();
                    cinema.getImageGallery().setMainImage(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cinema.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                } else if (!name.equals("")){
                    cinema.getImageGallery().setMainImage(name);
                }
                break;
            case "image1":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    cinema.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + cinema.getImageGallery().getImage1());
                    file.delete();
                    cinema.getImageGallery().setImage1(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cinema.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    cinema.getImageGallery().setImage1(name);
                }
                break;
            case "image2":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    cinema.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + cinema.getImageGallery().getImage2());
                    file.delete();
                    cinema.getImageGallery().setImage2(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cinema.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    cinema.getImageGallery().setImage2(name);
                }
                break;
            case "image3":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    cinema.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + cinema.getImageGallery().getImage3());
                    file.delete();
                    cinema.getImageGallery().setImage3(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cinema.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    cinema.getImageGallery().setImage3(name);
                }
                break;
            case "image4":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    cinema.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + cinema.getImageGallery().getImage4());
                    file.delete();
                    cinema.getImageGallery().setImage4(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cinema.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    cinema.getImageGallery().setImage4(name);
                }
                break;
            case "image5":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    cinema.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + cinema.getImageGallery().getImage5());
                    file.delete();
                    cinema.getImageGallery().setImage5(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cinema.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    cinema.getImageGallery().setImage5(name);
                }
                break;
            case "logoImage":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    cinema.setLogo(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + cinema.getLogo());
                    file.delete();
                    cinema.setLogo(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    cinema.setLogo(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    cinema.setLogo(name);
                }
                break;
        }
    }

    private void deleteCinemaImages(Cinema cinema) {
        if (cinema.getImageGallery().getMainImage() != null) {
            File file = new File(uploadPath + "/" + cinema.getImageGallery().getMainImage());
            file.delete();
        }
        if (cinema.getImageGallery().getImage1() != null) {
            File file = new File(uploadPath + "/" + cinema.getImageGallery().getImage1());
            file.delete();
        }
        if (cinema.getImageGallery().getImage2() != null) {
            File file = new File(uploadPath + "/" + cinema.getImageGallery().getImage2());
            file.delete();
        }
        if (cinema.getImageGallery().getImage3() != null) {
            File file = new File(uploadPath + "/" + cinema.getImageGallery().getImage3());
            file.delete();
        }
        if (cinema.getImageGallery().getImage4() != null) {
            File file = new File(uploadPath + "/" + cinema.getImageGallery().getImage4());
            file.delete();
        }
        if (cinema.getImageGallery().getImage5() != null) {
            File file = new File(uploadPath + "/" + cinema.getImageGallery().getImage5());
            file.delete();
        }
        if (cinema.getLogo() != null) {
            File file = new File(uploadPath + "/" + cinema.getLogo());
            file.delete();
        }
    }

    private void saveHallImage(MultipartFile image, String fileName, Hall hall, String name) {

        switch (fileName) {
            case "mainImage":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    hall.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + hall.getImageGallery().getMainImage());
                    file.delete();
                    hall.getImageGallery().setMainImage(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    hall.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    hall.getImageGallery().setMainImage(name);
                }
                break;
            case "image1":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    hall.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + hall.getImageGallery().getImage1());
                    file.delete();
                    hall.getImageGallery().setImage1(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    hall.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    hall.getImageGallery().setImage1(name);
                }
                break;
            case "image2":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    hall.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + hall.getImageGallery().getImage2());
                    file.delete();
                    hall.getImageGallery().setImage2(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    hall.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    hall.getImageGallery().setImage2(name);
                }
                break;
            case "image3":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    hall.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + hall.getImageGallery().getImage3());
                    file.delete();
                    hall.getImageGallery().setImage3(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    hall.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    hall.getImageGallery().setImage3(name);
                }
                break;
            case "image4":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    hall.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + hall.getImageGallery().getImage4());
                    file.delete();
                    hall.getImageGallery().setImage4(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    hall.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    hall.getImageGallery().setImage4(name);
                }
                break;
            case "image5":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    hall.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + hall.getImageGallery().getImage5());
                    file.delete();
                    hall.getImageGallery().setImage5(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    hall.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    hall.getImageGallery().setImage5(name);
                }
                break;
            case "schemaImg":
                if (!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + image.getOriginalFilename();
                    hall.setSchemaImage(uniqueName);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if (image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + hall.getSchemaImage());
                    file.delete();
                    hall.setSchemaImage(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    hall.setSchemaImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                    File file = new File(uploadPath+"/"+name);
                    file.delete();
                }else if (!name.equals("")){
                    hall.setSchemaImage(name);
                }
                break;
        }
    }

    private void deleteHallImages(Hall hall) {
        if (hall.getImageGallery().getMainImage() != null) {
            File file = new File(uploadPath + "/" + hall.getImageGallery().getMainImage());
            file.delete();
        }
        if (hall.getImageGallery().getImage1() != null) {
            File file = new File(uploadPath + "/" + hall.getImageGallery().getImage1());
            file.delete();
        }
        if (hall.getImageGallery().getImage2() != null) {
            File file = new File(uploadPath + "/" + hall.getImageGallery().getImage2());
            file.delete();
        }
        if (hall.getImageGallery().getImage3() != null) {
            File file = new File(uploadPath + "/" + hall.getImageGallery().getImage3());
            file.delete();
        }
        if (hall.getImageGallery().getImage4() != null) {
            File file = new File(uploadPath + "/" + hall.getImageGallery().getImage4());
            file.delete();
        }
        if (hall.getImageGallery().getImage5() != null) {
            File file = new File(uploadPath + "/" + hall.getImageGallery().getImage5());
            file.delete();
        }
        if (hall.getSchemaImage() != null) {
            File file = new File(uploadPath + "/" + hall.getSchemaImage());
            file.delete();
        }
    }
}
