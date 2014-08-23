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
public class Voice {
    /** 通过上传多媒体文件，得到的id */
    private String MediaId;

    public Voice(String mediaId) {
        MediaId = mediaId;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
