package project.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.entity.News;
import project.entity.Share;
import project.repository.NewsRepository;
import project.service.NewsService;

import java.util.List;
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");
    @Override
    public List<News> getAllNews() {
        logger.info("getAllNews() - Finding all news");
        List<News> newsList = newsRepository.findAll();
        logger.info("getAllNews() - All news were found");
        return newsList;
    }

    @Override
    public Page<News> getNewsWithPagination(int pageNumber, int pageSize) {
        logger.info("getNewsWithPagination() - Finding all news with pagination");
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<News> news = newsRepository.findByStatusNot(false,pageable);
        logger.info("getNewsWithPagination() - All news with pagination were found");
        return news;
    }

    @Override
    public News saveNews(News news) {
        logger.info("saveNews() - Saving news");
        News savedNews = newsRepository.save(news);
        logger.info("saveNews() - News was saved");
        return savedNews;
    }
    @Override
    public News getNewById(Long id) {
        logger.info("getNewById() - Finding news by id "+id);
        News foundNews = newsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        logger.info("getNewById() - News was found");
        return foundNews;
    }

    @Override
    public News updateNews(News news) {
        logger.info("updateNews() - Updating news");
        News updatedNews = newsRepository.save(news);
        logger.info("updateNews() - News was updated");
        return updatedNews;
    }

    @Override
    public void deleteNewsById(Long id) {
        logger.info("deleteNewsById() - Deleting news by id "+id);
        newsRepository.deleteById(id);
        logger.info("deleteNewsById() - News was deleted");
    }
}
