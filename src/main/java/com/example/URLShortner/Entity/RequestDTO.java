package com.example.URLShortner.Entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {
    private String requestkey;
    private String url;


    @JsonProperty("URL")
    private String URL; // Matches "URL"

    public String getRequestkey() {
        return requestkey;
    }
    public String getURL() {
        return url;
    }
}
