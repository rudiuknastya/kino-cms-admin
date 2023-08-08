package project.service;

import project.entity.NewPage;

import java.util.List;

public interface NewPageService {
    List<NewPage> getAllNewPages();
    NewPage saveNewPage(NewPage newPage);
    NewPage getNewPageById(Long id);
    void deleteNewPageById(Long id);
}
