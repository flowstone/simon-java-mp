package me.xueyao.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.xueyao.base.R;
import me.xueyao.config.MpConfig;
import me.xueyao.dto.TagSendAllDto;
import me.xueyao.dto.UploadNewsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 群发消息
 * @author Simon.Xue
 * @date 2020-03-07 17:18
 **/
@RestController
@RequestMapping("/batchSend")
@Slf4j
public class BatchSendController {

    @Autowired
    private MpConfig mpConfig;
    @Autowired
    private BaseController baseController;

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 上传图文消息内的图片获取URL
     * @param media
     * @return
     */
    @PostMapping("/uploadImage")
    public R uploadImage(@RequestParam("media") MultipartFile media) throws IOException {
        if (media.isEmpty()) {
            return R.ofParam("图片必传");
        }
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return accessTokenR;
        }
        String url = mpConfig.getUploadImage() + accessTokenR.getData();
        File file = new File("/Users/simonxue/Desktop/aaa.png");
        media.transferTo(file);
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("media", new FileSystemResource(file));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, httpHeaders);
        String body = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        String errcode = jsonObject.getString("errcode");
        if (!StringUtils.isEmpty(errcode)) {
            return R.ofParam("上传图片失败");
        }

        file.delete();
        log.info("上传图片成功 = {}", jsonObject);
        return R.ofSuccess("上传图片成功", jsonObject.getString("url"));
    }

    /**
     * 上传图文消息素材
     * @param uploadNewsDto
     * @return
     */
    @PostMapping("/uploadNew")
    public R uploadNew(@RequestBody UploadNewsDto uploadNewsDto) {
        R<Map<String, String>> mapR = handleAccessToken();
        if (!mapR.getSuccess()) {
            return mapR;
        }

        String body = restTemplate.postForEntity(mpConfig.getUploadNews(), uploadNewsDto, String.class, mapR.getData()).getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        String errcode = jsonObject.getString("errcode");
        if (!StringUtils.isEmpty(errcode)) {
            log.warn("上传图文消息素材失败， {}", jsonObject);
            return R.ofParam("上传图文消息素材失败");
        }
        log.info("上传图文消息素材成功， {}", jsonObject);

        return R.ofSuccess("上传图文消息素材成功", jsonObject);
    }

    /**
     * 根据标签群发消息  图文消息
     * @param tagSendAllDto
     * @return
     */
    @PostMapping("/tagSendAll")
    public R tagSendAll(@RequestBody TagSendAllDto tagSendAllDto) {
        R<Map<String, String>> mapR = handleAccessToken();
        if (!mapR.getSuccess()) {
            return mapR;
        }
        String body = restTemplate.postForEntity(mpConfig.getSendAllToTag(), tagSendAllDto, String.class, mapR.getData()).getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        String errcode = jsonObject.getString("errcode");
        if (!StringUtils.isEmpty(errcode)) {
            log.warn("根据标签群发消息失败，{}", jsonObject);
            return R.ofParam("根据标签群发消息失败");
        }

        log.info("根据标签群发消息成功，{}", jsonObject);
        return R.ofSuccess("根据标签群发消息成功", jsonObject);
    }

    @PostMapping("/sendByOpenId")
    public R sendByOpenId(@RequestBody JSONObject jsonObject) {
        R<Map<String, String>> mapR = handleAccessToken();
        if (!mapR.getSuccess()) {
            return mapR;
        }
        String body = restTemplate.postForEntity(mpConfig.getSendOpenId(), jsonObject, String.class, mapR.getData()).getBody();
        JSONObject json = JSONObject.parseObject(body);
        int errcode = json.getIntValue("errcode");
        if (0 != errcode) {
            log.warn("根据OpenId群发消息失败，{}", json);
            return R.ofParam("根据OpenId群发消息失败");
        }

        log.info("根据OpenId群发消息成功，{}", json);
        return R.ofSuccess("根据OpenId群发消息成功", json);
    }


    /**
     * 处理Map   AccessToken
     * @return
     */
    public R<Map<String, String>> handleAccessToken() {
        R<String> accessTokenR = baseController.getAccessToken();
        if (!accessTokenR.getSuccess()) {
            return R.ofParam("获取AccessToken失败");
        }

        Map<String, String> map = new HashMap<>(16);
        map.put("ACCESS_TOKEN", accessTokenR.getData());
        return R.ofSuccess("查询成功", map);
    }




}
