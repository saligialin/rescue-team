package com.rescue.team.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface OssService {

    String uploadFile(MultipartFile file, String id, String type);

    String uploadFile(MultipartFile file, String type);

    String uploadFace(MultipartFile file, String type);
}
