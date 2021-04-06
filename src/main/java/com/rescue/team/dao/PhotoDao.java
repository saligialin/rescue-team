package com.rescue.team.dao;

import com.rescue.team.bean.Photo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhotoDao {

    boolean insertPhoto(Photo photo);

    Photo getPhotoByEid(String eid);

    boolean changePhoto(Photo photo);

    boolean deletePhoto(String pid);
}
