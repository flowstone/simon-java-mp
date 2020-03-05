package me.xueyao.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.xueyao.base.R;
import me.xueyao.config.MpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon.Xue
 * @date 2020-03-03 19:44
 **/
@RestController
@RequestMapping("/base")
@Slf4j
public class BaseController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MpConfig mpConfig;

    /**
     * 获得唯一接口凭证
     * @return
     */
    @GetMapping("/getAccessToken")
    public R<String> getAccessToken() {
        Map<String, String> map = new HashMap<>(16);
        map.put("APPID", mpConfig.getAppId());
        map.put("APPSECRET", mpConfig.getAppSecret());
        String body = restTemplate.getForEntity(mpConfig.getAccessTokenUrl(), String.class, map).getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        String accessToken = jsonObject.getString("access_token");
        if (null == accessToken) {
            log.warn("获取accessToken失败");
            return R.ofSystem("获取accessToken失败");
        }

        log.info("获取accessToken成功，accessToken = {}", accessToken);
        return R.ofSuccess("获取accessToken成功", accessToken);
    }

    /**
     * 获得回调ip地址
     * @return
     */
    @GetMapping("/getCallbackIp")
    public R getCallbackIp() {
        R<String> resultR = handleRequest(mpConfig.getCallbackIpUrl());
        JSONObject jsonObject = JSONObject.parseObject(resultR.getData());
        String errcode = jsonObject.getString("errcode");

        if (null != errcode) {
            log.warn("获取回调ip地址失败，{}", jsonObject);
            return R.ofParam("获取回调ip地址失败");
        }

        log.info("获取回调ip地址成功，{}", jsonObject);
        return R.ofSuccess("获取回调ip地址成功", jsonObject);
    }

    /**
     * 获得API的IP列表
     * @return
     */
    @GetMapping("/getApiIp")
    public R getApiIp() {
        R<String> resultR = handleRequest(mpConfig.getApiIpUrl());
        JSONObject jsonObject = JSONObject.parseObject(resultR.getData());
        String errcode = jsonObject.getString("errcode");

        if (null != errcode) {
            log.warn("获取AIP Ip地址失败，{}", jsonObject);
            return R.ofParam("获取AIP Ip地址失败");
        }

        log.info("获取AIP Ip地址成功，{}", jsonObject);
        return R.ofSuccess("获取AIP Ip地址成功", jsonObject);

    }

    /**
     * 处理请求
     * @return
     */
    public R<String> handleRequest(String url) {
        R<String> accessTokenR = getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return accessTokenR;
        }

        Map<String, String> map = new HashMap<>(16);
        map.put("ACCESS_TOKEN", accessTokenR.getData());
        String body = restTemplate.getForEntity(url, String.class, map).getBody();
        return R.ofSuccess("处理结束", body);
    }


}
