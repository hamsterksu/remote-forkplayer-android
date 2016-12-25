package forkplayer.tv.remotefork.service.handler;

import java.io.IOException;

/**
 * Created by hamsterksu on 25.12.16.
 */

public class WgetHandler implements IHandler {

    private final HttpFactory factory;
    private final String url;

    public WgetHandler(HttpFactory factory, String url) {
        this.factory = factory;
        this.url = url;
    }

    @Override
    public String process() throws IOException {
        return factory.doGet(url, null);
    }


}
