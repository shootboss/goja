/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class QRCodeTest {
    @Test
    public void testCreateQR() throws Exception {
        QRCode qrCode = QRCode.create("黄鹤楼，呀浩然");
        qrCode.toFile("/Users/sog/Workthing/data/qr/hhl.jpg");
    }

    @Test
    public void testParse() throws Exception {

        String content = QRCode.from(new File("/Users/sog/Workthing/data/qr/hhl.jpg"));
        assertEquals("黄鹤楼，呀浩然", content);
    }
}