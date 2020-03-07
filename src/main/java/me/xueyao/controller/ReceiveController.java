package me.xueyao.controller;

import lombok.extern.slf4j.Slf4j;
import me.xueyao.enums.WxMpEnum;
import me.xueyao.util.SignHelper;
import me.xueyao.util.WxXmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon.Xue
 * @date 2020-03-05 21:16
 **/
@RestController
@RequestMapping("/receive")
@Slf4j
public class ReceiveController {

    @Autowired
    private SignHelper signHelper;

    @GetMapping("/validateSign")
    public String validateSign(@RequestParam("signature") String signature,
                               @RequestParam("timestamp") String timestamp,
                               @RequestParam("nonce") String nonce,
                               @RequestParam("echostr") String echostr) {
        log.info("signature = {}, timestamp = {}， nonce = {}， echostr = {}", signature, timestamp, nonce, echostr);
        if (signHelper.validate(signature, timestamp, nonce, echostr)) {
            log.info("校验签名成功，echostr= {}", echostr);
            return echostr;
        } else {
            return null;
        }
    }

    @PostMapping("/validateSign")
    public void receiveMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletInputStream inputStream = request.getInputStream();
        Map<String, String> map = WxXmlUtil.streamToMap(inputStream);
        String resultXml = handleMessage(map);
        //log.info("{}", resultXml);
        //把xml写到response中
        WxXmlUtil.writeToResponse(response.getOutputStream(), resultXml);
    }

    public String handleMessage(Map<String, String> map) throws Exception {
        String resultXmlStr = null;
        String msgType = map.get("MsgType");
        switch (msgType) {
            case "text":
                resultXmlStr = textMessage(map);
                break;
            case "image":
                resultXmlStr = imageMessage(map);
                break;
            case "voice":
                resultXmlStr = voiceMessage(map);
                break;
            case "video":
                resultXmlStr = videoMessage(map);
                break;
            case "music":
                resultXmlStr = musicMessage(map);
                break;
            case "news":
                break;
            case "event":
                resultXmlStr = subscribeMessage(map);
                break;
            default:
                map.put("Content", "该信息类型不支持");
                resultXmlStr = textMessage(map);

        }
        return resultXmlStr;
    }

    public String textMessage(Map<String, String> map) throws Exception {
        Map<String, String> resultMap = handleBaseMessage(map);
        resultMap.put("MsgType", "text");
        resultMap.put("Content", map.get("Content"));
        return WxXmlUtil.mapToXml(resultMap);
    }

    public String imageMessage(Map<String, String> map) throws Exception {
        Map<String, String> resultMap = handleBaseMessage(map);
        resultMap.put("MsgType", "image");
        resultMap.put("Image", "true");
        Map<String, String> innerMap = new HashMap<>(16);
        innerMap.put("MediaId", map.get("MediaId"));
        return WxXmlUtil.mapToXml(resultMap, innerMap);
    }

    public String voiceMessage(Map<String, String> map) throws Exception {
        Map<String, String> resultMap = handleBaseMessage(map);
        resultMap.put("MsgType", "voice");
        resultMap.put("Voice", "true");
        Map<String, String> innerMap = new HashMap<>(16);
        innerMap.put("MediaId", map.get("MediaId"));
        return WxXmlUtil.mapToXml(resultMap, innerMap);
    }

    public String videoMessage(Map<String, String> map) throws Exception {
        Map<String, String> resultMap = handleBaseMessage(map);
        resultMap.put("MsgType", "video");
        resultMap.put("Voice", "true");
        Map<String, String> innerMap = new HashMap<>(16);
        innerMap.put("MediaId", map.get("MediaId"));
        innerMap.put("Title", "测试视频标题");
        innerMap.put("Description", "测试视频描述");
        return WxXmlUtil.mapToXml(resultMap, innerMap);
    }


    public String musicMessage(Map<String, String> map) throws Exception {
        Map<String, String> resultMap = handleBaseMessage(map);
        resultMap.put("MsgType", "music");
        resultMap.put("Music", "true");
        Map<String, String> innerMap = new HashMap<>(16);
        innerMap.put("TitleTitle", "");
        innerMap.put("Description", "");
        innerMap.put("MusicUrl", "");
        innerMap.put("HQMusicUrl", "");
        innerMap.put("ThumbMediaId", "");
        return WxXmlUtil.mapToXml(resultMap, innerMap);
    }


    public Map<String, String> handleBaseMessage(Map<String, String> map) {
        Map<String, String> resultMap = new HashMap<>(16);
        resultMap.put("ToUserName", map.get("FromUserName"));
        resultMap.put("FromUserName", map.get("ToUserName"));
        resultMap.put("CreateTime", map.get("CreateTime"));
        return resultMap;
    }

    public String subscribeMessage(Map<String, String> map) throws Exception {
        Map<String, String> resultMap = handleBaseMessage(map);
        resultMap.put("MsgType", "text");
        if (WxMpEnum.Event.subscribe.getMsg().equals(map.get("Event"))) {
            resultMap.put("Content", "订阅公众号");
        }
        return WxXmlUtil.mapToXml(resultMap);
    }


}
