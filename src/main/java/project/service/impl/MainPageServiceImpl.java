package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.MainPage;
import project.repository.MainPageRepository;
import project.service.MainPageService;
@Service
public class MainPageServiceImpl implements MainPageService {
    private final MainPageRepository mainPageRepository;

    public MainPageServiceImpl(MainPageRepository mainPageRepository) {
        this.mainPageRepository = mainPageRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");
    @Override
    public MainPage getMainPage() {
        logger.info("getMainPage() - Finding main page");
        MainPage mainPage = mainPageRepository.findById(1L).get();
        logger.info("getMainPage() - Main page was found");
        return mainPage;
    }

    @Override
    public MainPage saveMainPage(MainPage mainPage) {
        logger.info("getMainPage() - Saving main page");
        MainPage mainPage1 = mainPageRepository.save(mainPage);
        logger.info("getMainPage() - Main page was saved");
        return mainPage1;
    }
}
