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
public class Image {
    private String MediaId;

    public Image(String mediaId) {
        MediaId = mediaId;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
