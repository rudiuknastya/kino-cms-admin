package project.controller;

import jakarta.validation.Valid;
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
    public String saveMainBanner(@Valid @ModelAttribute("mainBanner") MainBannerForm mainBannerForm, BindingResult bindingResult,
                                 @RequestParam("image0") MultipartFile image0, @RequestParam("imageName0")String imageName0,
                                 @RequestParam("image1") MultipartFile image1, @RequestParam("imageName1")String imageName1,
                                 @RequestParam("image2") MultipartFile image2, @RequestParam("imageName2")String imageName2,
                                 @RequestParam("image3") MultipartFile image3, @RequestParam("imageName3")String imageName3,
                                 @RequestParam("image4") MultipartFile image4, @RequestParam("imageName4")String imageName4,
                                 @RequestParam("images") MultipartFile[] images, Model model) throws IOException {
        saveMainBannerImage(image0,images,0, imageName0);
        saveMainBannerImage(image1,images,1, imageName1);
        saveMainBannerImage(image2,images,2, imageName2);
        saveMainBannerImage(image3,images,3, imageName3);
        saveMainBannerImage(image4,images,4, imageName4);
        if (bindingResult.hasErrors()) {
            String l = "banners/main";
            String ln = "banners/news";
            String lnk = "banners/background";
            model.addAttribute("pageNUMB", n);
            model.addAttribute("mainBannerLink", l);
            model.addAttribute("newsBannerLink", ln);
            model.addAttribute("backgroundImageLink", lnk);
            model.addAttribute("speed",speed);
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
    public String saveNewsBanner(@Valid @ModelAttribute("newsBanner") NewsBannerForm newsBannerForm, BindingResult bindingResult,
                                 @RequestParam("newsImage0") MultipartFile newsImage0, @RequestParam("newsImageName0")String newsImageName0,
                                 @RequestParam("newsImage1") MultipartFile newsImage1, @RequestParam("newsImageName1")String newsImageName1,
                                 @RequestParam("newsImage2") MultipartFile newsImage2, @RequestParam("newsImageName2")String newsImageName2,
                                 @RequestParam("newsImage3") MultipartFile newsImage3, @RequestParam("newsImageName3")String newsImageName3,
                                 @RequestParam("newsImage4") MultipartFile newsImage4, @RequestParam("newsImageName4")String newsImageName4,
                                 @RequestParam("newsImages") MultipartFile[] newsImages, Model model) throws IOException {
        saveNewsBannerImage(newsImage0,newsImages,0, newsImageName0);
        saveNewsBannerImage(newsImage1,newsImages,1, newsImageName1);
        saveNewsBannerImage(newsImage2,newsImages,2, newsImageName2);
        saveNewsBannerImage(newsImage3,newsImages,3, newsImageName3);
        saveNewsBannerImage(newsImage4,newsImages,4, newsImageName4);
        if (bindingResult.hasErrors()) {
            String l = "banners/main";
            String ln = "banners/news";
            String lnk = "banners/background";
            model.addAttribute("pageNUMB", n);
            model.addAttribute("mainBannerLink", l);
            model.addAttribute("newsBannerLink", ln);
            model.addAttribute("backgroundImageLink", lnk);
            model.addAttribute("speed",speed);
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
                                      @RequestParam(name="mainImage", required = false)MultipartFile mainImage, @RequestParam("mainImageName")String mainImageName,
                                      Model model){
//

        if(backgroundImage.getBackgroundImageList().get(1).getImage().equals("image")) {
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
        }
        if (bindingResult.hasErrors()) {
            String l = "banners/main";
            String ln = "banners/news";
            String lnk = "banners/background";
            model.addAttribute("pageNUMB", n);
            model.addAttribute("mainBannerLink", l);
            model.addAttribute("newsBannerLink", ln);
            model.addAttribute("backgroundImageLink", lnk);
            model.addAttribute("speed",speed);
            return "banner/banners";
        }
        BackgroundImage img =  bannerService.getBackgroundImageById(2L);
        img.setImage(backgroundImage.getBackgroundImageList().get(1).getImage());
        bannerService.saveBackgroundImage(img);
        return "redirect:/admin/banners";
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

