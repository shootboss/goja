package com.jfinal.module.wxchat.message.output;

/**
 * <p>
 * .
 * </p>
 *
 * @author Jerry Ou
 * @version 1.0 2014-03-26 20:03
 * @since JDK 1.6
 */
public class Video {
    /** 通过上传多媒体文件，得到的id */
    private String MediaId;
    /** 视频消息的标题 */
    private String Title;
    /** 视频消息的描述 */
    private String Description;

    public Video() {
    }

    public Video(String mediaId, String title, String description) {
        MediaId = mediaId;
        Title = title;
        Description = description;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
