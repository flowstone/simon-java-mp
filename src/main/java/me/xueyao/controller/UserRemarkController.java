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
 * 用户备注
 * @author Simon.Xue
 * @date 2020-03-09 14:11
 **/
@RestController
@RequestMapping("/userRemark")
@Slf4j
public class UserRemarkController {

    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private BaseController baseController;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 设置备注名
     * @param request
     * @return
     */
    @PostMapping("/update")
    public R updateRemark(@RequestBody JSONObject request) {
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return accessTokenR;
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("ACCESS_TOKEN", accessTokenR.getData());
        String httpBody = restTemplate.postForEntity(mpConfig.getUserRemarkUpdate(), request, String.class, paramMap).getBody();
        JSONObject resultJson = JSONObject.parseObject(httpBody);
        int errcode = resultJson.getIntValue("errcode");
        if (0 != errcode) {
            log.warn("用户设置备注名失败，{}", resultJson);
            return R.ofParam("用户设置备注名失败");
        }

        log.info("用户设置备注名成功，{}", resultJson);
        return R.ofSuccess("用户设置备注名成功", resultJson);
    }
}
