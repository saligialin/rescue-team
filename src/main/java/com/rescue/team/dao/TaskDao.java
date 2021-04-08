package com.rescue.team.dao;

import com.rescue.team.bean.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskDao {

    boolean insertTask(Task task);

    Task getTaskByTid(String tid);

    Task getTaskByCode(String code);

    boolean changeTask(Task task);

    boolean deleteTask(String tid);

    List<Task> getGoingTasksByDistrict(String district);

}
