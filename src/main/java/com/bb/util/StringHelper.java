package com.bb.util;

import org.apache.commons.io.IOUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class StringHelper {
    public static String parseUtf8String( String orig,  String f , String code) {
        String name = "";
        try {
            String iso = new String(orig.getBytes(f), code);
            name =new String(iso.getBytes(code),"UTF-8");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return name;
    }




}
