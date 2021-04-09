package com.rescue.team.service.impl;

import com.rescue.team.bean.Elder;
import com.rescue.team.dao.ElderDao;
import com.rescue.team.service.ElderService;
import com.rescue.team.service.FaceService;
import com.rescue.team.utils.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ElderServiceImpl implements ElderService {

    @Autowired
    private ElderDao elderDao;

    @Override
    public String insertElder(Elder elder) {
        log.info("开始插入操作老年人信息");
        try {
            elder.setEid(IdUtil.generateId());
            elder.setStatus(0);
            boolean b = elderDao.insertElder(elder);
            log.info("插入操作老年人信息结束");
            if(b) return elder.getEid();
            else return null;
        } catch (Exception e) {
            log.info("插入操作老年人信息异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public Elder getElderByEid(String eid) {
        log.info("开始查询老年人ID为"+eid+"的信息");
        try {
            Elder elder = elderDao.getElderByEid(eid);
            log.info("查询老年人ID为"+eid+"的信息结束");
            return elder;
        } catch (Exception e) {
            log.info("查询老年人ID为"+eid+"的信息异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public boolean changeElder(Elder elder) {
        log.info("开始更改老年人ID为"+elder.getEid()+"的信息");
        try {
            boolean b = elderDao.changeElder(elder);
            log.info("更改老年人ID为"+elder.getEid()+"的信息结束");
            return b;
        } catch (Exception e) {
            log.info("更改老年人ID为"+elder.getEid()+"的信息异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean deleteElder(String eid) {
        log.info("开始删除老年人ID为"+eid+"的老人信息");
        try {
            boolean b = elderDao.deleteElder(eid);
            log.info("删除老年人ID为"+eid+"的老人信息结束");
            return b;
        } catch (Exception e) {
            log.info("删除老年人ID为"+eid+"的老人信息异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean passVerification(String eid) {
        log.info("开始通过老年人ID为"+eid+"的审核");
        try {
            boolean b = elderDao.passVerification(eid);
            log.info("通过老年人ID为"+eid+"的审核结束");
            return b;
        } catch (Exception e) {
            log.info("通过老年人ID为"+eid+"的审核异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public List<Elder> getElderByUid(String uid) {
        log.info("正在获取用户ID为"+uid+"绑定的老人信息");
        try {
            List<Elder> elders = elderDao.getElderByUid(uid);
            log.info("获取用户ID为"+uid+"绑定的老人信息结束");
            return elders;
        } catch (Exception e) {
            log.info("获取用户ID为"+uid+"绑定的老人信息异常：");
            log.info(e.toString());
            return null;
        }
    }
}
