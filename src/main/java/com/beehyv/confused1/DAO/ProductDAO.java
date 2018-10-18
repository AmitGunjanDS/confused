package com.beehyv.confused1.DAO;

import com.beehyv.confused1.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {

    Product findByProductName(String productName);

    List<Product> findByCategory(String category);

    List<Product> findByProductPriceBetween(double lowest, double highest);

    List<Product> findByProductNameLike(String searchString);

//    void deleteByPrductId(Integer prductId);


}
