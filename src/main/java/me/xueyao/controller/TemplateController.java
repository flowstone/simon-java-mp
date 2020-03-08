package me.xueyao.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.xueyao.base.R;
import me.xueyao.config.MpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板消息接口文档
 * @author Simon.Xue
 * @date 2020-03-08 21:18
 **/
@RestController
@RequestMapping("/template")
@Slf4j
public class TemplateController {

    @Autowired
    private BaseController baseController;
    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送模板消息
     * @param json
     * @return
     */
    @PostMapping("/send")
    public R send(@RequestBody JSONObject json) {
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return accessTokenR;
        }

        String accessToken = accessTokenR.getData();
        Map<String, String> map = new HashMap<>();
        map.put("ACCESS_TOKEN", accessToken);
        String body = restTemplate.postForEntity(mpConfig.getTemplateSend(), json, String.class, map).getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        int errcode = jsonObject.getIntValue("errcode");
        if (0 != errcode) {
            log.warn("模板消息发送失败 = {}", jsonObject);
            return R.ofParam("模板消息发送失败");
        }

        log.info("模板消息发送成功 = {}", jsonObject);
        return R.ofSuccess("模板消息发送成功");
    }

}
