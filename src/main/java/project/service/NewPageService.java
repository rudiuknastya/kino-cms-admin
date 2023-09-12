package project.service;

import project.entity.NewPage;

import java.util.List;

public interface NewPageService {
    List<NewPage> getAllNewPages();
    List<NewPage> getEnabledNewPages();
    NewPage saveNewPage(NewPage newPage);
    NewPage getNewPageById(Long id);
    void deleteNewPageById(Long id);
}
