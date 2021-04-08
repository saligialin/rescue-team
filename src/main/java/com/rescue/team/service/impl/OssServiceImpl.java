package com.rescue.team.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.rescue.team.service.OssService;
import com.rescue.team.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.endpoint}")
    private String accessKeyId;

    @Value("${oss.endpoint}")
    private String accessKeySecret;

    @Value("${oss.endpoint}")
    private String bucketName;

    @Value("${oss.endpoint}")
    private String domain;

    @Value("${oss.endpoint}")
    private String tail;

    @Override
    public String uploadFile(MultipartFile file, String id, String type) {
        String filePath = null;
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            filePath = id + "/" + UuidUtil.getUUID() + "." + type;
            ossClient.putObject(bucketName,filePath,inputStream);
        } catch (IOException e) {
            log.info(e.toString());
            return "error";
        }

        return domain+filePath+tail;
    }

    @Override
    public String uploadFile(MultipartFile file, String type) {
        String filePath = null;
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        Calendar now = Calendar.getInstance();
        String year = ""+now.get(Calendar.YEAR);

        try {
            InputStream inputStream = file.getInputStream();
            filePath = year + "/" + UuidUtil.getUUID() + "." + type;
            ossClient.putObject(bucketName,filePath,inputStream);
        } catch (IOException e) {
            log.info(e.toString());
            return "error";
        }

        return domain+filePath+tail;
    }
}
