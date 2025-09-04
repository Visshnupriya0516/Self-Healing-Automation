package com.selfhealing.utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LocatorUtils {
    private static final String FILE_NAME = "healing-locators.json";
    private static Map<String, Map<String, String>> locators = new HashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        try {
            URL resource = LocatorUtils.class.getClassLoader().getResource(FILE_NAME);
            if (resource != null) {
                locators = mapper.readValue(new File(resource.getFile()), new TypeReference<>() {});
            }
        } catch (IOException e) {
            LoggerUtil.getLogger().warn("Could not load healing-locators.json, starting empty.");
        }
    }

    public static List<By> getFallbacks(String key) {
        if (!locators.containsKey(key)) return List.of();

        Map<String, String> entry = locators.get(key);
        return entry.values().stream()
                .map(LocatorUtils::convertToBy)
                .toList();
    }

    public static void saveLocators() {
        try {
            File outFile = new File("src/main/resources/" + FILE_NAME);
            mapper.writerWithDefaultPrettyPrinter().writeValue(outFile, locators);
        } catch (IOException e) {
            LoggerUtil.getLogger().error("Failed to save healing-locators.json", e);
        }
    }

    public static void addFallback(String key, String type, String value) {
        locators.computeIfAbsent(key, k -> new HashMap<>()).put(type, value);
        saveLocators();
    }

    private static By convertToBy(String locatorString) {
        if (locatorString.startsWith("//")) return By.xpath(locatorString);
        if (locatorString.startsWith("css=")) return By.cssSelector(locatorString.replace("css=", ""));
        if (locatorString.startsWith("id=")) return By.id(locatorString.replace("id=", ""));
        return By.xpath(locatorString);
    }
}
