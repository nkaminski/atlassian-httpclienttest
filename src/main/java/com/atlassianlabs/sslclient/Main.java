package com.atlassianlabs.sslclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class);

        if (args.length != 1) {
            System.out.println("Usage: java -jar httpclienttest-1.0.jar <https-host-url>:<port>");
            System.out.println("Example:  java -jar httpclienttest-1.0.jar jira.atlassian.com:443");
            System.exit(1);
        }

        HttpClient client = HttpClientBuilder.create().useSystemProperties().build();
        HttpGet request = new HttpGet("https://" + args[0]);
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
        System.out.println("HTTPClient: Connection Successful to https://" + args[0]);

    }
}
