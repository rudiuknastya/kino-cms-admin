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
import project.repository.ShareRepository;
import project.service.ShareService;

import java.util.List;
@Service
public class ShareServiceImpl implements ShareService {
    private final ShareRepository shareRepository;

    public ShareServiceImpl(ShareRepository shareRepository) {
        this.shareRepository = shareRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");
    @Override
    public List<Share> getAllShares() {
        logger.info("getAllShares() - Finding all shares");
        List<Share> shareList = shareRepository.findAll();
        logger.info("getAllShares() - All shares were found");
        return shareList;
    }

    @Override
    public Page<Share> getSharesWithPagination(int pageNumber, int pageSize) {
        logger.info("getSharesWithPagination() - Finding all shares with pagination");
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);
        Page<Share> shares = shareRepository.findByStatusNot(false,pageable);
        logger.info("getSharesWithPagination() - All shares with pagination were found");
        return shares;
    }

    @Override
    public Share saveShare(Share share) {
        logger.info("saveShare() - Saving share");
        Share savedShare = shareRepository.save(share);
        logger.info("saveShare() - Share was saved");
        return savedShare;
    }

    @Override
    public Share getShareById(Long id) {
        logger.info("getShareById() - Finding share by id "+id);
        Share foundShare = shareRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        logger.info("getShareById() - Share was found");
        return foundShare;
    }

    @Override
    public void deleteShareById(Long id) {
        logger.info("deleteShareById() - Deleting share by id "+id);
        shareRepository.deleteById(id);
        logger.info("deleteShareById() - Share was deleted");
    }
}
