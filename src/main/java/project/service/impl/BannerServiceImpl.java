package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.BackgroundImage;
import project.entity.MainBanner;
import project.entity.NewsBanner;
import project.repository.BackgroundImageRepository;
import project.repository.MainBannerRepository;
import project.repository.NewsBannerRepository;
import project.service.BannerService;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    private final MainBannerRepository mainBannerRepository;
    private final NewsBannerRepository newsBannerRepository;
    private final BackgroundImageRepository backgroundImageRepository;

    public BannerServiceImpl(MainBannerRepository mainBannerRepository, NewsBannerRepository newsBannerRepository, BackgroundImageRepository backgroundImageRepository) {
        this.mainBannerRepository = mainBannerRepository;
        this.newsBannerRepository = newsBannerRepository;
        this.backgroundImageRepository = backgroundImageRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");

    @Override
    public List<MainBanner> getAllMainBanners() {
        logger.info("getAllMainBanners() - Finding all main banners");
        List<MainBanner> mainBanners = mainBannerRepository.findAll();
        logger.info("getAllMainBanners() - All main banners were found");
        return mainBanners;
    }


    @Override
    public List<NewsBanner> getAllNewsBanners() {
        logger.info("getAllNewsBanners() - Finding all news banners");
        List<NewsBanner> newsBanners = newsBannerRepository.findAll();
        logger.info("getAllNewsBanners() - All news banners were found");
        return newsBanners;
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        logger.info("getBackgroundImage() - Finding background image");
        BackgroundImage backgroundImage = backgroundImageRepository.findById(1L).get();
        logger.info("getBackgroundImage() - background image was found");
        return backgroundImage;
    }

    @Override
    public MainBanner saveMainBanner(MainBanner mainBanner) {
        logger.info("saveMainBanner() - Saving main banner");
        MainBanner mainBanner1 = mainBannerRepository.save(mainBanner);
        logger.info("saveMainBanner() - Main banner was saved");
        return mainBanner1;
    }

    @Override
    public NewsBanner saveNewsBanner(NewsBanner newsBanner) {
        logger.info("saveNewsBanner() - Saving news banner");
        NewsBanner newsBanner1 = newsBannerRepository.save(newsBanner);
        logger.info("saveNewsBanner() - News banner was saved");
        return newsBanner1;
    }

    @Override
    public BackgroundImage saveBackgroundImage(BackgroundImage backgroundImage) {
        logger.info("saveBackgroundImage() - Saving background image");
        BackgroundImage backgroundImage1 = backgroundImageRepository.save(backgroundImage);
        logger.info("saveBackgroundImage() - Background image was saved");
        return backgroundImage1;
    }
}
