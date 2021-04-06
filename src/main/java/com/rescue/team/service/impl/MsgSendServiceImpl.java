package com.rescue.team.service.impl;

import com.rescue.team.service.MsgSendService;
import com.rescue.team.utils.VerificationCodeUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MsgSendServiceImpl implements MsgSendService {

    @Value("${tencentcloud.message.secretId}")
    private String secretId;

    @Value("${tencentcloud.message.secretKey}")
    private String secretKey;

    @Value("${tencentcloud.message.secretKey}")
    private String url;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean sendVerifiedCode(String tel) {
        try{
            Credential cred = new Credential(secretId, secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(url);

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);

            SendSmsRequest req = new SendSmsRequest();
            String nTel = "+86"+tel;
            String[] phoneNumberSet1 = {nTel};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setTemplateID("904170");
            req.setSign("归家行动救援平台");

            String code = VerificationCodeUtil.getCode();
            String[] templateParamSet1 = {code, "10"};
            req.setTemplateParamSet(templateParamSet1);

            req.setSmsSdkAppid("1400497762");

            SendSmsResponse resp = client.SendSms(req);
            redisTemplate.opsForValue().set(tel,code,10, TimeUnit.MINUTES);

            log.info(SendSmsResponse.toJsonString(resp));

            return true;
        } catch (TencentCloudSDKException e) {
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean sendTaskCode(List<String> tel, String code) {
        try{
            Credential cred = new Credential(secretId, secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(url);

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);

            SendSmsRequest req = new SendSmsRequest();

            String[] phoneNumberSet = new String[tel.size()];
            for (int i=0; i<tel.size(); i++ ) {
                phoneNumberSet[i] = "+86"+tel.get(i);
            }
            req.setPhoneNumberSet(phoneNumberSet);

            req.setTemplateID("904364");
            req.setSign("归家行动救援平台");

            String[] templateParamSet1 = {code,"30"};
            req.setTemplateParamSet(templateParamSet1);

            req.setSmsSdkAppid("1400497762");

            SendSmsResponse resp = client.SendSms(req);
            log.info(SendSmsResponse.toJsonString(resp));

            return true;
        } catch (TencentCloudSDKException e) {
            log.info(e.toString());
            return false;
        }
    }

    @Override
    public boolean checkCode(String tel, String code) {
        String redisCode = redisTemplate.opsForValue().get(tel);
        if(redisCode.equals(code)) return true;
        else return false;
    }

}
