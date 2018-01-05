package com.wesdm.JPAHibernateTest.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import org.hibernate.annotations.BatchSize;

@Entity
public class Item {

	@Id			//designates PK
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_POOLED")
	@org.hibernate.annotations.GenericGenerator(name = "ID_GENERATOR_POOLED", strategy = "enhanced-sequence",
			parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "item_seq"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
					@org.hibernate.annotations.Parameter(name = "increment_size", value = "25"),
					@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo") })
	// @SequenceGenerator(name = "sequence_generator", sequenceName = "item_seq", allocationSize = 5) can't use pooled or pooled-lo optimizers with
	// @SequenceGenerator
	private Long id;

	private String name;

	@ElementCollection
	@CollectionTable(name = "IMAGE", joinColumns = @JoinColumn(name = "item_id")) // overrides default name for table to IMAGE
	@OrderColumn(name = "index_id") //used so Hibernate can sort insertion order after fetching. what about using timestamp to sort?
	//@OrderBy("filename, width DESC")  //executed at sql level, ordered in DB
	@AttributeOverride(name = "filename", column = @Column(name = "FNAME", nullable = false))
	private Set<Image> images = new HashSet<Image>();
	
	//@BatchSize(size=2)
	@OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Bid> bids = new HashSet<>();

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private Set<CategorizedItem> categorizedItems = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SELLER_ID", nullable = false, updatable = false)
	//seller id col will be set when item is created. it will never be null therefore JoinColumn is sufficient
	//@JoinTable causes eager left outer join on jointable bc col is nullable so hibernate doesn't know what value to assign
	//@JoinTable(name = "ITEM_SELLER", joinColumns = @JoinColumn(name = "ITEM_ID", unique = true),
		//		inverseJoinColumns = @JoinColumn(name = "SELLER_ID", nullable = false))
	private User seller;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BUYER_ID", nullable = true)
//	@JoinTable(name = "ITEM_BUYER", joinColumns = @JoinColumn(name = "ITEM_ID", unique = true),
//			inverseJoinColumns = @JoinColumn(name = "BUYER_ID"))
	private User buyer;
	// JoinTable hides the table from app view..no Java class. Good choice to avoid null columns
	
	@Column(name = "AUCTION_START")
	private LocalDateTime auctionStart;
	
	@Column(name = "AUCTION_END")
	private LocalDateTime auctionEnd;
	
	@Column(name = "CREATED_ON", updatable = false)
	@org.hibernate.annotations.CreationTimestamp
	private LocalDateTime created;

	@Column(name = "MODIFIED_ON")
	@org.hibernate.annotations.UpdateTimestamp
	private LocalDateTime updated;


	public Item() {
	}

	public Item(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<CategorizedItem> getCategorizedItems() {
		return categorizedItems;
	}

	public void addBid(Bid bid) {
		// Be defensive
		if (bid == null)
			throw new NullPointerException("Can't add null Bid");
		if (bid.getItem() != null)
			throw new IllegalStateException("Bid is already assigned to an Item");

		getBids().add(bid);
		bid.setItem(this);
	}
	

	public Bid placeBid(BigDecimal currentHighestBid, BigDecimal bidAmount) {
		if (currentHighestBid == null || bidAmount.compareTo(currentHighestBid) > 0) {
			return new Bid(bidAmount,null);
		}
		return null;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<Bid> getBids() {
		return bids;
	}

	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public User getSeller() {
		return seller;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public boolean hasAuctionEnded() {
		return auctionEnd.isBefore(LocalDateTime.now());
	}

	public LocalDateTime getAuctionStart() {
		return auctionStart;
	}

	public void setAuctionStart(LocalDateTime auctionStart) {
		this.auctionStart = auctionStart;
	}

	public LocalDateTime getAuctionEnd() {
		return auctionEnd;
	}

	public void setAuctionEnd(LocalDateTime auctionEnd) {
		this.auctionEnd = auctionEnd;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}
}
