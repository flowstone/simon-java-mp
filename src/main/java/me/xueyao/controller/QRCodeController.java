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
 * @author Simon.Xue
 * @date 2020-03-09 15:00
 **/
@RestController
@RequestMapping("/qrCode")
@Slf4j
public class QRCodeController {
    @Autowired
    private BaseController baseController;
    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    public R create(@RequestBody JSONObject request) {
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return accessTokenR;
        }

        Map<String, String> param = new HashMap<>(16);
        param.put("ACCESS_TOKEN", accessTokenR.getData());
        String resultStr = restTemplate.postForEntity(mpConfig.getQrCodeCreate(), request, String.class, param).getBody();
        JSONObject resultJson = JSONObject.parseObject(resultStr);
        log.info("生成临时二维码，{}", resultJson);
        return R.ofSuccess("生成临时二维码成功", resultJson);

    }

    @PostMapping("/createEver")
    public R createEver(@RequestBody JSONObject request) {
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return accessTokenR;
        }

        Map<String, String> param = new HashMap<>(16);
        param.put("ACCESS_TOKEN", accessTokenR.getData());
        String resultStr = restTemplate.postForEntity(mpConfig.getQrCodeCreateEver(), request, String.class, param).getBody();
        JSONObject resultJson = JSONObject.parseObject(resultStr);
        log.info("生成永久二维码，{}", resultJson);
        return R.ofSuccess("生成永久二维码成功", resultJson);

    }
}
