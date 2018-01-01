package com.wesdm.JPAHibernateTest.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_POOLED")
	@org.hibernate.annotations.GenericGenerator(name = "ID_GENERATOR_POOLED", strategy = "enhanced-sequence",
			parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "users_seq"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
					@org.hibernate.annotations.Parameter(name = "increment_size", value = "25"),
					@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo") })
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	// @SequenceGenerator(name = "sequence_generator", sequenceName = "users_seq", allocationSize = 5)
	protected Long id;

	// DO WE NEED MAP THE FOLLOWING IF WE CAN WRITE QUERIES TO GET THEM?
	// @OneToMany(mappedBy = "bidder", fetch = FetchType.LAZY)
	// protected Set<Bid> bids = new HashSet<>();
	//
	// @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
	// protected Set<Item> itemsForAuction = new HashSet<>();
	//
	 @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
	 protected Set<Item> boughtItems = new HashSet<>();

	public Long getId() {
		return id;
	}

	// public Set<Bid> getBids() {
	// return bids;
	// }
	//
	// public void setBids(Set<Bid> bids) {
	// this.bids = bids;
	// }

	protected String username;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// The Address is @Embeddable, no annotation needed here...
	protected Address homeAddress;

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	@Embedded // Not necessary...
	@AttributeOverrides({ @AttributeOverride(name = "street", column = @Column(name = "BILLING_STREET")), // NULLable!
			@AttributeOverride(name = "zipcode", column = @Column(name = "BILLING_ZIPCODE", length = 5)),
			@AttributeOverride(name = "city", column = @Column(name = "BILLING_CITY")) })
	protected Address billingAddress;

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public BigDecimal calcShippingCosts(Address fromLocation) {
		// Empty implementation of business method
		return null;
	}

	// public void addBid(Bid bid) {
	// bids.add(bid);
	// bid.setBidder(this);
	// }

	// public void removeBid(Bid bid) {
	// bids.remove(bid);
	// bid.setBidder(null);
	// }
	//
	// public void addItemForAuction(Item item) {
	// itemsForAuction.add(item);
	// item.setSeller(this);
	// }
	//
	// public void removeItemForAuction(Item item) {
	// itemsForAuction.remove(item);
	// item.setSeller(null);
	// }
	
	public void buyItem(Item item) {
		item.setBuyer(this);
		this.boughtItems.add(item);
	}
	// ...
}
