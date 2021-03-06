package com.rescue.team.service;

import com.rescue.team.bean.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    boolean insertTask(Task task);

    Task getTaskByTid(String tid);

    Task getTaskByCode(String code);

    boolean changeTask(Task task);

    boolean deleteTask(String tid);

    List<Task> getGoingTasksByDistrict(String district);

    List<Task> getTaskByByDistrict(String district);

    List<Task> getTasks();

    boolean taskIsGoing(String eid);
}
