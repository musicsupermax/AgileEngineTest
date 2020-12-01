package com.agileengine.fss.service;

import com.agileengine.fss.cash.ImageCash;
import com.agileengine.fss.model.Image;
import com.agileengine.fss.model.ImageDetails;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class ImagesService {

    private final RestTemplate restTemplate;
    private final ImageCash imageCash;

    public List<Image> findAllImages() {
        return imageCash.getImagesFromCash();
    }

    public ImageDetails findPhotoDetailsById(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(imageCash.getToken());

        return restTemplate
            .exchange(ImageCash.IMAGES + "/" + id, HttpMethod.GET, new HttpEntity<>(headers), ImageDetails.class)
            .getBody();
    }
}
