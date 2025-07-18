package com.example.URLShortner.ServiceLayer;

public class URLShortenerUtils {
    public static String generateShortUrlKey(String url) {
        // Simple example: hashCode (replace with your logic)
        return Integer.toHexString(url.hashCode());
    }
}