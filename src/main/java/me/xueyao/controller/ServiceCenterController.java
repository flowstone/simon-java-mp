package me.xueyao.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.xueyao.base.R;
import me.xueyao.config.MpConfig;
import me.xueyao.dto.KfAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon.Xue
 * @date 2020-03-07 09:54
 **/
@RestController
@RequestMapping("/serviceCenter")
@Slf4j
public class ServiceCenterController {

    @Autowired
    private BaseController baseController;
    @Autowired
    private MpConfig mpConfig;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/add")
    public R add(@RequestBody KfAccountDto kfAccountDto) {
        Map<String, String> map = handleServiceInfo(kfAccountDto);
        R<String> resultR = postRequest(mpConfig.getServiceCenterAdd(), map);
        if (!resultR.getSuccess()) {
            return R.ofParam(resultR.getMessage());
        }

        JSONObject jsonObject = JSONObject.parseObject(resultR.getData());
        String errcode = jsonObject.getString("errcode");
        if (!StringUtils.isEmpty(errcode)) {
            log.warn("添加客服账号失败 = {}", jsonObject);
            return R.ofParam("添加客服账号失败");
        }
        log.info("添加客服账号成功 = {}", jsonObject);
        return R.ofSuccess("添加客服账号成功");
    }

    @PostMapping("/modify")
    public R modify(@RequestBody KfAccountDto kfAccountDto) {
        Map<String, String> map = handleServiceInfo(kfAccountDto);
        R<String> resultR = postRequest(mpConfig.getServiceCenterModify(), map);
        if (!resultR.getSuccess()) {
            return R.ofParam(resultR.getMessage());
        }

        JSONObject jsonObject = JSONObject.parseObject(resultR.getData());
        String errcode = jsonObject.getString("errcode");
        if (!StringUtils.isEmpty(errcode)) {
            log.warn("修改客服账号失败 = {}", jsonObject);
            return R.ofParam("修改客服账号失败");
        }
        log.info("修改客服账号成功 = {}", jsonObject);
        return R.ofSuccess("修改客服账号成功");
    }

    @PostMapping("/remove")
    public R remove(@RequestBody KfAccountDto kfAccountDto) {
        Map<String, String> map = handleServiceInfo(kfAccountDto);
        R<String> resultR = postRequest(mpConfig.getServiceCenterRemove(), map);
        if (!resultR.getSuccess()) {
            return R.ofParam(resultR.getMessage());
        }

        JSONObject jsonObject = JSONObject.parseObject(resultR.getData());
        String errcode = jsonObject.getString("errcode");
        if (!StringUtils.isEmpty(errcode)) {
            log.warn("删除客服账号失败 = {}", jsonObject);
            return R.ofParam("删除客服账号失败");
        }
        log.info("删除客服账号成功 = {}", jsonObject);
        return R.ofSuccess("删除客服账号成功");
    }

    /**
     * 查询所有客户账号
     * @return
     */
    @GetMapping("/list")
    public R list() {
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return accessTokenR;
        }
        String accessToken = accessTokenR.getData();
        String url = mpConfig.getServiceCenterList() + accessToken;
        String body = restTemplate.getForEntity(url, String.class).getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        String errcode = jsonObject.getString("errcode");
        if (!StringUtils.isEmpty(errcode)) {
            log.warn("查询所有客服账号失败 = {}", jsonObject);
            return R.ofParam("查询所有客户账号失败");
        }
        log.info("查询所有客服账号成功 = {}", jsonObject);
        return R.ofSuccess("查询成功", jsonObject);
    }


    /**
     * POST请求
     * @param serviceUrl
     * @param map
     * @return
     */
    public R<String> postRequest(String serviceUrl, Map<String, String> map) {
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return R.ofParam(accessTokenR.getMessage());
        }

        String accessToken = accessTokenR.getData();
        String url = serviceUrl + accessToken;
        String body = restTemplate.postForEntity(url, Map.class, String.class, map).getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        String errcode = jsonObject.getString("errcode");
        if (!StringUtils.isEmpty(errcode)) {
            return R.ofParam(jsonObject.getString("errmsg"));
        }
        return R.ofSuccess(body);
    }

    public Map<String, String> handleServiceInfo(KfAccountDto kfAccountDto) {
        Map<String, String> map = new HashMap<>(16);
        map.put("kf_account", kfAccountDto.getKfAccount());
        map.put("nickname", kfAccountDto.getNickname());
        map.put("password", kfAccountDto.getPassword());
        return map;
    }
}
