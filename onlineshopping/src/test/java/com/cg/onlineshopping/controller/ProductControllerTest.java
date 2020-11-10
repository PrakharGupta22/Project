package com.cg.onlineshopping.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.onlineshopping.entities.Category;
import com.cg.onlineshopping.entities.Product;
import com.cg.onlineshopping.exception.ProductAlreadyExistsException;
import com.cg.onlineshopping.exception.ProductNotFoundException;
import com.cg.onlineshopping.model.CreateProductRequest;
import com.cg.onlineshopping.model.ProductDetails;
import com.cg.onlineshopping.model.UpdateProductRequest;
import com.cg.onlineshopping.service.IProductService;
import com.cg.onlineshopping.util.ProductUtil;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductControllerTest {

	@Mock
	IProductService service;

	@Mock
	ProductUtil productUtil;

	@InjectMocks
	ProductController controller;

	
	   @BeforeEach
	   public void setUp() {
		      
	   }
	   
	   @Test
	   public void testAddProductSuccess() throws ProductAlreadyExistsException {
		   //Mock Service Layer
		   Category category=new Category("101","Gaming");

			Product product = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category);
		  
			when(service.addProduct(Mockito.any(Product.class))).thenReturn(product);

			//Create Sample CreateCustomerRequest that needs to be passed in controller
			CreateProductRequest req = new CreateProductRequest();
			
			
			//Call Controller and assert
			Assertions.assertEquals(HttpStatus.OK, controller.addProduct(req).getStatusCode());
			
	   }

	   @Test
	   public void testAddProductFailure() throws ProductAlreadyExistsException {
	   	//Mock Service Layer
		   when(service.addProduct(Mockito.any(Product.class))).thenThrow(new ProductAlreadyExistsException("Sample error"));

		   //Create Sample CreateCustomerRequest that needs to be passed in controller
		   CreateProductRequest req = new CreateProductRequest();
		   
		   //Call controller and assert
		   Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, controller.addProduct(req).getStatusCode());
	   }
	   
	   
	   @Test
	   public void testViewAllProductsSuccessRequest() throws Exception  {
		   
		   //Mock service layer call
		   Category category1=new Category("101","Gaming");

			Product product1 = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category1);
			Category category2=new Category("102","Grocery");

			Product product2 = new Product("Tata Salt",20.00,"white","10","salt","tata",50,category2);
		   List<Product> allProductList = new ArrayList<>();
		   allProductList.add(product1);
		   allProductList.add(product2);
		   when(service.viewAllProducts()).thenReturn(allProductList);
		   
		   //Call controller and assert
		   Assertions.assertEquals(HttpStatus.OK, controller.viewAllProducts().getStatusCode());
		   
	   }
	   
	   @Test
	   public void testViewAllProductsFailureScenario() throws Exception  {
		   
		   //Mock Service Layer exception
		   when(service.viewAllProducts()).thenThrow(new Exception("Sample error"));
		   
		   //Call Controller
		   ResponseEntity<List<Product>> response = controller.viewAllProducts();
		   
		   //Assert
		   Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	   }

	   @Test
	   public void testDeleteProductSuccessScenario() throws ProductNotFoundException {
		   //Mock service layer call
		   Category category1=new Category("101","Gaming");

			Product product1 = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category1);
			Category category2=new Category("102","Grocery");

			Product product2 = new Product("Tata Salt",20.00,"white","10","salt","tata",50,category2);
		   List<Product> allProductList = new ArrayList<>();
		   allProductList.add(product1);
		   allProductList.add(product2);
		   when(service.removeProduct(product1.getProductId())).thenReturn(null);
		   //Call Controller
		   ResponseEntity<Void> response = controller.removeProduct(product1.getProductId());

		   //Call controller and assert
		   Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
	   }
	@Test
	public void testDeleteProductFailureScenario() throws ProductNotFoundException {

		//Mock Service Layer exception
		
		  Category category=new Category("101","Gaming");

			Product product = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category);
		   List<Product> allProductList = new ArrayList<>();
		   allProductList.add(product);
		when(service.removeProduct(product.getProductId())).thenThrow(new ProductNotFoundException("Sample error"));

		//Call Controller
		ResponseEntity<Void> response = controller.removeProduct(product.getProductId());

		//Assert
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	@Test
	public void testUpdateProductSuccessScenario() throws Exception{
		//Mock Service Layer

		  Category category=new Category("101","Gaming");

			Product product = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category);
		when(service.addProduct(Mockito.any(Product.class))).thenReturn(product);

		//Create Sample CreateCustomerRequest that needs to be passed in controller
		UpdateProductRequest req = new UpdateProductRequest();
		req.setColor("blue");

		//Call Controller and assert
		Assertions.assertEquals(HttpStatus.OK, controller.update(req).getStatusCode());
	}
	@Test
	public void testUpdateCustomerFailure() throws ProductNotFoundException {
		//Mock Service Layer
		when(service.updateProduct(Mockito.any(Product.class))).thenThrow(new ProductNotFoundException("Sample error"));

		//Create Sample CreateCustomerRequest that needs to be passed in controller
		UpdateProductRequest req = new UpdateProductRequest();
		

		//Call controller and assert
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, controller.update(req).getStatusCode());
	}
	@Test
	public void testViewProductSuccessRequest() throws ProductNotFoundException {

		//Mock service layer call
		 Category category1=new Category("101","Gaming");

		Product product1 = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category1);
		Category category2=new Category("102","Grocery");

		Product product2 = new Product("Tata Salt",20.00,"white","10","salt","tata",50,category2);
	   List<Product> allProductList = new ArrayList<>();
	   allProductList.add(product1);
	   allProductList.add(product2);
		when(service.viewProduct(product1.getProductId())).thenReturn(product1);

		//Call controller and assert
		Assertions.assertEquals(HttpStatus.OK, controller.viewProduct(product1.getProductId()).getStatusCode());

	}
	@Test
	public void testViewProductFailureScenario() throws ProductNotFoundException {
		 Category category1=new Category("101","Gaming");

			Product product1 = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category1);
			Category category2=new Category("102","Grocery");

			Product product2 = new Product("Tata Salt",20.00,"white","10","salt","tata",50,category2);
		   List<Product> allProductList = new ArrayList<>();
		   allProductList.add(product1);
		   allProductList.add(product2);
		//Mock Service Layer exception
		when(service.viewProduct(product1.getProductId())).thenThrow(new ProductNotFoundException("Sample error"));

		//Call Controller
		ResponseEntity<ProductDetails> response = controller.viewProduct(product1.getProductId());

		//Assert
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	@Test
	public void testViewProductsByCategorySuccessRequest() throws Exception {

		//Mock service layer call
		 Category category1=new Category("101","Gaming");

		Product product1 = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category1);
		Category category2=new Category("102","Grocery");

		Product product2 = new Product("Tata Salt",20.00,"white","10","salt","tata",50,category2);
	   List<Product> allProductList = new ArrayList<>();
	   allProductList.add(product1);
	   allProductList.add(product2);
		when( service.viewProductsByCategory(category1.getCatId())).thenReturn(allProductList);

		//Call controller and assert
		ResponseEntity<List<ProductDetails>> response=controller.viewProductsByCategory(category1.getCatId());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	@Test
	public void testViewProductsByCategoryFailureScenario() throws ProductNotFoundException {
		 Category category1=new Category("101","Gaming");

			Product product1 = new Product("Gaming Mouse",200.00,"black","10","mouse","msi",20,category1);
			Category category2=new Category("102","Grocery");

			Product product2 = new Product("Tata Salt",20.00,"white","10","salt","tata",50,category2);
		   List<Product> allProductList = new ArrayList<>();
		   allProductList.add(product1);
		   allProductList.add(product2);
		//Mock Service Layer exception
		when(service.viewProductsByCategory(category1.getCatId())).thenThrow(new ProductNotFoundException("Sample error"));

		//Call Controller
		ResponseEntity<List<ProductDetails>> response = controller.viewProductsByCategory(category1.getCatId());

		//Assert
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
}