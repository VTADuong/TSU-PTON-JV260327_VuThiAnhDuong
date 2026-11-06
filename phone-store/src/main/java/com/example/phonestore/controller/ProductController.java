package com.example.phonestore.controller;

import com.example.phonestore.entity.Product;
import com.example.phonestore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public Page<Product> list(@RequestParam(name = "brand", required = false) String brand,
                              @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
                              @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
                              @RequestParam(name = "stock", required = false) Integer stock,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "10") int size,
                              @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                              @RequestParam(name = "dir", defaultValue = "asc") String dir) {
        return service.search(brand, minPrice, maxPrice, stock, page, size, sortBy, dir);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody Product p) { return service.create(p); }

    @PutMapping("/{id}")
    public Product update(@PathVariable("id") Integer id, @Valid @RequestBody Product p) { return service.update(id, p); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) { service.delete(id); }
}
