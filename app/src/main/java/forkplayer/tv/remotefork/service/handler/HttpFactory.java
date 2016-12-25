package forkplayer.tv.remotefork.service.handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hamsterksu on 25.12.16.
 */

public class HttpFactory {

    private OkHttpClient client;

    public HttpFactory() {
        this.client = new OkHttpClient.Builder().addInterceptor(new UserAgentInterceptor()).build();
    }

    public String doGet(String url, Map<String, String> headers) throws IOException {
        Request.Builder request = new Request.Builder()
                .url(url);
        if (headers != null) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }
        Response resp = client.newCall(request.build()).execute();
        return resp.body().string();
    }

    public String doPost(String url, Map<String, String> headers, Map<String, String> data) throws IOException {
        FormBody.Builder body = new FormBody.Builder();
        if (data != null) {
            for (Map.Entry<String, String> e : data.entrySet()) {
                body.add(e.getKey(), e.getValue());
            }
        }

        Request.Builder request = new Request.Builder()
                .method("POST", body.build())
                .url(url);
        if (headers != null) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }
        Response resp = client.newCall(request.build()).execute();
        return resp.body().string();
    }

    private class UserAgentInterceptor implements Interceptor {

        private static final String USER_AGENT = "Mozilla/5.0 (Web0S; Linux/SmartTV) AppleWebKit/537.41 (KHTML, like Gecko) Large Screen WebAppManager Safari/537.41";

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .header("User-Agent", USER_AGENT)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }
}
