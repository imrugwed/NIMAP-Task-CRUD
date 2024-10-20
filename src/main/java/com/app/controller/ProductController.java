package com.app.controller;

import com.app.entity.Product;
import com.app.other.NotFoundException;
import com.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Get all products with pagination
    @GetMapping
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return productService.getAllProducts(page-1, size);
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCategory(@RequestBody Product product) {
        Long id = productService.saveProduct(product).getId();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Saved Successfully");
        response.put("id", id);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Product product = productService.getProductById(id);
            response.put("product", product);

            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Update product by ID
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Map<String, Object> response = new HashMap<>();
        try {
            Product pro = productService.updateProduct(id, updatedProduct);
//            response.put("Product", pro);
            response.put("message", "Updated Successfully");

            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Delete product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            productService.deleteProduct(id);
            response.put("message", "Product deleted successfully");
            response.put("status", HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.put("message", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}