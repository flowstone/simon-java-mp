package me.xueyao.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.xueyao.base.R;
import me.xueyao.config.MpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户分组
 * @author Simon.Xue
 * @date 2020-03-08 21:51
 **/
@RestController
@RequestMapping("/userTag")
@Slf4j
public class UserTagController {

    @Autowired
    private BaseController baseControllero;
    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    public R create(@RequestBody JSONObject request) {
        R<Map<String, String>> mapR = handleMap();
        if (!mapR.getSuccess()) {
            return mapR;
        }
        String body = restTemplate.postForEntity(mpConfig.getUserTagCreate(), request, String.class, mapR.getData()).getBody();
        JSONObject result = JSONObject.parseObject(body);
        int errcode = result.getIntValue("errcode");
        if (0 != errcode) {
            log.warn("创建用户标签失败，{}", result);
            return R.ofParam("创建用户标签失败");
        }

        log.info("创建用户标签成功,{}", result);
        return R.ofSuccess("创建用户标签成功", result);
    }

    @GetMapping("/get")
    public R get() {
        R<Map<String, String>> mapR = handleMap();
        if (!mapR.getSuccess()) {
            return R.ofParam(mapR.getMessage());
        }
        String body = restTemplate.getForEntity(mpConfig.getUserTagGet(),String.class, mapR.getData()).getBody();
        JSONObject result = JSONObject.parseObject(body);
        int errcode = result.getIntValue("errcode");
        if (0 != errcode) {
            log.warn("获取用户标签失败，{}", result);
            return R.ofParam("获取用户标签失败");
        }

        log.info("获取用户标签成功,{}", result);
        return R.ofSuccess("获取用户标签成功", result);
    }

    @PostMapping("/update")
    public R update(@RequestBody JSONObject request) {
        R<Map<String, String>> mapR = handleMap();
        if (!mapR.getSuccess()) {
            return mapR;
        }
        String body = restTemplate.postForEntity(mpConfig.getUserTagUpdate(), request,String.class, mapR.getData()).getBody();
        JSONObject result = JSONObject.parseObject(body);
        int errcode = result.getIntValue("errcode");
        if (0 != errcode) {
            log.warn("更新用户标签失败，{}", result);
            return R.ofParam("更新用户标签失败");
        }

        log.info("更新用户标签成功,{}", result);
        return R.ofSuccess("更新用户标签成功", result);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody JSONObject request) {
        R<Map<String, String>> mapR = handleMap();
        if (!mapR.getSuccess()) {
            return mapR;
        }
        String body = restTemplate.postForEntity(mpConfig.getUserTagDelete(), request,String.class, mapR.getData()).getBody();
        JSONObject result = JSONObject.parseObject(body);
        int errcode = result.getIntValue("errcode");
        if (0 != errcode) {
            log.warn("删除用户标签失败，{}", result);
            return R.ofParam("删除用户标签失败");
        }

        log.info("删除用户标签成功,{}", result);
        return R.ofSuccess("删除用户标签成功", result);
    }


    public R<Map<String, String>> handleMap() {
        R<String> accessTokenR = baseControllero.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return R.ofParam(accessTokenR.getMessage());
        }

        Map<String, String> map = new HashMap<>(16);
        map.put("ACCESS_TOKEN", accessTokenR.getData());
        return R.ofSuccess("查询成功", map);
    }


}
