package project.service;

import org.springframework.data.domain.Page;
import project.entity.News;
import java.util.List;

public interface NewsService {
    List<News> getAllNews();
    Page<News> getNewsWithPagination(int pageNumber, int pageSize);
    News saveNews(News news);
    News getNewById(Long id);
    News updateNews(News news);
    void deleteNewsById(Long id);
}
