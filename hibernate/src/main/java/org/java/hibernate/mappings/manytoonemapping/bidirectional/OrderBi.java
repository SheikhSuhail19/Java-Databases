package org.java.hibernate.mappings.manytoonemapping.bidirectional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderBi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String product;

	@ManyToOne
	@JoinColumn(name = "customer_id") // Foreign key column in OrderBi table
	private CustomerBi customerBi;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public CustomerBi getCustomerBi() {
		return customerBi;
	}

	public void setCustomerBi(CustomerBi customerBi) {
		this.customerBi = customerBi;
	}
}
