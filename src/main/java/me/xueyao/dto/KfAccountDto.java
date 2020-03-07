package me.xueyao.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 添加客服
 * @author Simon.Xue
 * @date 2020-03-07 09:58
 **/
@Data
public class KfAccountDto implements Serializable {
    private String kfAccount;
    private String nickname;
    private String password;
}
