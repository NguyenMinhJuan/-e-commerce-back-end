package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.FileStorageService;
import com.example.ecommerce.service.product.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
                RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowedHeaders = "*")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService IProductService;

    @Autowired
    private FileStorageService fileStorageService;

    // Lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = IProductService.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestParam("files") List<MultipartFile> files,
                                           @RequestParam("name") String name,
                                           @RequestParam("price") double price,
                                           @RequestParam("quantity") int quantity,
                                           @RequestParam("description") String description,
                                           @RequestParam("category") String category) {
        try {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setDescription(description);
            product.setCategory(category);

            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                String fileName = fileStorageService.storeFile(file);
                imageUrls.add(fileName);
            }
            product.setImageUrls(imageUrls);

            Product savedProduct = IProductService.save(product);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating product: " + e.getMessage());
        }
    }

    // API lấy chi tiết sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> product = IProductService.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy sản phẩm với ID: " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestParam(value = "files", required = false) List<MultipartFile> files,
                                           @RequestParam("name") String name,
                                           @RequestParam("price") double price,
                                           @RequestParam("quantity") int quantity,
                                           @RequestParam("description") String description,
                                           @RequestParam("category") String category,
                                           @RequestParam("existingImages") String existingImagesJson) {
        try {
            Product product = IProductService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            // Cập nhật thông tin cơ bản
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setDescription(description);
            product.setCategory(category);

            // Chuyển đổi JSON string thành List
            ObjectMapper mapper = new ObjectMapper();
            List<String> existingImages = mapper.readValue(existingImagesJson,
                    mapper.getTypeFactory().constructCollectionType(List.class, String.class));

            // Tạo danh sách ảnh mới bao gồm cả ảnh cũ
            List<String> updatedImageUrls = new ArrayList<>(existingImages);

            // Thêm các ảnh mới (nếu có)
            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    String fileName = fileStorageService.storeFile(file);
                    updatedImageUrls.add(fileName);
                }
            }

            // Cập nhật danh sách ảnh
            product.setImageUrls(updatedImageUrls);

            Product updatedProduct = IProductService.save(product);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating product: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            Product product = IProductService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            // Xóa các file ảnh liên quan
            for (String imageUrl : product.getImageUrls()) {
                fileStorageService.deleteFile(imageUrl);
            }

            IProductService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting product: " + e.getMessage());
        }
    }

    // Thêm endpoint mới cho phân trang
    @GetMapping("/page")
    public ResponseEntity<Page<Product>> getProductsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id,desc") String sort,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category) {
        
        try {
            log.info("=== Start processing request ===");
            log.info("Parameters received - page: {}, size: {}, sort: {}, search: '{}', category: '{}'", 
                    page, size, sort, search, category);
            
            // Parse sort parameters
            String[] sortParams = sort.split(",");
            String sortField = sortParams[0];
            Sort.Direction direction = sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            
            // Create pageable object
            Sort sorting = Sort.by(direction, sortField);
            Pageable pageable = PageRequest.of(page, size, sorting);
            
            // Execute search or get all
            Page<Product> productPage;
            
            if (category != null && !category.trim().isEmpty()) {
                if (search != null && !search.trim().isEmpty()) {
                    // Tìm theo cả category và search term
                    log.info("Searching by category '{}' and search term '{}'", category.trim(), search.trim());
                    productPage = IProductService.findByCategoryAndNameContaining(
                        category.trim(), search.trim(), pageable);
                } else {
                    // Chỉ tìm theo category
                    log.info("Searching by category '{}'", category.trim());
                    productPage = IProductService.findByCategory(category.trim(), pageable);
                }
            } else if (search != null && !search.trim().isEmpty()) {
                // Chỉ tìm theo search term
                log.info("Searching by term '{}'", search.trim());
                productPage = IProductService.findByNameContaining(search.trim(), pageable);
            } else {
                // Không có điều kiện tìm kiếm
                log.info("Getting all products");
                productPage = IProductService.findAll(pageable);
            }
            
            log.info("Found {} products", productPage.getTotalElements());
            productPage.getContent().forEach(product -> 
                log.info("Found product: {} (ID: {}, Category: {})", 
                    product.getName(), product.getId(), product.getCategory())
            );
            
            return ResponseEntity.ok(productPage);
        } catch (Exception e) {
            log.error("Error processing request:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}