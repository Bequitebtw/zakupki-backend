package ru.pro.zakupki.model.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductAddRequest {
    private String name;
    private String description;
    private Long price;
    private Integer quantity;
    private String manufacturerName;
    private MultipartFile imageFile;
}
