package com.cg.onlineshopping.service;

import java.util.List;

import com.cg.onlineshopping.entities.Product;
import com.cg.onlineshopping.exception.ProductAlreadyExistsException;
import com.cg.onlineshopping.exception.ProductNotFoundException;

public interface IProductService {
 public List<Product> viewAllProducts() throws Exception;
 public Product addProduct(Product product) throws ProductAlreadyExistsException;
 public Product updateProduct(Product product) throws ProductNotFoundException;
 public Product viewProduct(Integer id) throws ProductNotFoundException;
 public List<Product> viewProductsByCategory(String catId) throws ProductNotFoundException; 
 public Product removeProduct(Integer productId) throws ProductNotFoundException;

}
