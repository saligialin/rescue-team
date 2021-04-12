package com.rescue.team;

import com.rescue.team.service.FaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamApplicationTests {

    @Autowired
    FaceService faceService;

    @Test
    void contextLoads() {
//
//        faceService.addEntity("slk");
//
//        faceService.addFace("slk","http://home-rescue.oss-cn-shanghai.aliyuncs.com/testslk/blueslk.jpg?auto-orient,1/quality,q_90");
//        faceService.addFace("slk","http://home-rescue.oss-cn-shanghai.aliyuncs.com/testslk/redslk.jpg?auto-orient,1/quality,q_90");
//        faceService.addFace("slk","http://home-rescue.oss-cn-shanghai.aliyuncs.com/testslk/whiteslk.jpg?auto-orient,1/quality,q_90");

        boolean b = faceService.searchFace("http://home-rescue.oss-cn-shanghai.aliyuncs.com/testslk/zqa.jpg?auto-orient,1/quality,q_90");
    }

}
