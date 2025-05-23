package com.bcs.trainingwebsite.util;

import java.nio.charset.StandardCharsets;

public class ImageConverter {

    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] stringToBytes(String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }

}
