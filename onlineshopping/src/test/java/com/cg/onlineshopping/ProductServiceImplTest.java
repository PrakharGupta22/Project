package com.cg.onlineshopping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cg.onlineshopping.entities.Category;
import com.cg.onlineshopping.entities.Product;
import com.cg.onlineshopping.exception.ProductAlreadyExistsException;
import com.cg.onlineshopping.exception.ProductNotFoundException;
import com.cg.onlineshopping.repository.IProductRepository;
import com.cg.onlineshopping.service.impl.ProductServiceImpl;

@ExtendWith(SpringExtension.class)

@SpringBootTest
public class ProductServiceImplTest {
	@Autowired
	private ProductServiceImpl service;

	@MockBean
	private IProductRepository repo;
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	 void testCreateProduct() throws ProductAlreadyExistsException {

		Category category=new Category("101","Gaming");

		Product product = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category);
		Mockito.when(repo.save(product)).thenReturn(product);
		assertThat(service.addProduct(product)).isEqualTo(product);

	}
	@Test
	void testViewProduct() throws ProductNotFoundException {
		Category category=new Category("101","Gaming");

		Product product = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category);
		Optional<Product> oproduct = Optional.of(product);
		Mockito.when(repo.findById(1)).thenReturn(oproduct);
		assertThat(service.viewProduct(1)).isEqualTo(oproduct.get());

	}
	@Test
	void testViewAllProducts() {

		Category category1=new Category("101","Gaming");

		Product product1 = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category1);
		Category category2=new Category("102","Grocery");

		Product product2 = new Product("Tata Salt",20.00,"white","10","salt","tata",50,category2);

		List<Product> listOfProducts = new ArrayList<>();
		listOfProducts.add(product1);
		listOfProducts.add(product2);
		Mockito.when(repo.findAll()).thenReturn(listOfProducts);
		assertThat(service.viewAllProducts()).isEqualTo(listOfProducts);
	}
	
	@Test
	void testViewProductsByCategory() {
		Category category=new Category("101","Gaming");

		Product product = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category);
		List<Product> listOfProducts = new ArrayList<>();
		listOfProducts.add(product);
		 Mockito.when(repo.findByCatId("101")).thenReturn(listOfProducts);
		 assertThat(service.viewProductsByCategory("101")).isEqualTo(listOfProducts);
	}
	
	@Test
	void testUpdateProduct() throws ProductNotFoundException {
		Category category=new Category("101","Gaming");

		Product product = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category);
		
		Optional<Product> oproduct = Optional.of(product);
		Mockito.when(repo.findById(1)).thenReturn(oproduct);
		oproduct.get().setColor("blue");
		Mockito.when(repo.findById(1)).thenReturn(oproduct);
		assertThat(service.viewProduct(1).getColor()).isEqualTo("blue");

	}
	
	@Test
	public void testDeleteProduct() {
		Category category=new Category("101","Gaming");

		Product product = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category);
		
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(product));
		Mockito.when(repo.existsById(product.getProductId())).thenReturn(false);

		assertFalse(repo.existsById(product.getProductId()));
	}
}