package project.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.entity.Gallery;
import project.entity.Film;
import project.service.FilmService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    private Integer n = 3;
    private String uploadPath = "/Users/Anastassia/IdeaProjects/Kino-CMS_admin/uploads";
    @GetMapping("/admin/films")
    public String getFilmsList(Model model){
        model.addAttribute("filmsList", filmService.getAllFilms());
        model.addAttribute("pagenUm", n);
        return "film/films";
    }
    @GetMapping("/admin/films/delete/{id}")
    public String deleteNews(@PathVariable Long id){
        Film film = filmService.getFilmById(id);
        deleteImages(film);
        filmService.deleteFilmById(id);
        return "redirect:/admin/films";
    }
    @GetMapping("/admin/films/new")
    public String createFilm(Model model){
        Film film = new Film();
        film.setImageGallery(new Gallery());
        film.setDate(LocalDate.now());
        String l = "films/new";
        model.addAttribute("film", film);
        model.addAttribute("pagenUm", n);
        model.addAttribute("link", l);
        model.addAttribute("check3D", "3Dt");
        model.addAttribute("check2D", "2Dt");
        model.addAttribute("checkImax", "IMAXt");
        return "film/film_page";
    }
    @PostMapping("/admin/films/new")
    public String saveFilm(@Valid @ModelAttribute("film") Film film, BindingResult bindingResult,
                           @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                           @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                           @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                           @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                           @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                           @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                           @RequestParam(name="checkBox3D",required=false)String checkBox3D, @RequestParam(name="checkBox2D", required=false)String checkBox2D,
                           @RequestParam(name="checkBoxImax", required=false)String checkBoxImax, Model model) throws IOException {
        film.setImageGallery(new Gallery());
        saveImage(mainImage,"mainImage", film, mainImageName);
        saveImage(image1,"image1", film, image1Name);
        saveImage(image2,"image2", film, image2Name);
        saveImage(image3,"image3", film, image3Name);
        saveImage(image4,"image4", film, image4Name);
        saveImage(image5,"image5", film, image5Name);
        String type = "";
        if(checkBox3D !=null){
            checkBox3D = checkBox3D.substring(0,2);
            type+=checkBox3D+",";
            model.addAttribute("check3D", checkBox3D);
        }else{
            model.addAttribute("check3D", "3Dt");
        }
        if(checkBox2D !=null){
            checkBox2D = checkBox2D.substring(0,2);
            type+=checkBox2D+",";
            model.addAttribute("check2D", checkBox2D);
        } else{
            model.addAttribute("check2D", "2Dt");
        }
        if(checkBoxImax !=null){
            checkBoxImax = checkBoxImax.substring(0,4);
            type+=checkBoxImax;
            model.addAttribute("checkImax", checkBoxImax);
        }else{
            model.addAttribute("checkImax", "IMAXt");
        }
        System.out.println(type);
        if (bindingResult.hasErrors()) {
            String l = "films/new";
            model.addAttribute("pagenUm", n);
            model.addAttribute("link", l);
            return "film/film_page";
        }
        film.setType(type);
        filmService.saveFilm(film);
        return "redirect:/admin/films";
    }
    @GetMapping("/admin/films/edit/{id}")
    public String editFilm(@PathVariable Long id, Model model){
        Film film = filmService.getFilmById(id);
        String [] types = film.getType().split(",");
        int i = 0;
        if(types[0] != null){
            checkType(model, types[0], i, types.length);
            i++;
        }
        if(i<types.length && types[1] != null){
            checkType(model, types[1],i,types.length);
            i++;
        }
        if(i<types.length && types[2] != null){
            checkType(model, types[2],i, types.length);
        }
        String l = "films/edit/"+id;
        model.addAttribute("film", film);
        model.addAttribute("pagenUm", n);
        model.addAttribute("link", l);
        return "film/film_page";
    }
    @PostMapping("/admin/films/edit/{id}")
    public String updateFilm(@PathVariable("id") Long id,@Valid @ModelAttribute("film") Film film, BindingResult bindingResult,
                           @RequestParam("mainImage") MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                           @RequestParam("image1")MultipartFile image1, @RequestParam("image1Name")String image1Name,
                           @RequestParam("image2")MultipartFile image2, @RequestParam("image2Name")String image2Name,
                           @RequestParam("image3")MultipartFile image3, @RequestParam("image3Name")String image3Name,
                           @RequestParam("image4")MultipartFile image4, @RequestParam("image4Name")String image4Name,
                           @RequestParam("image5")MultipartFile image5, @RequestParam("image5Name")String image5Name,
                           @RequestParam(name="checkBox3D",required=false)String checkBox3D, @RequestParam(name="checkBox2D", required=false)String checkBox2D,
                           @RequestParam(name="checkBoxImax", required=false)String checkBoxImax, Model model) throws IOException {
        Film filmInDb = filmService.getFilmById(id);
        saveImage(mainImage,"mainImage", filmInDb, mainImageName);
        saveImage(image1,"image1", filmInDb, image1Name);
        saveImage(image2,"image2", filmInDb, image2Name);
        saveImage(image3,"image3", filmInDb, image3Name);
        saveImage(image4,"image4", filmInDb, image4Name);
        saveImage(image5,"image5", filmInDb, image5Name);
        film.setImageGallery(filmInDb.getImageGallery());
        String type = "";
        System.out.println();
        if(checkBox3D !=null){

            checkBox3D = checkBox3D.substring(0,2);
            type+=checkBox3D+",";
            model.addAttribute("check3D", checkBox3D);
        }else{
            model.addAttribute("check3D", "3Dt");
        }
        if(checkBox2D !=null){
            checkBox2D = checkBox2D.substring(0,2);
            type+=checkBox2D+",";
            model.addAttribute("check2D", checkBox2D);
        } else{
            model.addAttribute("check2D", "2Dt");
        }
        if(checkBoxImax !=null){
            checkBoxImax = checkBoxImax.substring(0,4);
            type+=checkBoxImax;
            model.addAttribute("checkImax", checkBoxImax);
        }else{
            model.addAttribute("checkImax", "IMAXt");
        }
        System.out.println(type);
        if (bindingResult.hasErrors()) {
            String l = "films/edit/"+id;
            model.addAttribute("pagenUm", n);
            model.addAttribute("link", l);
            return "film/film_page";
        }
        filmInDb.setName(film.getName());
        filmInDb.setDirector(film.getDescription());
        filmInDb.setTrailer(film.getTrailer());
        filmInDb.setType(type);
        filmInDb.setDate(film.getDate());
        filmInDb.setCountry(film.getCountry());
        filmInDb.setGenre(film.getGenre());
        filmInDb.setProducer(film.getProducer());
        filmInDb.setDirector(film.getDirector());
        filmInDb.setTime(film.getTime());
        filmInDb.setAge(film.getAge());
        filmService.saveFilm(filmInDb);
        return "redirect:/admin/films";
    }

    private void checkType(Model model, String type, int i, int length){
        if(type.equals("3D")){
            if(length==2){
                model.addAttribute("checkImax", "IMAXt");
            }
            if(length==1){
                model.addAttribute("check2D", "2Dt");
                model.addAttribute("checkImax", "IMAXt");
            }
            model.addAttribute("check3D", type);
        }
        if(type.equals("2D")){
            if(i==0 && length==2){
                model.addAttribute("check3D", "3Dt");
            }
            if(i==0 && length==1){
                model.addAttribute("check3D", "3Dt");
                model.addAttribute("checkImax", "IMAXt");
            }
            model.addAttribute("check2D", type);
        }
        if(type.equals("IMAX")){
            if(i==0){
                model.addAttribute("check3D", "3Dt");
                model.addAttribute("check2D", "2Dt");
            }
            model.addAttribute("checkImax", type);
        }
    }
    private void saveImage(MultipartFile image,String fileName, Film film, String name){

        switch(fileName){
            case "mainImage":
                if(!image.getOriginalFilename().equals("") && name.equals("")) {
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    film.getImageGallery().setMainImage(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                } else if(image.getOriginalFilename().equals("") && name.equals("") ) {
                    File file = new File(uploadPath + "/" + film.getImageGallery().getMainImage());
                    file.delete();
                    film.getImageGallery().setMainImage(null);
                }
                 else if(!image.getOriginalFilename().equals(name) && !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    film.getImageGallery().setMainImage(uniqueName);
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
                    film.getImageGallery().setImage1(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+film.getImageGallery().getImage1());
                    file.delete();
                    film.getImageGallery().setImage1(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    film.getImageGallery().setImage1(uniqueName);
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
                    film.getImageGallery().setImage2(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+film.getImageGallery().getImage2());
                    file.delete();
                    film.getImageGallery().setImage2(null);

                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    film.getImageGallery().setImage2(uniqueName);
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
                    film.getImageGallery().setImage3(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")) {
                    File file = new File(uploadPath + "/" + film.getImageGallery().getImage3());
                    file.delete();
                    film.getImageGallery().setImage3(null);
                } else if(!image.getOriginalFilename().equals(name) && !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    film.getImageGallery().setImage3(uniqueName);
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
                    film.getImageGallery().setImage4(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+film.getImageGallery().getImage4());
                    file.delete();
                    film.getImageGallery().setImage4(null);
                }else if(!image.getOriginalFilename().equals(name)&& !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    film.getImageGallery().setImage4(uniqueName);
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
                    film.getImageGallery().setImage5(uniqueName);
                    Path path = Paths.get(uploadPath+"/"+uniqueName);
                    try {
                        image.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }
                else if(image.getOriginalFilename().equals("") && name.equals("")){
                    File file = new File(uploadPath+"/"+film.getImageGallery().getImage5());
                    file.delete();
                    film.getImageGallery().setImage5(null);
                }else if(!image.getOriginalFilename().equals(name) && !image.getOriginalFilename().equals("")){
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile+"."+image.getOriginalFilename();
                    film.getImageGallery().setImage5(uniqueName);
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
    private void deleteImages(Film film){
        if(film.getImageGallery().getMainImage() != null){
            File file = new File(uploadPath+"/"+ film.getImageGallery().getMainImage());
            file.delete();
        }
        if(film.getImageGallery().getImage1() != null){
            File file = new File(uploadPath+"/"+ film.getImageGallery().getImage1());
            file.delete();
        }
        if(film.getImageGallery().getImage2() != null){
            File file = new File(uploadPath+"/"+ film.getImageGallery().getImage2());
            file.delete();
        }
        if(film.getImageGallery().getImage3() != null){
            File file = new File(uploadPath+"/"+ film.getImageGallery().getImage3());
            file.delete();
        }
        if(film.getImageGallery().getImage4() != null){
            File file = new File(uploadPath+"/"+ film.getImageGallery().getImage4());
            file.delete();
        }
        if(film.getImageGallery().getImage5() != null){
            File file = new File(uploadPath+"/"+ film.getImageGallery().getImage5());
            file.delete();
        }
    }
}
