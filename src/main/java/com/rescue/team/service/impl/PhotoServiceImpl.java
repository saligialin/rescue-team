package com.rescue.team.service.impl;

import com.rescue.team.bean.Photo;
import com.rescue.team.dao.PhotoDao;
import com.rescue.team.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoDao photoDao;

    @Override
    public boolean insertPhoto(Photo photo) {
        log.info("开始新增老人照片组");
        try {
            boolean b = photoDao.insertPhoto(photo);
            log.info("新增老人照片组结束");
            return b;
        } catch (Exception e) {
            log.info("新增老人照片组异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public Photo getPhotoByEid(String eid) {
        log.info("开始获取eid为"+eid+"的老人的照片");
        try {
            Photo photo = photoDao.getPhotoByEid(eid);
            log.info("获取eid为"+eid+"的老人的照片结束");
            return photo;
        } catch (Exception e) {
            log.info("获取eid为"+eid+"的老人的照片异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public boolean changePhoto(Photo photo) {
        log.info("开始更改eid为"+photo.getEid()+"的老人的照片");
        try {
            boolean b = photoDao.changePhoto(photo);
            log.info("更改eid为"+photo.getEid()+"的老人的照片结束");
            return b;
        } catch (Exception e) {
            log.info("更改eid为"+photo.getEid()+"的老人的照片异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean deletePhoto(String pid) {
        log.info("开始删除eid为"+pid+"的老人的照片");
        try {
            boolean b = photoDao.deletePhoto(pid);
            log.info("删除eid为"+pid+"的老人的照片结束");
            return b;
        } catch (Exception e) {
            log.info("删除eid为"+pid+"的老人的照片异常：");
            log.info(e.toString());
            return false;
        }
    }
}
