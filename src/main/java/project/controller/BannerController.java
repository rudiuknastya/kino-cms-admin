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
import project.entity.BackgroundImage;
import project.entity.MainBanner;
import project.entity.NewsBanner;
import project.listWrapper.BackgroundImageForm;
import project.listWrapper.MainBannerForm;
import project.listWrapper.NewsBannerForm;
import project.service.BannerService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    private Integer n = 2;
    @Value("${upload.path}")
    private String uploadPath;
    private List<MainBanner> mainBanners;
    private List<NewsBanner> newsBanners;
    private List<Integer> speed = List.of(0,1,2,3,4,5);
    @GetMapping("/admin/banners")
    public String getBanners(Model model) {
        String l = "banners/main";
        String ln = "banners/news";
        String lnk = "banners/background";
        NewsBannerForm newsBannerForm = new NewsBannerForm();
        newsBanners = bannerService.getAllNewsBanners();
        newsBannerForm.setNewsBannerList(newsBanners);
        MainBannerForm mainBannerForm = new MainBannerForm();
        mainBanners = bannerService.getAllMainBanners();
        mainBannerForm.setMainBannerList(mainBanners);
        BackgroundImageForm backgroundImageForm = new BackgroundImageForm();
        backgroundImageForm.setBackgroundImageList(bannerService.getBackgroundImages());
        model.addAttribute("mainBanner", mainBannerForm);
        model.addAttribute("newsBanner", newsBannerForm);
        model.addAttribute("backgroundImage", backgroundImageForm);
        model.addAttribute("pageNUMB", n);
        model.addAttribute("mainBannerLink", l);
        model.addAttribute("newsBannerLink", ln);
        model.addAttribute("backgroundImageLink", lnk);
        model.addAttribute("speed",speed);
        return "banner/banners";
    }

    @PostMapping("/admin/banners/main")
    public String saveMainBanner(@ModelAttribute("mainBanner") MainBannerForm mainBannerForm,
                                 @RequestParam("image0") MultipartFile image0, @RequestParam("imageName0")String imageName0,
                                 @RequestParam("image1") MultipartFile image1, @RequestParam("imageName1")String imageName1,
                                 @RequestParam("image2") MultipartFile image2, @RequestParam("imageName2")String imageName2,
                                 @RequestParam("image3") MultipartFile image3, @RequestParam("imageName3")String imageName3,
                                 @RequestParam("image4") MultipartFile image4, @RequestParam("imageName4")String imageName4,
                                 @RequestParam("images") MultipartFile[] images, Model model) throws IOException {
        String [] mainWarnings = new String[5];
        if((isSupportedExtension(FilenameUtils.getExtension(
                image0.getOriginalFilename())) && !image0.getOriginalFilename().equals("")) ||
                (0 < images.length && isSupportedExtension(FilenameUtils.getExtension(
                        images[0].getOriginalFilename())) && !images[0].getOriginalFilename().equals(""))) {
            saveMainBannerImage(image0, images, 0, imageName0);
        }else if(!image0.getOriginalFilename().equals("") || (0 < images.length && !images[0].getOriginalFilename().equals(""))){
            mainWarnings[0] =  "Некоректний тип файлу";
        }
        if((isSupportedExtension(FilenameUtils.getExtension(
                image1.getOriginalFilename())) && !image1.getOriginalFilename().equals("")) ||
                (1 < images.length && isSupportedExtension(FilenameUtils.getExtension(
                        images[1].getOriginalFilename())) && !images[1].getOriginalFilename().equals(""))) {
            saveMainBannerImage(image1, images, 1, imageName1);
        }else if(!image1.getOriginalFilename().equals("") || (1 < images.length && !images[1].getOriginalFilename().equals(""))){
            mainWarnings[1] =  "Некоректний тип файлу";
        }
        if((isSupportedExtension(FilenameUtils.getExtension(
                image2.getOriginalFilename())) && !image2.getOriginalFilename().equals("")) ||
                (2 < images.length && isSupportedExtension(FilenameUtils.getExtension(
                        images[2].getOriginalFilename())) && !images[2].getOriginalFilename().equals(""))) {
            saveMainBannerImage(image2, images, 2, imageName2);
        } else if(!image2.getOriginalFilename().equals("") || (2 < images.length && !images[2].getOriginalFilename().equals(""))){
            mainWarnings[2] =  "Некоректний тип файлу";
        }
        if((isSupportedExtension(FilenameUtils.getExtension(
                image3.getOriginalFilename())) && !image3.getOriginalFilename().equals("")) ||
                (3 < images.length && isSupportedExtension(FilenameUtils.getExtension(
                        images[3].getOriginalFilename())) && !images[3].getOriginalFilename().equals(""))) {
            saveMainBannerImage(image3, images, 3, imageName3);
        } else if(!image3.getOriginalFilename().equals("") || (3 < images.length && !images[3].getOriginalFilename().equals(""))){
            mainWarnings[3] =  "Некоректний тип файлу";
        }
        if((isSupportedExtension(FilenameUtils.getExtension(
                image4.getOriginalFilename())) && !image4.getOriginalFilename().equals("")) ||
                (4 < images.length && isSupportedExtension(FilenameUtils.getExtension(
                        images[4].getOriginalFilename())) && !images[4].getOriginalFilename().equals(""))) {
            saveMainBannerImage(image4, images, 4, imageName4);
        } else if(!image4.getOriginalFilename().equals("") || (4 < images.length && !images[4].getOriginalFilename().equals(""))){
            mainWarnings[4] =  "Некоректний тип файлу";
        }
        if (((!isSupportedExtension(FilenameUtils.getExtension(image0.getOriginalFilename())) && !image0.getOriginalFilename().equals("")) ||
                (0 < images.length && !isSupportedExtension(FilenameUtils.getExtension(images[0].getOriginalFilename())) && !images[0].getOriginalFilename().equals(""))) ||
                ((!isSupportedExtension(FilenameUtils.getExtension(image1.getOriginalFilename())) && !image1.getOriginalFilename().equals("")) ||
                        (1 < images.length && !isSupportedExtension(FilenameUtils.getExtension(images[1].getOriginalFilename())) && !images[1].getOriginalFilename().equals(""))) ||
                ((!isSupportedExtension(FilenameUtils.getExtension(image2.getOriginalFilename())) && !image2.getOriginalFilename().equals("")) ||
                        (2 < images.length && !isSupportedExtension(FilenameUtils.getExtension(images[2].getOriginalFilename())) && !images[2].getOriginalFilename().equals(""))) ||
                ((!isSupportedExtension(FilenameUtils.getExtension(image3.getOriginalFilename())) && !image3.getOriginalFilename().equals("")) ||
                        (3 < images.length && !isSupportedExtension(FilenameUtils.getExtension(images[3].getOriginalFilename())) && !images[3].getOriginalFilename().equals(""))) ||
                ((!isSupportedExtension(FilenameUtils.getExtension(image4.getOriginalFilename())) && !image4.getOriginalFilename().equals("")) ||
                        (4 < images.length && !isSupportedExtension(FilenameUtils.getExtension(images[4].getOriginalFilename())) && !images[4].getOriginalFilename().equals("")))
        ) {
            MainBannerForm mainBannerForm1 = new MainBannerForm();
            mainBannerForm1.setMainBannerList(mainBanners);
            BackgroundImageForm backgroundImageForm = new BackgroundImageForm();
            backgroundImageForm.setBackgroundImageList(bannerService.getBackgroundImages());
            NewsBannerForm newsBannerForm = new NewsBannerForm();
            newsBannerForm.setNewsBannerList(bannerService.getAllNewsBanners());
            String l = "banners/main";
            String ln = "banners/news";
            String lnk = "banners/background";
            model.addAttribute("pageNUMB", n);
            model.addAttribute("mainBannerLink", l);
            model.addAttribute("newsBannerLink", ln);
            model.addAttribute("backgroundImageLink", lnk);
            model.addAttribute("speed",speed);
            model.addAttribute("mainWarnings", mainWarnings);
            model.addAttribute("mainBanner", mainBannerForm1);
            model.addAttribute("newsBanner", newsBannerForm);
            model.addAttribute("backgroundImage", backgroundImageForm);
            return "banner/banners";
        }

        int i = 0;
        mainBanners.get(0).setStatus(mainBannerForm.getMainBannerList().get(0).getStatus());
        for(MainBanner mainBanner: mainBannerForm.getMainBannerList()){
            mainBanners.get(i).setText(mainBanner.getText());
            mainBanners.get(i).setUrl(mainBanner.getUrl());
            mainBanners.get(i).setSpeed(mainBannerForm.getMainBannerList().get(0).getSpeed());
            bannerService.saveMainBanner(mainBanners.get(i));
            i++;
        }

        return "redirect:/admin/banners";
    }
    @PostMapping("/admin/banners/news")
    public String saveNewsBanner(@ModelAttribute("newsBanner") NewsBannerForm newsBannerForm,
                                 @RequestParam("newsImage0") MultipartFile newsImage0, @RequestParam("newsImageName0")String newsImageName0,
                                 @RequestParam("newsImage1") MultipartFile newsImage1, @RequestParam("newsImageName1")String newsImageName1,
                                 @RequestParam("newsImage2") MultipartFile newsImage2, @RequestParam("newsImageName2")String newsImageName2,
                                 @RequestParam("newsImage3") MultipartFile newsImage3, @RequestParam("newsImageName3")String newsImageName3,
                                 @RequestParam("newsImage4") MultipartFile newsImage4, @RequestParam("newsImageName4")String newsImageName4,
                                 @RequestParam("newsImages") MultipartFile[] newsImages, Model model) throws IOException {
        String [] newsWarnings = new String[5];
        if((isSupportedExtension(FilenameUtils.getExtension(
                newsImage0.getOriginalFilename())) && !newsImage0.getOriginalFilename().equals("")) ||
                (0 < newsImages.length && isSupportedExtension(FilenameUtils.getExtension(
                        newsImages[0].getOriginalFilename())) && !newsImages[0].getOriginalFilename().equals(""))) {
            saveNewsBannerImage(newsImage0, newsImages, 0, newsImageName0);
        } else if(!newsImage0.getOriginalFilename().equals("") || (0 < newsImages.length && !newsImages[0].getOriginalFilename().equals(""))){
            newsWarnings[0] =  "Некоректний тип файлу";
        }
        if((isSupportedExtension(FilenameUtils.getExtension(
                newsImage1.getOriginalFilename())) && !newsImage1.getOriginalFilename().equals("")) ||
                (1 < newsImages.length && isSupportedExtension(FilenameUtils.getExtension(
                        newsImages[1].getOriginalFilename())) && !newsImages[1].getOriginalFilename().equals(""))) {
            saveNewsBannerImage(newsImage1, newsImages, 1, newsImageName1);
        }else if(!newsImage1.getOriginalFilename().equals("") || (1 < newsImages.length && !newsImages[1].getOriginalFilename().equals(""))){
            newsWarnings[1] =  "Некоректний тип файлу";
        }
        if((isSupportedExtension(FilenameUtils.getExtension(
                newsImage2.getOriginalFilename())) && !newsImage2.getOriginalFilename().equals("")) ||
                (2 < newsImages.length && isSupportedExtension(FilenameUtils.getExtension(
                        newsImages[2].getOriginalFilename())) && !newsImages[2].getOriginalFilename().equals(""))) {
            saveNewsBannerImage(newsImage2, newsImages, 2, newsImageName2);
        } else if(!newsImage2.getOriginalFilename().equals("") || (2 < newsImages.length && !newsImages[2].getOriginalFilename().equals(""))){
            newsWarnings[2] =  "Некоректний тип файлу";
        }
        if((isSupportedExtension(FilenameUtils.getExtension(
                newsImage3.getOriginalFilename())) && !newsImage3.getOriginalFilename().equals("")) ||
                (3 < newsImages.length && isSupportedExtension(FilenameUtils.getExtension(
                        newsImages[3].getOriginalFilename())) && !newsImages[3].getOriginalFilename().equals(""))) {
            saveNewsBannerImage(newsImage3,newsImages,3, newsImageName3);
        } else if(!newsImage3.getOriginalFilename().equals("") || (3 < newsImages.length && !newsImages[3].getOriginalFilename().equals(""))){
            newsWarnings[3] =  "Некоректний тип файлу";
        }
        if((isSupportedExtension(FilenameUtils.getExtension(
                newsImage4.getOriginalFilename())) && !newsImage4.getOriginalFilename().equals("")) ||
                (4 < newsImages.length && isSupportedExtension(FilenameUtils.getExtension(
                        newsImages[4].getOriginalFilename())) && !newsImages[4].getOriginalFilename().equals(""))) {
            saveNewsBannerImage(newsImage4, newsImages, 4, newsImageName4);
        } else if(!newsImage4.getOriginalFilename().equals("") || (4 < newsImages.length && !newsImages[4].getOriginalFilename().equals(""))){
            newsWarnings[4] =  "Некоректний тип файлу";
        }
        if (((!isSupportedExtension(FilenameUtils.getExtension(newsImage0.getOriginalFilename())) && !newsImage0.getOriginalFilename().equals("")) ||
                (0 < newsImages.length && !isSupportedExtension(FilenameUtils.getExtension(newsImages[0].getOriginalFilename())) && !newsImages[0].getOriginalFilename().equals(""))) ||
                ((!isSupportedExtension(FilenameUtils.getExtension(newsImage1.getOriginalFilename())) && !newsImage1.getOriginalFilename().equals("")) ||
                        (1 < newsImages.length && !isSupportedExtension(FilenameUtils.getExtension(newsImages[1].getOriginalFilename())) && !newsImages[1].getOriginalFilename().equals(""))) ||
                ((!isSupportedExtension(FilenameUtils.getExtension(newsImage2.getOriginalFilename())) && !newsImage2.getOriginalFilename().equals("")) ||
                        (2 < newsImages.length && !isSupportedExtension(FilenameUtils.getExtension(newsImages[2].getOriginalFilename())) && !newsImages[2].getOriginalFilename().equals(""))) ||
                ((!isSupportedExtension(FilenameUtils.getExtension(newsImage3.getOriginalFilename())) && !newsImage3.getOriginalFilename().equals("")) ||
                        (3 < newsImages.length && !isSupportedExtension(FilenameUtils.getExtension(newsImages[3].getOriginalFilename())) && !newsImages[3].getOriginalFilename().equals(""))) ||
                ((!isSupportedExtension(FilenameUtils.getExtension(newsImage4.getOriginalFilename())) && !newsImage4.getOriginalFilename().equals("")) ||
                        (4 < newsImages.length && !isSupportedExtension(FilenameUtils.getExtension(newsImages[4].getOriginalFilename())) && !newsImages[4].getOriginalFilename().equals("")))
        ) {
            MainBannerForm mainBannerForm = new MainBannerForm();
            mainBanners = bannerService.getAllMainBanners();
            mainBannerForm.setMainBannerList(mainBanners);
            BackgroundImageForm backgroundImageForm = new BackgroundImageForm();
            backgroundImageForm.setBackgroundImageList(bannerService.getBackgroundImages());
            NewsBannerForm newsBannerForm1 = new NewsBannerForm();
            newsBannerForm1.setNewsBannerList(newsBanners);
            String l = "banners/main";
            String ln = "banners/news";
            String lnk = "banners/background";
            model.addAttribute("pageNUMB", n);
            model.addAttribute("mainBannerLink", l);
            model.addAttribute("newsBannerLink", ln);
            model.addAttribute("backgroundImageLink", lnk);
            model.addAttribute("speed",speed);
            model.addAttribute("newsWarnings", newsWarnings);
            model.addAttribute("mainBanner", mainBannerForm);
            model.addAttribute("newsBanner", newsBannerForm1);
            model.addAttribute("backgroundImage", backgroundImageForm);
            return "banner/banners";
        }
        int i = 0;
        newsBanners.get(0).setStatus(newsBannerForm.getNewsBannerList().get(0).getStatus());
        for(NewsBanner newsBanner: newsBannerForm.getNewsBannerList()){
            newsBanners.get(i).setUrl(newsBanner.getUrl());
            newsBanners.get(i).setSpeed(newsBannerForm.getNewsBannerList().get(0).getSpeed());
            bannerService.saveNewsBanner(newsBanners.get(i));
            i++;
        }
        return "redirect:/admin/banners";
    }

    @PostMapping("/admin/banners/background")
    public String saveBackgroundImage(@Valid @ModelAttribute("backgroundImage") BackgroundImageForm backgroundImage, BindingResult bindingResult,
                                      @RequestParam(name="mainImage", required = false) MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                                      Model model){
    System.out.println(backgroundImage.getBackgroundImageList().get(1).getImage());
        System.out.println(mainImage.getOriginalFilename());
        if(backgroundImage.getBackgroundImageList().get(1).getImage().equals("image")) {
            if(isSupportedExtension(FilenameUtils.getExtension(
                    mainImage.getOriginalFilename()))) {
                if (!mainImage.getOriginalFilename().equals("")) {
                    BackgroundImage backgroundImageInDb = bannerService.getBackgroundImageById(1L);
                    File file = new File(uploadPath + "/" + backgroundImageInDb.getImage());
                    file.delete();
                    String uuidFile = UUID.randomUUID().toString();
                    String uniqueName = uuidFile + "." + mainImage.getOriginalFilename();
                    backgroundImageInDb.setImage(uniqueName);
                    bannerService.saveBackgroundImage(backgroundImageInDb);
                    Path path = Paths.get(uploadPath + "/" + uniqueName);
                    try {
                        mainImage.transferTo(new File(path.toUri()));
                    } catch (IOException e) {
                    }
                }
            }else if(!mainImage.getOriginalFilename().equals("")){
                model.addAttribute("backgroundWarning", "Некоректний тип файлу");
            }
        }
        if (bindingResult.hasErrors()) {
            NewsBannerForm newsBannerForm = new NewsBannerForm();
            newsBannerForm.setNewsBannerList(bannerService.getAllNewsBanners());
            MainBannerForm mainBannerForm = new MainBannerForm();
            mainBannerForm.setMainBannerList(bannerService.getAllMainBanners());
            backgroundImage.setBackgroundImageList(bannerService.getBackgroundImages());
            String l = "banners/main";
            String ln = "banners/news";
            String lnk = "banners/background";
            model.addAttribute("pageNUMB", n);
            model.addAttribute("mainBannerLink", l);
            model.addAttribute("newsBannerLink", ln);
            model.addAttribute("backgroundImageLink", lnk);
            model.addAttribute("speed",speed);
            model.addAttribute("mainBanner", mainBannerForm);
            model.addAttribute("newsBanner", newsBannerForm);
            model.addAttribute("backgroundImage", backgroundImage);
            return "banner/banners";
        }
        if(!mainImage.getOriginalFilename().equals("") && !isSupportedExtension(FilenameUtils.getExtension(mainImage.getOriginalFilename()))){
            NewsBannerForm newsBannerForm = new NewsBannerForm();
            newsBannerForm.setNewsBannerList(bannerService.getAllNewsBanners());
            MainBannerForm mainBannerForm = new MainBannerForm();
            mainBannerForm.setMainBannerList(bannerService.getAllMainBanners());
            backgroundImage.setBackgroundImageList(bannerService.getBackgroundImages());
            String l = "banners/main";
            String ln = "banners/news";
            String lnk = "banners/background";
            model.addAttribute("pageNUMB", n);
            model.addAttribute("mainBannerLink", l);
            model.addAttribute("newsBannerLink", ln);
            model.addAttribute("backgroundImageLink", lnk);
            model.addAttribute("speed",speed);
            model.addAttribute("mainBanner", mainBannerForm);
            model.addAttribute("newsBanner", newsBannerForm);
            model.addAttribute("backgroundImage", backgroundImage);
            return "banner/banners";
        }
        BackgroundImage img =  bannerService.getBackgroundImageById(2L);
        img.setImage(backgroundImage.getBackgroundImageList().get(1).getImage());
        bannerService.saveBackgroundImage(img);
        return "redirect:/admin/banners";
    }

    private boolean isSupportedExtension(String extension) {
        return extension != null && (
                extension.equals("png")
                        || extension.equals("jpg")
                        || extension.equals("jpeg"));
    }

    private void saveNewsBannerImage(MultipartFile image, MultipartFile[] images, int i, String fileName) {
        if (!image.getOriginalFilename().equals("") && image.getOriginalFilename().equals(fileName)) {
            System.out.println(i+" Here 1 if");
            String uuidFile = UUID.randomUUID().toString();
            String uniqueName = uuidFile + "." + fileName;
            Path path = Paths.get(uploadPath + "/" + uniqueName);
            try {
                image.transferTo(new File(path.toUri()));
            } catch (IOException e) {
            }
            if (!image.getOriginalFilename().equals(newsBanners.get(i).getImage())){
                File file = new File(uploadPath+"/"+ newsBanners.get(i).getImage());
                file.delete();
            }
            newsBanners.get(i).setImage(uniqueName);
        } else if(i<images.length && images[i].getOriginalFilename().equals(fileName) && !fileName.equals("")){
            System.out.println(i+" Here 2 if");
            String uuidFile = UUID.randomUUID().toString();
            String uniqueName = uuidFile + "." + fileName;
            Path path = Paths.get(uploadPath + "/" + uniqueName);
            try {
                images[i].transferTo(new File(path.toUri()));
            } catch (IOException e) {
            }
            if (!images[i].getOriginalFilename().equals(newsBanners.get(i).getImage())){
                File file = new File(uploadPath+"/"+ newsBanners.get(i).getImage());
                file.delete();
            }
            newsBanners.get(i).setImage(uniqueName);
        } else if(fileName.equals("") && newsBanners.get(i).getImage() != null){
            System.out.println(i+" Here 3 if");
            File file = new File(uploadPath+"/"+ newsBanners.get(i).getImage());
            file.delete();
            newsBanners.get(i).setImage(null);
        }
    }

    private void saveMainBannerImage(MultipartFile image, MultipartFile[] images, int i, String fileName) {
        if (!image.getOriginalFilename().equals("") && image.getOriginalFilename().equals(fileName)) {
            System.out.println(i+" Here 1 if");
                String uuidFile = UUID.randomUUID().toString();
                String uniqueName = uuidFile + "." + fileName;
                Path path = Paths.get(uploadPath + "/" + uniqueName);
                try {
                   image.transferTo(new File(path.toUri()));
                } catch (IOException e) {
                }
                if (!image.getOriginalFilename().equals(mainBanners.get(i).getImage())){
                    File file = new File(uploadPath+"/"+ mainBanners.get(i).getImage());
                    file.delete();
                }
            mainBanners.get(i).setImage(uniqueName);
            } else if(i<images.length && images[i].getOriginalFilename().equals(fileName) && !fileName.equals("")){
                System.out.println(i+" Here 2 if");
                String uuidFile = UUID.randomUUID().toString();
                String uniqueName = uuidFile + "." + fileName;
                Path path = Paths.get(uploadPath + "/" + uniqueName);
                try {
                    images[i].transferTo(new File(path.toUri()));
                } catch (IOException e) {
                }
            if (!images[i].getOriginalFilename().equals(mainBanners.get(i).getImage())){
                File file = new File(uploadPath+"/"+ mainBanners.get(i).getImage());
                file.delete();
            }
            mainBanners.get(i).setImage(uniqueName);
            } else if(fileName.equals("") && mainBanners.get(i).getImage() != null){
            System.out.println(i+" Here 3 if");
                File file = new File(uploadPath+"/"+ mainBanners.get(i).getImage());
                file.delete();
                mainBanners.get(i).setImage(null);
            }
        }
    }

