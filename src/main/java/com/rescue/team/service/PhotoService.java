package com.rescue.team.service;

import com.rescue.team.bean.Photo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhotoService {

    boolean insertPhoto(Photo photo);

    Photo getPhotoByEid(String eid);

    boolean changePhoto(Photo photo);

    boolean deletePhoto(String pid);

    boolean insertOnePhoto(String eid, String photo, String which);

    List<String> getPhotoList(Photo photo);
}
