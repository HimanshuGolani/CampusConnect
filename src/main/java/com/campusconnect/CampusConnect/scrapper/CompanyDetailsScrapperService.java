package com.campusconnect.CampusConnect.scrapper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CompanyDetailsScrapperService {

    public Map<String, String> getCompanyDetailsFromWikipedia(String companyName) {
        Map<String, String> companyDetails = new HashMap<>();
        String url = "https://en.wikipedia.org/wiki/" + companyName;

        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                    .timeout(10 * 1000) // Timeout after 10 seconds
                    .referrer("http://www.google.com")
                    .get();

            // Extract the company infobox (generally on the right side of Wikipedia page)
            Element infoBox = document.selectFirst(".infobox.vcard");

            if (infoBox != null) {
                Elements rows = infoBox.select("tr"); // Table rows

                for (Element row : rows) {
                    Elements header = row.select("th"); // Header cells
                    Elements data = row.select("td");   // Data cells

                    if (!header.isEmpty() && !data.isEmpty()) {
                        String key = header.text().trim();
                        String value = data.text().trim();
                        companyDetails.put(key, value);
                    }
                }
            } else {
                log.warn("No infobox found for the company: {}", companyName);
            }

            // Optionally extract the first paragraph of the company description
            Element description = document.selectFirst(".mw-parser-output > p");
            if (description != null) {
                companyDetails.put("Description", description.text());
            }

        } catch (IOException e) {
            log.error("Failed to fetch Wikipedia details for: {}, URL: {}", companyName, url, e);
            throw new RuntimeException("Failed to scrape Wikipedia data for " + companyName);
        }

        return companyDetails;
    }
}
