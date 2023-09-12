package project.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.NewPage;
import project.repository.NewPageRepository;
import project.service.NewPageService;

import java.util.List;
@Service
public class NewPageServiceImpl implements NewPageService {
    private final NewPageRepository newPageRepository;

    public NewPageServiceImpl(NewPageRepository newPageRepository) {
        this.newPageRepository = newPageRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");
    @Override
    public List<NewPage> getAllNewPages() {
        logger.info("getAllNewPages() - Finding all new pages");
        List<NewPage> newPages = newPageRepository.findAll();
        logger.info("getAllNewPages() - All new pages were found");
        return newPages;
    }

    @Override
    public List<NewPage> getEnabledNewPages() {
        logger.info("getEnabledNewPages() - Finding enabled new pages");
        List<NewPage> newPages = newPageRepository.enabledNewPages();
        logger.info("getEnabledNewPages() - Enabled new pages were found");
        return newPages;
    }

    @Override
    public NewPage saveNewPage(NewPage newPage) {
        logger.info("saveNewPage() - Saving new page");
        NewPage newPage1 = newPageRepository.save(newPage);
        logger.info("saveNewPage() - New page was saved");
        return newPage1;
    }

    @Override
    public NewPage getNewPageById(Long id) {
        logger.info("getNewPageById() - Finding new page by id "+id);
        NewPage newPage = newPageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        logger.info("getNewPageById() - New page was found");
        return newPage;
    }

    @Override
    public void deleteNewPageById(Long id) {
        logger.info("deleteNewPageById() - Deleting new page by id "+id);
        newPageRepository.deleteById(id);
        logger.info("deleteNewPageById() - New page was deleted");
    }
}
