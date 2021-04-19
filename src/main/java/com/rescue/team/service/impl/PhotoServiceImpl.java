package com.rescue.team.service.impl;

import com.rescue.team.bean.Photo;
import com.rescue.team.dao.PhotoDao;
import com.rescue.team.service.PhotoService;
import com.rescue.team.utils.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoDao photoDao;

    @Override
    public boolean insertPhoto(Photo photo) {
        log.info("开始新增老人照片组");
        try {
            photo.setPid(IdUtil.generateId());
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

    @Override
    public boolean insertOnePhoto(String eid, String photo, String which) {
        log.info("为id为"+eid+"的老人增加"+which);
        try {
            boolean b = photoDao.insertOnePhoto(eid, photo, which);
            log.info("结束操作");
            return b;
        } catch (Exception e) {
            log.info("操作结束");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public List<String> getPhotoList(Photo photo) {
        log.info("开始将老人的照片封装在list中");
        List<String> list = new ArrayList<>();
        if (photo.getPhoto1()!=null) list.add(photo.getPhoto1());
        if (photo.getPhoto2()!=null) list.add(photo.getPhoto2());
        if (photo.getPhoto3()!=null) list.add(photo.getPhoto3());
        if (photo.getPhoto4()!=null) list.add(photo.getPhoto4());
        if (photo.getPhoto5()!=null) list.add(photo.getPhoto5());
        if (photo.getPhoto6()!=null) list.add(photo.getPhoto6());
        if (photo.getPhoto7()!=null) list.add(photo.getPhoto7());
        if (photo.getPhoto8()!=null) list.add(photo.getPhoto8());
        if (photo.getPhoto9()!=null) list.add(photo.getPhoto9());
        log.info("将老人的照片封装在list中结束");
        return list;
    }
}
