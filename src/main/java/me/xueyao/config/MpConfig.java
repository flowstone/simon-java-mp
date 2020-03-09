package me.xueyao.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Simon.Xue
 * @date 2020-03-03 12:12
 **/
@Configuration
@Getter
public class MpConfig {

    @Value("${mp.appId}")
    private String appId;

    @Value("${mp.appSecret}")
    private String appSecret;

    @Value("${mp.token}")
    private String token;


    @Value("${base.accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${base.callbackIpUrl}")
    private String callbackIpUrl;

    @Value("${base.apiIpUrl}")
    private String apiIpUrl;

    /**
     * --------- 客服中心 ---------
     */
    @Value("${talk.serviceCenter.add}")
    private String serviceCenterAdd;

    @Value("${talk.serviceCenter.modify}")
    private String serviceCenterModify;

    @Value("${talk.serviceCenter.remove}")
    private String serviceCenterRemove;

    @Value("${talk.serviceCenter.list}")
    private String serviceCenterList;

    @Value("${talk.serviceCenter.sendMessage}")
    private String serviceCenterSendMessage;

    /**
     * ------------- 群发消息 ------------
     */

    @Value("${talk.batchSend.uploadImg}")
    private String uploadImage;

    @Value("${talk.batchSend.uploadnews}")
    private String uploadNews;

    @Value("${talk.batchSend.sendAllToTag}")
    private String sendAllToTag;

    @Value("${talk.batchSend.sendOpenId}")
    private String sendOpenId;

    @Value("${talk.batchSend.uploadVideo}")
    private String uploadVideo;

    @Value("${talk.batchSend.delete}")
    private String delete;

    @Value("${talk.batchSend.preview}")
    private String preview;

    @Value("${talk.batchSend.getSendStatus}")
    private String getSendStatus;

    @Value("${talk.batchSend.getSendSpeed}")
    private String getSendSpeed;

    @Value("${talk.batchSend.setSendSpeed}")
    private String setSendSpeed;

    /**
     * -------------素材管理----------------
     */
    @Value("${talk.media.upload}")
    private String mediaUpload;

    @Value("${talk.media.list}")
    private String mediaList;

    /**
     * -------------模板管理--------------
     */
    @Value("${talk.template.send}")
    private String templateSend;

    /**
     * 用户标签管理
     */
    @Value("${talk.userTag.create}")
    private String userTagCreate;

    @Value("${talk.userTag.get}")
    private String userTagGet;

    @Value("${talk.userTag.update}")
    private String userTagUpdate;

    @Value("${talk.userTag.delete}")
    private String userTagDelete;

    /**
     * -------------用户备注----------
     */
    @Value("${talk.userRemark.update}")
    private String userRemarkUpdate;

    @Value("${talk.user.getBaseInfo}")
    private String getUserBaseInfo;

}
