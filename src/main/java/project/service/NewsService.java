package project.service;

import project.entity.News;
import java.util.List;

public interface NewsService {
    List<News> getAllNews();
    News saveNews(News news);
}
