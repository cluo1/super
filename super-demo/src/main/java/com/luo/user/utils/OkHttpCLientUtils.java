package com.luo.user.utils;

import com.squareup.okhttp.*;

import java.io.IOException;

public class OkHttpCLientUtils {

    /**
     * get请求
     */
    public static String get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "undefined=");
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }
}
