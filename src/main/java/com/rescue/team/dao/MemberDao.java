package com.rescue.team.dao;

import com.rescue.team.bean.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {

    boolean insertMember(Member member);

    List<Member> getMemberByTid(String tid);

    List<Member> getMemberByVid(String vid);

    Member getMemberByTidAndVid(String tid, String vid);
}
