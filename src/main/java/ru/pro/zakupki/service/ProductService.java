package ru.pro.zakupki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pro.zakupki.exception.NotFoundProductException;
import ru.pro.zakupki.model.Image;
import ru.pro.zakupki.model.Manufacturer;
import ru.pro.zakupki.model.Product;
import ru.pro.zakupki.model.dto.ProductQuantityDto;
import ru.pro.zakupki.model.requests.ProductAddRequest;
import ru.pro.zakupki.repository.ImageRepository;
import ru.pro.zakupki.repository.ManufacturerRepository;
import ru.pro.zakupki.repository.OrderItemRepository;
import ru.pro.zakupki.repository.ProductRepository;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ImageRepository imageRepository;
    private final OrderItemRepository orderItemRepository;

    public void addProduct(ProductAddRequest productAddRequest) throws IOException {
        Manufacturer manufacturer = manufacturerRepository.findByName(productAddRequest.getManufacturerName())
                .orElseGet(() -> {
                            Manufacturer newMan = new Manufacturer();
                            newMan.setName(productAddRequest.getManufacturerName());
                            return manufacturerRepository.save(newMan);
                        }
                );
        Image image = new Image();
        image.setFileName(productAddRequest.getImageFile().getOriginalFilename());
        image.setContentType(productAddRequest.getImageFile().getContentType());
        image.setImageData(productAddRequest.getImageFile().getBytes());

        imageRepository.save(image);

        Product product = new Product();
        product.setName(productAddRequest.getName());
        product.setDescription(productAddRequest.getDescription());
        product.setPrice(productAddRequest.getPrice());
        product.setManufacturer(manufacturer);
        product.setImage(image);

        productRepository.save(product);
    }


    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    @Transactional(readOnly = true)
    public List<ProductQuantityDto> getAllActiveProducts() {
        return orderItemRepository.findProductQuantitiesInActiveOrders();
    }

    @Transactional
    public boolean deleteProductById(Long id) {
        productRepository.findById(id).orElseThrow(() -> new NotFoundProductException(id));
        productRepository.deleteById(id);
        return !productRepository.existsById(id);
    }
}
