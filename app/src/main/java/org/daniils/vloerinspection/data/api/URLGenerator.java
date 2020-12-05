package org.daniils.vloerinspection.data.api;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class URLGenerator {

    private static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private static String urlEncodeUTF8(HashMap<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    public static String generateURL(String host, String method, HashMap<?, ?> map) {
        try {
            String url = new URL(new URL(host), method).toString();
            if (map.isEmpty()) {
                return url;
            } else {
                return url + "?" + urlEncodeUTF8(map);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
