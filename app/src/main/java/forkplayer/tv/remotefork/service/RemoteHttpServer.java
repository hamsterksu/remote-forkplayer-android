package forkplayer.tv.remotefork.service;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;
import forkplayer.tv.remotefork.service.handler.CurlHandler;
import forkplayer.tv.remotefork.service.handler.HttpFactory;
import forkplayer.tv.remotefork.service.handler.IHandler;
import forkplayer.tv.remotefork.service.handler.WgetHandler;
import timber.log.Timber;

/**
 * Created by hamsterksu on 25.12.16.
 */

public class RemoteHttpServer extends NanoHTTPD {

    private static final int DEFAULT_PORT = 8028;

    private final HttpFactory httpFactory = new HttpFactory();
    private final ResponseFactory responseFactory = new ResponseFactory();

    public RemoteHttpServer() {
        super(DEFAULT_PORT);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Timber.d(".serve: [" + session.getMethod() + "] + " + session.getUri());
        if (session.getMethod() != Method.GET) {
            Timber.w(".serve 405");
            return responseFactory.r405();
        }
        Timber.d(".serve: " + session.getQueryParameterString());
        String uri = session.getUri();
        String result = null;
        if (uri.startsWith("/test")) {
            result = discoveryService();
        } else if (uri.startsWith("/parserlink")) {
            try {
                result = parseLink(uri);
            } catch (Exception e) {
                Timber.e(e, ".parseLink 500");
                return responseFactory.r500();
            }
        }
        if (result == null) {
            Timber.w(".serve 404");
            return responseFactory.r404();
        } else {
            Timber.d(".serve response: " + result);
            return responseFactory.r200(result);
        }
    }

    private String parseLink(String url) throws IOException {
        String[] parts = url.split("|");
        String request = parts[0];
        IHandler handler;
        if ("curl".equals(request)) {
            handler = new CurlHandler(httpFactory, request);
        } else {
            handler = new WgetHandler(httpFactory, request);
        }
        return handler.process();
    }

    private String discoveryService() {
        //TODO save attached device
        return "<html><h1>ForkPlayer DLNA Work!</h1><br><b>Server by Visual Studio 2015</b></html>";
    }
}
