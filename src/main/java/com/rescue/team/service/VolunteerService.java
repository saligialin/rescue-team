package com.rescue.team.service;

import com.rescue.team.bean.Task;
import com.rescue.team.bean.Volunteer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VolunteerService {

    boolean insertVolunteer(Volunteer volunteer);

    Volunteer getVolunteerByVid(String vid);

    Volunteer getVolunteerByTel(String tel);

    boolean changeVolunteer(Volunteer volunteer);

    boolean deleteVolunteer(String vid);

    boolean passVerification(String vid);

    boolean beBusy(String vid);

    boolean beIdle(String vid);

    boolean beFault(String vid);

    List<String> getVolunteerTels(Task task);
}
