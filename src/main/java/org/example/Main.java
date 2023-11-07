package org.example;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        WebClient client = new WebClient();
        try {
            HtmlPage searchPage = client.getPage("https://new.uschess.org/player-search");
            searchPage.getByXPath("//form");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}