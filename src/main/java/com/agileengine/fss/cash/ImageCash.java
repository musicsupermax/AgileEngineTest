package com.agileengine.fss.cash;

import com.agileengine.fss.model.ApiKey;
import com.agileengine.fss.model.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class ImageCash {

    public static final String IMAGES = "http://interview.agileengine.com/images";
    private static final String AUTHORIZATION = "http://interview.agileengine.com/auth";
    private static final String KEY = "23567b218376f79d9415";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @PostConstruct
    @Cacheable(cacheNames = "images")
    public List<Image> getImagesFromCash() {
        List<JsonNode> pictures = new ArrayList();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());

        HttpEntity request = new HttpEntity<>(headers);
        JsonNode images = restTemplate
            .exchange(IMAGES, HttpMethod.GET, request, JsonNode.class)
            .getBody();
        try {
            if (Objects.nonNull(images)) {
                pictures = objectMapper.treeToValue(images.get("pictures"), List.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return pictures.stream()
            .map(node -> new Image(node.get("id").asText(), node.get("cropped_picture").asText()))
            .collect(Collectors.toList());
    }

    public String getToken() {
        HttpEntity request = new HttpEntity<>(new ApiKey(KEY), new HttpHeaders());

        JsonNode body = restTemplate.postForEntity(AUTHORIZATION, request, JsonNode.class)
            .getBody();

        if (Objects.nonNull(body)) {
            return body.get("token").asText();
        } else {
            throw new NullPointerException("Response from http://interview.agileengine.com/auth should contain value");
        }
    }
}