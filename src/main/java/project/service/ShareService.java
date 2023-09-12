package project.service;


import org.springframework.data.domain.Page;
import project.entity.Share;

import java.util.List;

public interface ShareService {
    List<Share> getAllShares();
    Share saveShare(Share share);
    Share getShareById(Long id);
    void deleteShareById(Long id);
    Page<Share> getSharesWithPagination(int pageNumber, int pageSize);
}
