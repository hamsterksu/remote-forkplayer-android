package forkplayer.tv.remotefork.service.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hamsterksu on 25.12.16.
 */

public class CurlHandler implements IHandler {

    private static final Pattern pattern = Pattern.compile("(?:\")(.*?)(?=\")");
    private static final Pattern regexpHeaders = Pattern.compile("(?:-H\\s\")(.*?)(?=\")");
    private static final Pattern regexpData = Pattern.compile("(?:--data\\s\")(.*?)(?=\")");

    private final HttpFactory factory;
    private final String request;

    public CurlHandler(HttpFactory factory, String request) {
        this.factory = factory;
        this.request = request;
    }

    @Override
    public String process() throws IOException {
        String url = pattern.matcher(request).group(1);
        HashMap<String, String> headers = new HashMap<String, String>();
        Matcher matches = regexpHeaders.matcher(request);
        for (int i = 0; i < matches.groupCount(); i++) {
            String header = matches.group(i);
            String[] pair = header.split(":", 2);
            if (pair.length == 2) {
                headers.put(pair[0], pair[1]);
            }
        }

        if (request.contains("--data")) {
            String dataString = regexpData.matcher(request).group(1);
            String[] dataArray = dataString.split("&");
            HashMap<String, String> data = new HashMap<>();
            for (String item : dataArray) {
                String[] pair = item.split("=", 2);
                if (pair.length == 2) {
                    data.put(pair[0], pair[1]);
                }
            }
            return factory.doPost(url, headers, data);
        } else {
            return factory.doGet(url, headers);
        }
    }
}
