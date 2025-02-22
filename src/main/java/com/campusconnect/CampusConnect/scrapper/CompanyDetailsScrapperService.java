package com.campusconnect.CampusConnect.scrapper;

import com.campusconnect.CampusConnect.cache.AppCache;
import com.campusconnect.CampusConnect.entity.COMPANY_NAME_TAG;
import com.campusconnect.CampusConnect.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompanyDetailsScrapperService {

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    private final Map<String, COMPANY_NAME_TAG> companyNameMap;

    public CompanyDetailsScrapperService() {
        this.companyNameMap = Arrays.stream(COMPANY_NAME_TAG.values())
                .collect(Collectors.toMap(
                        COMPANY_NAME_TAG::getFullName,
                        name -> name,
                        (existing, replacement) -> existing)
                );
    }

    private Optional<COMPANY_NAME_TAG> findMostCommonName(String companyName) {
        log.debug("Finding common name for company: {}", companyName);
        return Optional.ofNullable(companyNameMap.get(companyName));
    }

    public Map<String, String> getCompanyDetails(String companyName) throws JsonProcessingException {
        if (companyName == null || companyName.trim().isEmpty()) {
            log.error("Invalid company name provided.");
            throw new IllegalArgumentException("Company name cannot be null or empty.");
        }

        log.info("Fetching company details for: {}", companyName);

        String originalCompanyName = companyName.trim();

        companyName = findMostCommonName(companyName)
                .map(COMPANY_NAME_TAG::getFullName)
                .orElse(companyName);

        log.debug("Mapped company name: {} -> {}", originalCompanyName, companyName);

        String formattedCompanyName = companyName.replace(" ", "_");

        Map<String, String> cacheMap = redisService.get("details_of_company_" + formattedCompanyName, HashMap.class);
        if (cacheMap != null) {
            log.info("Company details found in cache for: {}", companyName);
            return cacheMap;
        }

        log.debug("Company details not found in cache, attempting to fetch from Wikipedia.");

        Map<String, String> companyDetails = new HashMap<>();
        String wikipediaUrl = "https://en.wikipedia.org/wiki/" + formattedCompanyName;

        try {
            companyDetails = fetchDetailsFromWikipedia(wikipediaUrl);
        } catch (IOException e) {
            log.warn("Failed to fetch details from Wikipedia for: {}, attempting OpenCorporates.", companyName);
            String openCorporatesUrl = "https://opencorporates.com/companies/search?q=" + companyName.replace(" ", "+");
            try {
                companyDetails = fetchDetailsFromOpenCorporates(openCorporatesUrl);
            } catch (IOException ex) {
                log.error("Failed to fetch details from OpenCorporates for: {}", companyName, ex);
                throw new RuntimeException("Failed to scrape data for " + companyName + " from both Wikipedia and OpenCorporates.");
            }
        }

        redisService.set("details_of_company_" + formattedCompanyName, companyDetails, 24L);
        log.info("Company details successfully stored in cache for: {}", companyName);

        return companyDetails;
    }

    private Map<String, String> fetchDetailsFromWikipedia(String url) throws IOException {
        Map<String, String> companyDetails = new HashMap<>();

        log.debug("Connecting to Wikipedia URL: {}", url);
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                .timeout(10 * 1000)
                .referrer("http://www.google.com")
                .get();

        log.debug("Successfully connected to Wikipedia page.");

        Element infoBox = document.selectFirst(".infobox.vcard");

        if (infoBox != null) {
            log.debug("Infobox found, extracting data...");

            infoBox.select("tr").forEach(row -> {
                Element header = row.selectFirst("th");
                Element data = row.selectFirst("td");

                if (header != null && data != null) {
                    String key = header.text().trim();
                    String value = data.text().trim();
                    companyDetails.put(key, value);
                    log.debug("Extracted detail: {} -> {}", key, value);
                }
            });
        } else {
            log.warn("No infobox found on Wikipedia page.");
        }

        Element description = document.selectFirst(".mw-parser-output > p");
        if (description != null) {
            String descText = description.text().trim();
            companyDetails.put("Description", descText);
            log.debug("Extracted company description: {}", descText);
        } else {
            log.warn("No description found on Wikipedia page.");
        }

        return companyDetails;
    }

    private Map<String, String> fetchDetailsFromOpenCorporates(String url) throws IOException {
        Map<String, String> companyDetails = new HashMap<>();

        log.debug("Connecting to OpenCorporates URL: {}", url);
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                .timeout(10 * 1000)
                .referrer("http://www.google.com")
                .get();

        log.debug("Successfully connected to OpenCorporates search page.");

        Element searchResult = document.selectFirst(".search-results .search-result");

        if (searchResult != null) {
            log.debug("Search result found, extracting data...");

            Element companyNameElement = searchResult.selectFirst(".name a");
            if (companyNameElement != null) {
                companyDetails.put("Company Name", companyNameElement.text().trim());
            }

            Element jurisdiction = searchResult.selectFirst(".jurisdiction");
            if (jurisdiction != null) {
                companyDetails.put("Jurisdiction", jurisdiction.text().trim());
            }

            Element companyNumber = searchResult.selectFirst(".company-number");
            if (companyNumber != null) {
                companyDetails.put("Company Number", companyNumber.text().trim());
            }

            Element status = searchResult.selectFirst(".status");
            if (status != null) {
                companyDetails.put("Status", status.text().trim());
            }

        } else {
            log.warn("No search results found on OpenCorporates.");
        }

        return companyDetails;
    }
}
