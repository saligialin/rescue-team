package com.rescue.team.service;

import com.rescue.team.bean.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    boolean insertMember(Member member);

    List<Member> getMemberByTid(String tid);

    List<Member> getMemberByVid(String vid);

    Member getMemberByTidAndVid(String tid, String vid);
}
