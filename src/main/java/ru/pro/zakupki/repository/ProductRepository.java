package ru.pro.zakupki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pro.zakupki.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
