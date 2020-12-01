package com.agileengine.fss.rest;

import com.agileengine.fss.model.Image;
import com.agileengine.fss.model.ImageDetails;
import com.agileengine.fss.service.ImagesService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImagesController {

    private final ImagesService imagesService;

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.ok(imagesService.findAllImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDetails> getDetailsById(@PathVariable String id) {
        return ResponseEntity.ok(imagesService.findPhotoDetailsById(id));
    }
}
