package me.xueyao.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Simon.Xue
 * @date 2020-03-03 20:26
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class R<T> {
    private Integer code;
    private String message;
    private T data;

    public R(String msg, T object) {
        this.message = msg;
        this.data = object;
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }


    public T getData() {
        return this.data;
    }


    public static <T> R<T> ofParam(String msg) {
        return new R(status.BAD_PARAM.getCode(), msg);
    }

    public static <T> R<T> ofSuccess(String msg) {
        return new R(status.SUCCESS.getCode(), msg);
    }

    public static <T> R<T> ofSuccess(String msg, T data) {
        return new R(status.SUCCESS.getCode(), msg, data);
    }

    public static <T> R<T> ofSystem(String msg) {
        return new R(status.SYSTEM.getCode(), msg);
    }

    public boolean getSuccess() {
        return status.SUCCESS.getCode().equals(this.code);
    }

    @AllArgsConstructor
    @Getter
    enum status {
        SUCCESS(200, "请求成功"),
        BAD_PARAM(403, "请求参数错误"),
        SYSTEM(500, "服务器错误");
        Integer code;
        String msg;
    }
}
