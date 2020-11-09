package com.cg.onlineshopping.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "product")
/**
 * The Product program contains all the entities and constructor method that
 * call all the variable in Product
 * 
 * @author prakhar gupta
 * @version 1.0
 * @since 2020-10-19
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * This constructor is used to initialize objects of Product class.
	 * 
	 * @Id Specifies the primary key of an entity
	 * @Entity defines that a class can be mapped to a table
	 * @OneToOne defines the one to one relationship
	 * @Column permits the name of the column to be explicitly specified
	 * @param productId     this is the productId for Product method
	 * @param productName   this is the productName for Product method
	 * @param price         this is the price for Product method
	 * @param color         this is the color for Product method
	 * @param dimension     this is the dimension for Product method
	 * @param specification this is the specification for Product method
	 * @param manufacturer  this is the manufacturer for Product method
	 * @param quantity      this is the quantity for Product method
	 * @param category      this is the category for Product method
	 */

	@Id
	@Column(name = "productId")
	@GeneratedValue
	private Integer productId;

	@Column(name = "productName")
	private String productName;

	@Column(name = "price")
	private double price;

	@Column(name = "color")
	private String color;

	@Column(name = "dimension")
	private String dimension;

	@Column(name = "specification")
	private String specification;

	@Column(name = "manufacturer")
	private String manufacturer;

	@Column(name = "quantity")
	private int quantity;

	@Embedded
	private Category category;

	public Product() {

	}

	public Product( String productName, double price, String color, String dimension,
			String specification, String manufacturer, int quantity, Category category) {
		
		this.productName = productName;
		this.price = price;
		this.color = color;
		this.dimension = dimension;
		this.specification = specification;
		this.manufacturer = manufacturer;
		this.quantity = quantity;
		this.category = category;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", price=" + price + ", color=" + color + ", dimension=" + dimension + ", specification=" + specification + ", manufacturer=" + manufacturer + ", quantity=" +quantity + ", category=" +category + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return productId == other.productId;
	}

}
