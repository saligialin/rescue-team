package com.rescue.team.dao;

import com.rescue.team.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    boolean insertUser(User user);

    User getUserByTel(String tel);

    User getUserByUid(String uid);

    boolean changeUser(User user);

    boolean deleteUserByUid(String uid);

    boolean notVolunteer(String uid);
}
