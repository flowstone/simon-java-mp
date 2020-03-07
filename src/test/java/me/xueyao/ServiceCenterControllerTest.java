package me.xueyao;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.xueyao.base.R;
import me.xueyao.controller.ServiceCenterController;
import me.xueyao.dto.KfAccountDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Simon.Xue
 * @date 2020-03-07 15:06
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ServiceCenterControllerTest {

    @Autowired
    private ServiceCenterController serviceCenterController;

    @Test
    public void testAdd() {
        KfAccountDto kfAccountAdd = new KfAccountDto();
        kfAccountAdd.setKfAccount("test");
        kfAccountAdd.setNickname("测试");
        kfAccountAdd.setPassword("test");
        R add = serviceCenterController.add(kfAccountAdd);
        log.info("{}", JSONObject.toJSONString(add));
    }
}
