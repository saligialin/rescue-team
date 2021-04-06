package com.rescue.team.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MsgSendService {

    boolean sendVerifiedCode(String tel);

    boolean sendTaskCode(List<String> tel, String code);

    boolean checkCode(String tel, String code);
}
