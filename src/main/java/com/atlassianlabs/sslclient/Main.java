package com.atlassianlabs.sslclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProxySelector;
import java.util.Arrays;
import java.net.URL;
import java.net.MalformedURLException;

public class Main {
    private static final int DEFAULT_CONNECT_TIMEOUT_MS = 5000;
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        String baseurl = "";
        if (args.length != 1) {
            System.out.println("Usage: java -jar httpclienttest-1.0.2.jar <host-url>:<port>");
            System.out.println("Example:  java -jar httpclienttest-1.0.2.jar https://jira.atlassian.com:443");
            System.out.println("          java -jar httpclienttest-1.0.2.jar http://jira.atlassian.com:8080/jira");
            System.exit(1);
        }

        try {
            baseurl = new URL(args[0]).toString();
        } catch (MalformedURLException e) {
            System.out.println(String.format("Error: Provided URL %s is an invalid URL", args[0]));
            System.exit(1);
        }

        HttpClient client = newHttpClient();
        HttpGet request = new HttpGet(baseurl);
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }

        System.out.println();
        System.out.println("HTTPClient: Connection Successful to " + baseurl);
    }

    private static HttpClient newHttpClient() {
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(DEFAULT_CONNECT_TIMEOUT_MS).build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
        httpClientBuilder.setRoutePlanner(routePlanner);
        httpClientBuilder.setDefaultSocketConfig(socketConfig);
        httpClientBuilder.setDefaultHeaders((Arrays.asList(new BasicHeader("Accept-Encoding", "gzip, deflate"))));


        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setMaxRedirects(3);
        httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());

        return httpClientBuilder.build();
    }
}
