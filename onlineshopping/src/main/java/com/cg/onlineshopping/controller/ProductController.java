package com.cg.onlineshopping.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlineshopping.entities.Product;
import com.cg.onlineshopping.exception.ProductAlreadyExistsException;
import com.cg.onlineshopping.exception.ProductNotFoundException;
import com.cg.onlineshopping.model.CreateProductRequest;
import com.cg.onlineshopping.model.ProductDetails;
import com.cg.onlineshopping.model.UpdateProductRequest;
import com.cg.onlineshopping.service.IProductService;
import com.cg.onlineshopping.util.ProductUtil;

@Validated
@RequestMapping("/products")
@RestController
public class ProductController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private IProductService service;
	@Autowired
	private ProductUtil productUtil;

	@PostMapping("/add")
	public ResponseEntity<ProductDetails> addProduct(@RequestBody @Valid CreateProductRequest requestData)
			throws ProductAlreadyExistsException {
		try {
			Product product = new Product(  requestData.getProductName(), requestData.getPrice(),
					requestData.getColor(), requestData.getDimension(), requestData.getSpecification(), requestData.getManufacturer(), requestData.getQuantity() ,requestData.getCategory());
			product = service.addProduct(product);
			ProductDetails details = productUtil.toDetails(product);
			return new ResponseEntity<>(details, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("unable to add product:{} errorlog: ", requestData.getProductName(), e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/update")
	public ResponseEntity<ProductDetails> update(@RequestBody @Valid UpdateProductRequest requestData)
			throws ProductNotFoundException {
		try {
			Product product = new Product( requestData.getProductName(), requestData.getPrice(),
					requestData.getColor(), requestData.getDimension(), requestData.getSpecification(), requestData.getManufacturer(), requestData.getQuantity() ,requestData.getCategory());
			product.setProductId(requestData.getProductId());
			product= service.updateProduct(product);
			ProductDetails details = productUtil.toDetails(product);
			return new ResponseEntity<>(details, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("unable to update product for productId:{} errlog: ", requestData.getProductId(), e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/get/id/{id}")
	public ResponseEntity<ProductDetails> viewProduct(@PathVariable("id") Integer productId)
			throws ProductNotFoundException {
		try {
			Product product = service.viewProduct(productId);
			ProductDetails details = productUtil.toDetails(product);
			return new ResponseEntity<>(details, HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			LOGGER.error("unable to view product for productId:{} errlog", productId, e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/viewall")
	public ResponseEntity<List<Product>> viewAllProducts() throws Exception {
		try {
			return new ResponseEntity<>(service.viewAllProducts(), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("unable to view all the products: ", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/remove/{productId}")
	public ResponseEntity<Void> removeProduct(@PathVariable("productId") Integer productId)
			throws ProductNotFoundException {
		try {
			service.removeProduct(productId);
			  return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			LOGGER.error("unable to delete product for productId:{} errlog", productId, e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/viewProductByCategory/{catId}")
    public ResponseEntity<List<ProductDetails>> viewProductsByCategory(@PathVariable("catId") String catId)throws ProductNotFoundException 
    {
			try {
        return new ResponseEntity<>(productUtil.toDetails(service.viewProductsByCategory(catId)), HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			LOGGER.error("unable to find products with catId:{} errlog", catId, e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
		
	

}
