package forkplayer.tv.remotefork;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    String test = "curl -L \"http://s5.cdnapponline.com/serial/ab13a831298e1a58bb1fea427753ed98/iframe\" -H \"Referer: http://hdrezka.me/\" -H \"Pragma: no-cache\" -H \"DNT: 1\" -H \"Accept-Encoding: deflate\" -H \"Accept-Language: ru,en;q=0.8,uk;q=0.6,bg;q=0.4\" -H \"User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36\" -H \"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\" -H \"Cache-Control: no-cache\" -H \"Connection: keep-alive\"";

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}