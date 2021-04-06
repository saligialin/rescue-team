package com.rescue.team.service.impl;

import com.rescue.team.bean.Member;
import com.rescue.team.dao.MemberDao;
import com.rescue.team.service.MemberService;
import com.rescue.team.utils.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Override
    public boolean insertMember(Member member) {
        log.info("有新的志愿者加入队伍，开始执行插入操作");
        try {
            member.setMid(IdUtil.generateId());
            boolean b = memberDao.insertMember(member);
            log.info("有新的志愿者加入队伍，插入操作结束");
            return b;
        } catch (Exception e) {
            log.info("有新的志愿者加入队伍，插入操作异常：");
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public List<Member> getMemberByTid(String tid) {
        log.info("正在查询任务ID为"+tid+"的所有参与志愿者的信息");
        try {
            List<Member> members = memberDao.getMemberByTid(tid);
            log.info("查询任务ID为"+tid+"的所有参与志愿者的信息结束");
            return members;
        } catch (Exception e) {
            log.info("查询任务ID为"+tid+"的所有参与志愿者的信息异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public List<Member> getMemberByVid(String vid) {
        log.info("正在查询志愿者ID为"+vid+"参与的任务的信息");
        try {
            List<Member> members = memberDao.getMemberByVid(vid);
            log.info("查询志愿者ID为"+vid+"参与的任务的信息结束");
            return members;
        } catch (Exception e) {
            log.info("查询志愿者ID为"+vid+"参与的任务的信息异常：");
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public Member getMemberByTidAndVid(String tid, String vid) {
        log.info("正在根据vid和tid查询任务信息");
        try {
            Member member = memberDao.getMemberByTidAndVid(tid, vid);
            log.info("根据vid和tid查询任务信息结束");
            return member;
        } catch (Exception e) {
            log.info("根据vid和tid查询任务异常：");
            log.info(e.toString());
            return null;
        }
    }
}
