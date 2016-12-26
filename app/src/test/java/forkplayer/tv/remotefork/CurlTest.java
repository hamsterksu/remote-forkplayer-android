package forkplayer.tv.remotefork;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import forkplayer.tv.remotefork.service.handler.CurlHandler;
import forkplayer.tv.remotefork.service.handler.HttpFactory;

import static org.junit.Assert.*;

public class CurlTest {

    @Test
    public void curlGetOk() throws Exception {
        String request = "curl -L \"http://s5.cdnapponline.com/serial/ab13a831298e1a58bb1fea427753ed98/iframe\" " +
                "-H \"Referer: http://hdrezka.me/\" " +
                "-H \"Pragma: no-cache\" " +
                "-H \"DNT: 1\" " +
                "-H \"Accept-Encoding: deflate\" " +
                "-H \"Accept-Language: ru,en;q=0.8,uk;q=0.6,bg;q=0.4\" " +
                "-H \"User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36\" " +
                "-H \"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\" " +
                "-H \"Cache-Control: no-cache\" " +
                "-H \"Connection: keep-alive\"";
        HttpFactory factory = new HttpFactory() {
            @Override
            public String doGet(String url, Map<String, String> headers) throws IOException {
                assertEquals("http://s5.cdnapponline.com/serial/ab13a831298e1a58bb1fea427753ed98/iframe", url);

                assertEquals(9, headers.size());
                assertEquals("http://hdrezka.me/", headers.get("Referer"));
                assertEquals("no-cache", headers.get("Pragma"));
                assertEquals("1", headers.get("DNT"));
                assertEquals("deflate", headers.get("Accept-Encoding"));
                assertEquals("ru,en;q=0.8,uk;q=0.6,bg;q=0.4", headers.get("Accept-Language"));
                assertEquals("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36", headers.get("User-Agent"));
                assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8", headers.get("Accept"));
                assertEquals("no-cache", headers.get("Cache-Control"));
                assertEquals("keep-alive", headers.get("Connection"));

                return "OK";
            }

            @Override
            public String doPost(String url, Map<String, String> headers, Map<String, String> data) throws IOException {
                throw new RuntimeException("wrong method");
            }
        };
        String result = new CurlHandler(factory, request).process();
        assertEquals("OK", result);
    }
}