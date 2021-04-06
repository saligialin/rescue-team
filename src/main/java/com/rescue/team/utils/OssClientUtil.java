package com.rescue.team.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

public class OssClientUtil {

    private static String endpoint = "https://oss-cn-shanghai.aliyuncs.com";

    private static String accessKeyId = "LTAI5t9y69kbzo8rntCmkW9R";

    private static String accessKeySecret = "m6qQBuZut17W45Ne6UkYMC3poB0Pi4";

    public static OSS getOss() {
        return new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
    }
}
