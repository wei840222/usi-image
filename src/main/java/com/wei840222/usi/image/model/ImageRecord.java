package com.wei840222.usi.image.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NonNull;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageRecord implements Serializable {
    private static final long serialVersionUID = 100L;
    
    @NotEmpty private String imageName;
    @NotEmpty private String mergeFile;
    @NonNull private Integer startByte;
    @NonNull private Integer fileSize;
}