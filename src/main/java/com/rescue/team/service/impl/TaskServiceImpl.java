package com.rescue.team.service.impl;

import com.rescue.team.bean.Task;
import com.rescue.team.dao.TaskDao;
import com.rescue.team.service.TaskService;
import com.rescue.team.utils.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public boolean insertTask(Task task) {
        log.info("正在插入新救援任务");
        try {
            task.setEid(IdUtil.generateId());
            boolean b = taskDao.insertTask(task);
            log.info("插入新救援任务结束");
            return b;
        } catch (Exception e) {
            log.info("插入新救援任务异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public Task getTaskByTid(String tid) {
        log.info("开始查询任务ID为" + tid + "的救援任务信息");
        try {
            Task task = taskDao.getTaskByTid(tid);
            log.info("查询任务ID为" + tid + "的救援任务信息结束");
            return task;
        } catch (Exception e) {
            log.info("查询任务ID为" + tid + "的救援任务信息异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public Task getTaskByCode(String code) {
        log.info("开始查询任务编号为" + code + "的救援任务信息");
        try {
            Task task = taskDao.getTaskByCode(code);
            log.info("查询任务编号为" + code + "的救援任务信息结束");
            return task;
        } catch (Exception e) {
            log.info("查询任务编号为" + code + "的救援任务信息异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public boolean changeTask(Task task) {
        log.info("开始更改任务ID为" + task.getTid() + "的任务");
        try {
            boolean b = taskDao.changeTask(task);
            log.info("更改任务ID为" + task.getTid() + "的任务结束");
            return b;
        } catch (Exception e) {
            log.info("更改任务ID为" + task.getTid() + "的任务异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean deleteTask(String tid) {
        log.info("开始删除任务ID为" + tid + "的任务");
        try {
            boolean b = taskDao.deleteTask(tid);
            log.info("删除任务ID为" + tid + "的任务结束");
            return b;
        } catch (Exception e) {
            log.info("删除任务ID为" + tid + "的任务异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public List<Task> getGoingTasksByDistrict(String district) {
        log.info("开始获取正在进行中的任务");
        try {
            List<Task> goingTasks = taskDao.getGoingTasksByDistrict(district);
            log.info("获取正在进行中的任务结束");
            return goingTasks;
        } catch (Exception e) {
            log.info("获取正在进行中的任务异常：");
            log.info(e.toString());
            return null;
        }
    }

}
