package com.rescue.team.service.impl;

import com.rescue.team.bean.Elder;
import com.rescue.team.bean.Task;
import com.rescue.team.bean.Volunteer;
import com.rescue.team.dao.ElderDao;
import com.rescue.team.dao.VolunteerDao;
import com.rescue.team.service.VolunteerService;
import com.rescue.team.utils.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    VolunteerDao volunteerDao;

    @Autowired
    ElderDao elderDao;

    @Override
    public boolean insertVolunteer(Volunteer volunteer) {
        log.info("开始插入新的志愿者信息");
        try {
            volunteer.setVid(IdUtil.generateId());
            volunteer.setStatus(0);
            boolean b = volunteerDao.insertVolunteer(volunteer);
            log.info("插入新的志愿者信息结束");
            return b;
        } catch (Exception e) {
            log.info("插入新的志愿者信息异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public Volunteer getVolunteerByVid(String vid) {
        log.info("正在查询志愿者ID为"+vid+"的志愿者信息");
        try {
            Volunteer volunteer = volunteerDao.getVolunteerByVid(vid);
            log.info("查询志愿者ID为"+vid+"的志愿者信息结束");
            return volunteer;
        } catch (Exception e) {
            log.info("查询志愿者ID为"+vid+"的志愿者信息异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public Volunteer getVolunteerByTel(String tel) {
        log.info("正在查询手机号码为"+tel+"的志愿者信息");
        try {
            Volunteer volunteer = volunteerDao.getVolunteerByTel(tel);
            log.info("查询手机号码为"+tel+"的志愿者信息结束");
            return volunteer;
        } catch (Exception e) {
            log.info("查询手机号码为"+tel+"的志愿者信息异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public boolean changeVolunteer(Volunteer volunteer) {
        log.info("正在更改志愿者ID为"+volunteer.getVid()+"的志愿者信息");
        try {
            boolean b = volunteerDao.changeVolunteer(volunteer);
            log.info("更改志愿者ID为"+volunteer.getVid()+"的志愿者信息结束");
            return b;
        } catch (Exception e) {
            log.info("更改志愿者ID为"+volunteer.getVid()+"的志愿者信息异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean deleteVolunteer(String vid) {
        log.info("正在删除志愿者ID为"+vid+"的志愿者信息");
        try {
            boolean b = volunteerDao.deleteVolunteer(vid);
            log.info("删除志愿者ID为"+vid+"的志愿者信息结束");
            return b;
        } catch (Exception e) {
            log.info("删除志愿者ID为"+vid+"的志愿者信息异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean passVerification(String vid) {
        log.info("开始通过志愿者ID为"+vid+"的审核");
        try {
            boolean b = volunteerDao.passVerification(vid);
            log.info("通过志愿者ID为"+vid+"的审核结束");
            return b;
        } catch (Exception e) {
            log.info("通过志愿者ID为"+vid+"的审核异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean beBusy(String vid) {
        log.info("志愿者ID为"+vid+"的志愿者开始繁忙");
        try {
            boolean b = volunteerDao.beBusy(vid);
            log.info("志愿者ID为"+vid+"的志愿者更改状态成功");
            return b;
        } catch (Exception e) {
            log.info("志愿者ID为"+vid+"的志愿者更改状态异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean beIdle(String vid) {
        log.info("志愿者ID为"+vid+"的志愿者开始空闲");
        try {
            boolean b = volunteerDao.beIdle(vid);
            log.info("志愿者ID为"+vid+"的志愿者更改状态成功");
            return b;
        } catch (Exception e) {
            log.info("志愿者ID为"+vid+"的志愿者更改状态异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean beFault(String vid) {
        log.info("志愿者ID为"+vid+"的志愿者设备故障报备");
        try {
            boolean b = volunteerDao.beFault(vid);
            log.info("志愿者ID为"+vid+"的志愿者更改状态成功");
            return b;
        } catch (Exception e) {
            log.info("志愿者ID为"+vid+"的志愿者更改状态异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public List<String> getVolunteerTels(Task task) {
        Integer countByDistrict = volunteerDao.getCountByDistrict(task.getDistrict());
        Elder elder = elderDao.getElderByEid(task.getEid());
        if(countByDistrict<elder.getLevel()*10+10) {
            return volunteerDao.getTelsByCity(task.getCity());
        } else {
            return volunteerDao.getTelsByDistrict(task.getDistrict());
        }
    }
}
