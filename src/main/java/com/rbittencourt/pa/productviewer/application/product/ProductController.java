package com.rbittencourt.pa.productviewer.application.product;

import com.rbittencourt.pa.productviewer.infrastructure.product.Product;
import com.rbittencourt.pa.productviewer.infrastructure.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping({"/product"})
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(record -> ok().body(record))
                .orElse(notFound().build());
    }

    @GetMapping("/recommendation")
    public ResponseEntity<List<Product>> recommendations() {
        List<Long> randomIds = new Random().longs(10, 1, 200)
                .boxed().collect(toList());

        List<Product> recommendedProducts = productRepository.findByIdIn(randomIds);

        return ResponseEntity.ok().body(recommendedProducts);
    }

}
