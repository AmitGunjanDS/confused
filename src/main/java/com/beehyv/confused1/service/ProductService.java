package com.beehyv.confused1.service;

import com.beehyv.confused1.DAO.ProductDAO;
import com.beehyv.confused1.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    public List<Product> getProductList() {
        List<Product> pro = new ArrayList<>();
        for(Product p: productDAO.findAll()) {
            if(p.isAvailable()) {
                pro.add(p);
            }
        }
        return pro;
    }

    public void addProduct(Product product) {
        productDAO.save(product);
    }

    public Product findProduct(Integer productId) {

        Optional<Product> product = productDAO.findById(productId);

        return product.get();
    }

    public void setProductImage(Product product, byte[] productImage) {
        product.setProductImage(productImage);
    }

    public Product getProductById(Integer productId) {
        if (productDAO.findById(productId).get().isAvailable()) {
            return productDAO.findById(productId).get();
        }

        else return null;
    }

    public List<Product> getBySearchString(String searchString) {

        List<Product> pro = new ArrayList<>();
        for(Product p:  productDAO.findByProductNameLike("%" + searchString + "%")) {
            if(p.isAvailable()) {
                pro.add(p);
            }
        }
        return pro;
    }

    public List<Product> priceFilter(Double lowest, Double highest) {
        List<Product> pro = new ArrayList<>();
        for(Product p: productDAO.findByProductPriceBetween(lowest, highest)) {
            if(p.isAvailable()) {
                pro.add(p);
            }
        }
        return pro;
    }

    public List<Product> getProductByCategory(String category) {
        List<Product> pro = new ArrayList<>();
        for(Product p: productDAO.findByCategory(category)) {
            if(p.isAvailable()) {
                pro.add(p);
            }
        }
        return pro;
    }

    public Product getProductByName(String productName) {
        return productDAO.findByProductName(productName);
    }

    public void deleteProduct(Integer productId){
        productDAO.findById(productId).get().setAvailable(false);
    }



}
