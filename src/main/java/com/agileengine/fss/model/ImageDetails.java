package com.agileengine.fss.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDetails {

    private String id;
    private String author;
    private String tags;
    private String camera;

}
