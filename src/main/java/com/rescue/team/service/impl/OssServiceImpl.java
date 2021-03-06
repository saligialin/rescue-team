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

    @Value("${ali.oss.endpoint}")
    private String endpoint;

    @Value("${ali.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${ali.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${ali.oss.bucketName}")
    private String bucketName;

    @Value("${ali.oss.domain}")
    private String domain;

    @Value("${ali.oss.tail}")
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
        String data = ""+now.get(Calendar.YEAR);    //年
        if((now.get(Calendar.MONTH) + 1)<10) {
            data += "0"+(now.get(Calendar.MONTH) + 1);
        } else {
            data += (now.get(Calendar.MONTH) + 1);
        }
        //日
        if(now.get(Calendar.DAY_OF_MONTH)<10) {
            data += "0"+now.get(Calendar.DAY_OF_MONTH);
        } else {
            data += now.get(Calendar.DAY_OF_MONTH);
        }

        try {
            InputStream inputStream = file.getInputStream();
            filePath = data + "/" + UuidUtil.getUUID() + "." + type;
            ossClient.putObject(bucketName,filePath,inputStream);
        } catch (IOException e) {
            log.info(e.toString());
            return "error";
        }

        return domain+filePath+tail;
    }

    @Override
    public String uploadFace(MultipartFile file, String type) {
        String filePath = null;
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            filePath = "face/" + UuidUtil.getUUID() + "." + type;
            ossClient.putObject(bucketName,filePath,inputStream);
        } catch (IOException e) {
            log.info(e.toString());
            return "error";
        }

        return domain+filePath+tail;
    }
}
