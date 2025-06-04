package ru.pro.zakupki.config;

import ru.pro.zakupki.model.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageUtils {

    public static Image createTestImage(String path) {
        try {
            Image image = new Image();

            Path imagePath = Path.of(path);

            byte[] imageData = Files.readAllBytes(imagePath);

            image.setFileName("test-image.png");
            image.setContentType("image/png");
            image.setImageData(imageData);

            return image;
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать картинку");
        }
    }
}