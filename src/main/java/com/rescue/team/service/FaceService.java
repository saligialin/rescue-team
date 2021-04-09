package com.rescue.team.service;

import org.springframework.stereotype.Service;

@Service
public interface FaceService {

    boolean addEntity(String id);

    boolean addFace(String id, String url);

    boolean searchFace(String url);

}
