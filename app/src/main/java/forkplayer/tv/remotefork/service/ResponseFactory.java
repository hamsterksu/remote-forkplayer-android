package forkplayer.tv.remotefork.service;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by hamsterksu on 25.12.16.
 */

public class ResponseFactory {

    public NanoHTTPD.Response r200(String response){
        return adjust(NanoHTTPD.newFixedLengthResponse(response));
    }

    public NanoHTTPD.Response r404(){
        return adjust(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_HTML, ""));
    }

    public NanoHTTPD.Response r405(){
        return adjust(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED, NanoHTTPD.MIME_HTML, ""));
    }

    public NanoHTTPD.Response r500(){
        return adjust(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_HTML, "Error appeared"));
    }

    private NanoHTTPD.Response adjust(NanoHTTPD.Response response) {
        response.setKeepAlive(false);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Connection", "close");
        return response;
    }
}
