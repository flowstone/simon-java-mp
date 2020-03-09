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
 * 短链接
 * @author Simon.Xue
 * @date 2020-03-09 15:29
 **/
@RestController
@RequestMapping("/shortUrl")
@Slf4j
public class ShortUrlController {
    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private BaseController baseController;
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
        String resultStr = restTemplate.postForEntity(mpConfig.getShortUrl(), request, String.class, param).getBody();
        JSONObject resultJson = JSONObject.parseObject(resultStr);
        int errcode = resultJson.getIntValue("errcode");
        if (0 != errcode) {
            log.warn("生成短链接失败，{}", resultJson);
            return R.ofParam("生成短链接失败");
        }
        log.info("生成短链接成功，{}", resultJson);
        return R.ofSuccess("生成短链接成功", resultJson);

    }
}
