package me.xueyao.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2020-03-08 14:40
 **/
@Data
public class TagSendAllDto implements Serializable {
    private String msgtype;
    private String send_ignore_reprint;
    private Filter filter;
    private MpNews mpnews;

}

@Data
class MpNews {
    public String media_id;
}

@Data
class Filter {
    public String is_to_all;
    public String tag_id;
}
