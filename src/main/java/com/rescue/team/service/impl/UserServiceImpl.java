package com.rescue.team.service.impl;

import com.rescue.team.bean.User;
import com.rescue.team.dao.UserDao;
import com.rescue.team.service.UserService;
import com.rescue.team.utils.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public boolean insertUser(User user) {
        log.info("手机号为"+user.getTel()+"的用户开始进行插入操作");
        try {
            user.setUid(IdUtil.generateId());
            boolean b = userDao.insertUser(user);
            log.info("手机号为"+user.getTel()+"的用户进行插入操作结束");
            return b;
        }catch (Exception e) {
            log.info(e.toString());
            log.info("手机号为"+user.getTel()+"的用户进行插入操作异常：");
            return false;
        }
    }

    @Override
    public User getUserByTel(String tel) {
        log.info("手机号为"+tel+"的用户开始进行查询操作");
        try {
            User user = userDao.getUserByTel(tel);
            log.info("手机号为"+tel+"的用户进行查询操作结束");
            return user;
        } catch (Exception e) {
            log.info("手机号为"+tel+"的用户进行查询操作异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public User getUserByUid(String uid) {
        log.info("用户ID为"+uid+"的用户开始进行查询操作");
        try {
            User user = userDao.getUserByUid(uid);
            log.info("用户ID为"+uid+"的用户进行查询操作结束");
            return user;
        } catch (Exception e) {
            log.info("用户ID为"+uid+"的用户进行查询操作异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public boolean changeUser(User user) {
        log.info("手机号为"+user.getTel()+"的用户开始进行更改操作");
        try {
            boolean b = userDao.changeUser(user);
            log.info("手机号为"+user.getTel()+"的用户进行更改操作结束");
            return b;
        } catch (Exception e) {
            log.info("手机号为"+user.getTel()+"的用户进行更改操作异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean deleteUserByUid(String uid) {
        log.info("用户ID为"+uid+"的用户开始进行删除操作");
        try {
            boolean b = userDao.deleteUserByUid(uid);
            log.info("用户ID为"+uid+"的用户进行删除操作结束");
            return b;
        } catch (Exception e) {
            log.info("用户ID为"+uid+"的用户进行删除操作异常：");
            log.info(e.toString());
            return false;
        }
    }
}
