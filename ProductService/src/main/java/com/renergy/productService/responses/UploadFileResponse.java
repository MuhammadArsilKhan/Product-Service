package com.renergy.productService.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UploadFileResponse {

    private String responseIdentifier;
    private String fileUrl;
    private String fileName;
}
