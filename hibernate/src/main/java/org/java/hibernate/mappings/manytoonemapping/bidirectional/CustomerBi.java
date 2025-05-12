package org.java.hibernate.mappings.manytoonemapping.bidirectional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.java.hibernate.mappings.manytoonemapping.unidirectional.Order;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CustomerBi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "customerBi", cascade = CascadeType.ALL)
	private List<OrderBi> orders = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OrderBi> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderBi> orders) {
		this.orders = orders;
	}

	public void addOrder(OrderBi order) {
		orders.add(order);
		order.setCustomerBi(this);
	}

	public void removeOrder(OrderBi order) {
		orders.remove(order);
		order.setCustomerBi(null);
	}
}

