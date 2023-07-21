package project.service.impl;

import org.springframework.stereotype.Service;
import project.entity.News;
import project.repository.NewsRepository;
import project.service.NewsService;

import java.util.List;
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }
    @Override
    public News saveNews(News news) {
        return newsRepository.save(news);
    }
    @Override
    public News getNewById(Long id) {
        return newsRepository.findById(id).get();
    }

    @Override
    public News updateNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }
}
