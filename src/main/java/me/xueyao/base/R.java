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
public class R {
    private Integer code;
    private String message;
    private Object data;

    public R(String msg, Object object) {
        this.message = msg;
        this.data = object;
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }


    public static R ofParam(String msg) {
        return new R(status.BAD_PARAM.getCode(), msg);
    }

    public static R ofSuccess(String msg) {
        return new R(status.SUCCESS.getCode(), msg);
    }

    public static R ofSuccess(String msg, Object data) {
        return new R(status.SUCCESS.getCode(), msg, data);
    }

    public static R ofSystem(String msg) {
        return new R(status.SYSTEM.getCode(), msg);
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
