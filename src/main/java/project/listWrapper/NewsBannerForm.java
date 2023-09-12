package project.listWrapper;

import project.entity.NewsBanner;

import java.util.List;

public class NewsBannerForm {
    private List<NewsBanner> newsBannerList;

    public List<NewsBanner> getNewsBannerList() {
        return newsBannerList;
    }

    public void setNewsBannerList(List<NewsBanner> newsBannerList) {
        this.newsBannerList = newsBannerList;
    }
}
