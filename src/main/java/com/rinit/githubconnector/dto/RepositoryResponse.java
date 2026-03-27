package com.rinit.githubconnector.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RepositoryResponse(String name,
                                 @JsonProperty("html_url") String url,
                                 @JsonProperty("stargazers_count") int stars) {
}
