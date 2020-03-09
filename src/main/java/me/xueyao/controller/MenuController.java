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
 * @date 2020-03-09 15:47
 **/
@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private BaseController baseController;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    public R create(@RequestBody JSONObject request) {
        R<String> accessToken = baseController.getAccessToken();
        if (!accessToken.getSuccess()) {
            return accessToken;
        }

        Map<String, String> param = new HashMap<>(16);
        param.put("ACCESS_TOKEN", accessToken.getData());
        String resultStr = restTemplate.postForEntity(mpConfig.getMenuCreate(), request, String.class, param).getBody();
        JSONObject resultJson = JSONObject.parseObject(resultStr);
        int errcode = resultJson.getIntValue("errcode");
        if (errcode != 0) {
            log.warn("创建自定义菜单失败,{}", resultJson);
            return R.ofParam("创建自定义菜单失败");
        }

        log.info("创建自定义菜单成功，{}", resultJson);
        return R.ofSuccess("创建自定义菜单成功", resultJson);
    }
}
