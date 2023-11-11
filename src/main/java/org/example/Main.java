package org.example;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlForm;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlTable;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try(WebClient client = new WebClient();) {
            client.getOptions().setJavaScriptEnabled(false);
            client.getOptions().setCssEnabled(false);
            HtmlPage searchPage = client.getPage("https://new.uschess.org/player-search");
            HtmlForm form = (HtmlForm) searchPage.getByXPath("//form").get(0);
            HtmlInput inputField = form.getInputByName("display_name");
            HtmlInput submitButton = form.getInputByName("op");
            inputField.type("Carlsen");
            HtmlPage resultsPage = submitButton.click();
            List<Player> players = parseResults(resultsPage);
            for (Player player : players) {
                System.out.println(player);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Player> parseResults(HtmlPage resultsPage) {
        HtmlTable table = (HtmlTable) resultsPage.getByXPath("//table").get(0);
        List<Player> players = table.getBodies().get(0).getRows().stream()
                .map(row -> {
                    String rating = row.getCell(2).getTextContent();
                    String qRating = row.getCell(3).getTextContent();
                    return new Player(
                            row.getCell(0).getTextContent(),
                            row.getCell(1).getTextContent(),
                            rating.isEmpty() ? null : Integer.parseInt(rating),
                            qRating.isEmpty() ? null : Integer.parseInt(qRating)
                    );
                }).toList();
        return players;
    }
}