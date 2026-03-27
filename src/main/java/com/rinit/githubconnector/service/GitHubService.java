package com.rinit.githubconnector.service;


import com.rinit.githubconnector.dto.RepositoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GitHubService {

    private final RestClient restClient;

    public GitHubService(@Value("${github.api.base-url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/vnd.github.v3+json")
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
    }

    public List<RepositoryResponse> fetchMyRepos(String token) {
        return restClient.get()
                .uri("/user/repos")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new ResponseStatusException(response.getStatusCode(), "GitHub API Error: Unauthorized or Bad Request");
                })
                .body(new ParameterizedTypeReference<List<RepositoryResponse>>() {});
    }
}