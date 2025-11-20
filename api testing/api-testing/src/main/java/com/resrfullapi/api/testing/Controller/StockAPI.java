package com.resrfullapi.api.testing.Controller;

import com.resrfullapi.api.testing.model.ProductModel;
import com.resrfullapi.api.testing.repo.StockManage;
import com.resrfullapi.api.testing.services.FileUploadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "http://localhost:3000")
public class StockAPI {

    @Autowired
    private StockManage stockManage;

    @Autowired
    private FileUploadServices fileUploadServices;

    // ------------------ GET ALL PRODUCTS ------------------
    @GetMapping("/getAlls")
    public List<ProductModel> getAllProducts() {
        return stockManage.findAll();
    }

    // ------------------ GET PRODUCT BY ID ------------------
    @GetMapping("/gets/{id}")
    public Optional<ProductModel> getProductById(@PathVariable Long id) {
        return stockManage.findById(Math.toIntExact(id));
    }

    // ------------------ ADD PRODUCT ------------------
    @PostMapping("/adds")
    public ProductModel addProduct(@RequestBody ProductModel product) {
        return stockManage.save(product);
    }

    // ------------------ UPDATE PRODUCT ------------------
    @PutMapping("/updates/{id}")
    public ProductModel updateProduct(@PathVariable Long id, @RequestBody ProductModel product) {
        product.setId(Math.toIntExact(id));
        return stockManage.save(product);
    }

    // ------------------ DELETE PRODUCT ------------------
    @DeleteMapping("/deletes/{id}")
    public String deleteProduct(@PathVariable Long id) {
        stockManage.deleteById(Math.toIntExact(id));
        return "Deleted Successfully";
    }

    // ------------------ UPLOAD IMAGE ------------------
    @PostMapping("/upload-images")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return fileUploadServices.saveImage(file);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/all-images")
    public List<String> getAllImages() {
        File folder = new File("file_upload/images/");
        File[] files = folder.listFiles();

        List<String> list = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    list.add("http://localhost:8080/stock/image/" + file.getName());
                }
            }
        }
        return list;
    }


    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get("file_upload/images/").resolve(filename);
        Resource resource = new UrlResource(path.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}