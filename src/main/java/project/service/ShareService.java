package project.service;


import project.entity.Share;

import java.util.List;

public interface ShareService {
    List<Share> getAllShares();
    Share saveShare(Share share);
    Share getShareById(Long id);
    void deleteShareById(Long id);
}
