package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.ChildrenRoom;
import project.repository.ChildrenRoomRepository;
import project.service.ChildrenRoomService;
@Service
public class ChildrenRoomServiceImpl implements ChildrenRoomService {
    private final ChildrenRoomRepository childrenRoomRepository;

    public ChildrenRoomServiceImpl(ChildrenRoomRepository childrenRoomRepository) {
        this.childrenRoomRepository = childrenRoomRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");
    @Override
    public ChildrenRoom saveChildrenRoom(ChildrenRoom childrenRoom) {
        logger.info("saveChildrenRoom() - Saving children room");
        ChildrenRoom childrenRoom1 = childrenRoomRepository.save(childrenRoom);
        logger.info("saveChildrenRoom() - Children room was saved");
        return childrenRoom1;
    }

    @Override
    public ChildrenRoom getChildrenRoom() {
        logger.info("getChildrenRoom() - Finding children room");
        ChildrenRoom childrenRoom = childrenRoomRepository.findById(1L).get();
        logger.info("getChildrenRoom() - Children room was found");
        return childrenRoom;
    }
}
