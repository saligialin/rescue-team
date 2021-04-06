package com.rescue.team.dao;

import com.rescue.team.bean.Elder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ElderDao {

    boolean insertElder(Elder elder);

    Elder getElderByEid(String eid);

    boolean changeElder(Elder elder);

    boolean deleteElder(String eid);

    boolean passVerification(String eid);

    List<Elder> getElderByUid(String uid);
}
