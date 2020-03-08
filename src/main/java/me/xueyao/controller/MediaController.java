package me.xueyao.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.xueyao.base.R;
import me.xueyao.config.MpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Simon.Xue
 * @date 2020-03-08 15:37
 **/
@RestController
@RequestMapping("/media")
@Slf4j
public class MediaController {

    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private BaseController baseController;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/uploadMedia")
    public R uploadImage(@RequestParam("media") MultipartFile media,
                         @RequestParam("type") String type) throws IOException {
        if (media.isEmpty()) {
            return R.ofParam("图片必传");
        }
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return accessTokenR;
        }
        String url = mpConfig.getMediaUpload() + accessTokenR.getData() + "&type=" + type;
        File file = new File("/Users/simonxue/Desktop/bbb.png");
        media.transferTo(file);

        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("media", new FileSystemResource(file));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestMap, httpHeaders);
        String body = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();

        JSONObject json = JSONObject.parseObject(body);
        String errcode = json.getString("errcode");
        if (!StringUtils.isEmpty(errcode)) {
            return R.ofParam("上传素材失败");
        }

        file.delete();
        log.info("上传素材成功 = {}", json);
        return R.ofSuccess("上传素材成功", json);
    }
}
