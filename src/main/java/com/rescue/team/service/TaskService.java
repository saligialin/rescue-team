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

    List<Task> getGoingTasks();

}
