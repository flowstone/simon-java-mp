package me.xueyao.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.xueyao.base.R;
import me.xueyao.config.MpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon.Xue
 * @date 2020-03-09 14:30
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private BaseController baseController;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获得用户基本信息
     * @return
     */
    @GetMapping("/getUserBaseInfo")
    public R getUserBaseInfo(@RequestParam("openId") String openId) {
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return accessTokenR;
        }

        Map<String, String> param = new HashMap<>(16);
        param.put("ACCESS_TOKEN", accessTokenR.getData());
        param.put("OPENID", openId);
        String resultStr = restTemplate.getForEntity(mpConfig.getGetUserBaseInfo(), String.class, param).getBody();
        JSONObject resultJson = JSONObject.parseObject(resultStr);
        int errcode = resultJson.getIntValue("errcode");
        if (0 != errcode) {
            log.warn("获取用户基本信息失败，{}", resultJson);
            return R.ofParam("获取用户基本信息失败");
        }

        log.info("获取用户基本信息成功，{}", resultJson);
        return R.ofSuccess("获取用户基本信息成功", resultJson);
    }

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/userList")
    public R userList() {
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return accessTokenR;
        }

        Map<String, String> param = new HashMap<>(16);
        param.put("ACCESS_TOKEN", accessTokenR.getData());

        String resultStr = restTemplate.getForEntity(mpConfig.getUserList(), String.class, param).getBody();
        JSONObject resultJson = JSONObject.parseObject(resultStr);
        String errcode = resultJson.getString("errcode");
        if (!StringUtils.isEmpty(errcode)) {
            log.warn("获取用户列表失败，{}", resultJson);
            return R.ofParam("获取用户列表失败");
        }

        log.info("获取用户列表成功，{}", resultJson);
        return R.ofSuccess("获取用户列表成功", resultJson);
    }
}
