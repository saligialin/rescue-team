package com.rescue.team.dao;

import com.rescue.team.bean.Volunteer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VolunteerDao {

    boolean insertVolunteer(Volunteer volunteer);

    Volunteer getVolunteerByVid(String vid);

    Volunteer getVolunteerByTel(String tel);

    boolean changeVolunteer(Volunteer volunteer);

    boolean deleteVolunteer(String vid);

    boolean passVerification(String vid);

    boolean beBusy(String vid);

    boolean beIdle(String vid);

    List<String> getTelsByArea(String area);
}
