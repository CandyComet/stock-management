package com.resrfullapi.api.testing.Controller;

import com.resrfullapi.api.testing.model.ProductModel;
import com.resrfullapi.api.testing.repo.StockManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private StockManage stockManage;

    @GetMapping("/all")
    public List<ProductModel> getAllProducts() {
        return stockManage.findAll();
    }

    @PostMapping("/add")
    public ProductModel addProduct(@RequestBody ProductModel product) {
        return stockManage.save(product);
    }

    @GetMapping("/search")
    public List<ProductModel> searchByKeyword(@RequestParam String keyword) {
        return stockManage.searchByKeyword(keyword);
    }

    @PutMapping("/update/{id}")
    public ProductModel updateProduct(@PathVariable int id, @RequestBody ProductModel product) {
        return stockManage.findById(id).map(existing -> {
            existing.setName(product.getName());
            existing.setWarehouse(product.getWarehouse());
            existing.setQuantity(product.getQuantity());
            existing.setPrice(product.getPrice());
            existing.setImage(product.getImage());
            return stockManage.save(existing);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        stockManage.deleteById(id);
        return "Deleted product with id " + id;
    }
}
