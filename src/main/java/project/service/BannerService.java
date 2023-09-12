package project.service;

import project.entity.BackgroundImage;
import project.entity.MainBanner;
import project.entity.NewsBanner;

import java.util.List;

public interface BannerService {
    List<MainBanner> getAllMainBanners();
    List<NewsBanner> getAllNewsBanners();
    BackgroundImage getBackgroundImage();
    MainBanner saveMainBanner(MainBanner mainBanner);
    NewsBanner saveNewsBanner(NewsBanner newsBanner);
    BackgroundImage saveBackgroundImage(BackgroundImage backgroundImage);
}
