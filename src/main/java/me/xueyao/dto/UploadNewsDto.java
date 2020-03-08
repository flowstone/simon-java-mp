package me.xueyao.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2020-03-08 14:02
 **/
@Data
public class UploadNewsDto implements Serializable {
    private List<Article> articles;
}


@Data
class Article {
    public String thumb_media_id;
    public String author;
    public String title;
    public String content_source_url;
    public String content;
    public String digest;
    public String show_cover_pic;
    public String need_open_comment;
    public String only_fans_can_comment;
}
