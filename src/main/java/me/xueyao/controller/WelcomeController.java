package me.xueyao.controller;

import lombok.extern.slf4j.Slf4j;
import me.xueyao.util.SignHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Simon.Xue
 * @date 2020-03-03 10:38
 **/
@RestController
@RequestMapping("/welcome")
@Slf4j
public class WelcomeController {

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
}
