package com.rescue.team.service;

import com.rescue.team.bean.Photo;
import org.springframework.stereotype.Service;

@Service
public interface PhotoService {

    boolean insertPhoto(Photo photo);

    Photo getPhotoByEid(String eid);

    boolean changePhoto(Photo photo);

    boolean deletePhoto(String pid);

}
