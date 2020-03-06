package me.xueyao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Simon.Xue
 * @date 2020-03-05 23:48
 **/
@AllArgsConstructor
@Getter
public enum MsgTypeEnum {
    TEXT("text"),
    IMAGE("image"),
    VOICE("voice"),
    VIDEO("video"),
    SHORT_VIDEO("shortvideo"),
    LOCATION("location"),
    LINK("link");
    String msg;
}
