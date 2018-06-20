package com.wesdm.JPAHibernateTest.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/*
 * you have to deal with possible shared references to Bid instances, so
 * the Bid class needs to be an entity. It has a dependent life cycle, but it must have its
 * own identity to support (future) shared references.
 */
@Entity
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_POOLED")
	@org.hibernate.annotations.GenericGenerator(name = "ID_GENERATOR_POOLED", strategy = "enhanced-sequence",
			parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "bid_seq"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
					@org.hibernate.annotations.Parameter(name = "increment_size", value = "30"),
					@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo") })
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
//	@SequenceGenerator(name = "sequence_generator", sequenceName = "bid_seq", allocationSize = 5)
    protected Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", nullable = false, updatable = false)
    protected Item item;
    

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "BIDDER_ID", nullable = false, updatable = false)
    protected User bidder;

    @NotNull
    @Column
    protected BigDecimal amount;

    public Bid() {
    }

    public Bid(BigDecimal amount, Item item) {
        this.amount = amount;
        this.item = item;
    }

    public Bid(BigDecimal bidAmount) {
    	new Bid(bidAmount, null);
    }

	public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

	public void setBidder(User user) {
		this.bidder = user;
	}

	public User getBidder() {
		return bidder;
	}

    // ...
}

