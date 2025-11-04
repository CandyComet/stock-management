package com.resrfullapi.api.testing.repo;

import com.resrfullapi.api.testing.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockManage extends JpaRepository<ProductModel, Integer> {

    @Query(value = "SELECT * FROM stock WHERE CONCAT(id, name, warehouse, quantity, price) REGEXP :keyword", nativeQuery = true)
    List<ProductModel> searchByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM stock WHEEGEXP :keyword) OR " +
            "(:field = 'warehouse' AND warehouse RERE \" +\n" +
            "            \"(:field = 'id' AND CAST(id AS CHAR) REGEXP :keyword) OR \" +\n" +
            "            \"(:field = 'name' AND name RGEXP :keyword) OR " +
            "(:field = 'quantity' AND CAST(quantity AS CHAR) REGEXP :keyword) OR " +
            "(:field = 'price' AND CAST(price AS CHAR) REGEXP :keyword)",
            nativeQuery = true)
    List<ProductModel> searchByField(@Param("field") String field, @Param("keyword") String keyword);
}
