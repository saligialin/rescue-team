package com.rescue.team.service;

import com.rescue.team.bean.Elder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ElderService {

    boolean insertElder(Elder elder);

    Elder getElderByEid(String eid);

    boolean changeElder(Elder elder);

    boolean deleteElder(String eid);

    boolean passVerification(String eid);

    List<Elder> getElderByUid(String uid);
}
