package com.amadeus.imdgenerator;

import com.amadeus.imdgenerator.parser.SwaggerParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        for (String arg : args) {
            URL url = null;
            try {
                url = new URL(arg);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            SwaggerParser parser = new SwaggerParser();

            try {
                parser.parse(url);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
