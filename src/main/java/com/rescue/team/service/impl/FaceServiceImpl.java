package com.rescue.team.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.facebody20191230.Client;
import com.aliyun.facebody20191230.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.rescue.team.service.FaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FaceServiceImpl implements FaceService {

    @Value("${ali.face.accessKeyId}")
    private String accessKeyId;

    @Value("${ali.face.accessKeySecret}")
    private String accessKeySecret;

    @Value("${ali.face.endpoint}")
    private String endpoint;

    @Value("${ali.face.DbName}")
    private String DbName;

    public Client getClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = endpoint;
        return new Client(config);
    }

    public boolean addEntity(String id) {
        try {
            Client client = getClient();
            AddFaceEntityRequest addFaceEntityRequest = new AddFaceEntityRequest()
                    .setDbName("home_rescue")
                    .setEntityId(id);
            client.addFaceEntity(addFaceEntityRequest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addFace(String id, String url) {
        try {
            Client client = getClient();
            AddFaceRequest addFaceRequest = new AddFaceRequest()
                    .setDbName("home_rescue")
                    .setEntityId(id)
                    .setImageUrl(url);
            client.addFace(addFaceRequest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean searchFace(String url) {
        try {
            Client client = getClient();
            SearchFaceRequest request = new SearchFaceRequest()
                    .setDbName(DbName)
                    .setImageUrl(url)
                    .setLimit(1);

            SearchFaceResponse response = client.searchFace(request);
            String responseJson = JSON.toJSONString(response.getBody());
            JSONObject result = JSON.parseObject(responseJson);

            JSONObject data = (JSONObject)result.get("data");

            List matchList = (List) data.get("matchList");
            JSONObject matchListJson = JSON.parseObject(matchList.get(0).toString());

            List faceItems = (List) matchListJson.get("faceItems");
            JSONObject faceItemsJson = JSON.parseObject(faceItems.get(0).toString());

            Double score = faceItemsJson.getDouble("score");
            if(score>0.5) return true;
            else return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEntity(String id) {
        try {
            Client client = getClient();
            DeleteFaceEntityRequest request = new DeleteFaceEntityRequest()
                    .setDbName(DbName)
                    .setEntityId(id);
            client.deleteFaceEntity(request);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
