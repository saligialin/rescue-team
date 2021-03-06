package com.rescue.team.service;

import com.rescue.team.bean.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean insertUser(User user);

    User getUserByTel(String tel);

    User getUserByUid(String uid);

    boolean changeUser(User user);

    boolean changePassword(String password, String uid);

    boolean deleteUserByUid(String uid);

    boolean notVolunteer(String uid);
}
