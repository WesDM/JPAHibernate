package com.wesdm.JPAHibernateTest.dao;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.wesdm.JPAHibernateTest.model.Bid;
import com.wesdm.JPAHibernateTest.model.Item;

@Repository
public class BidDaoImpl extends GenericDaoImpl<Bid, Long> implements BidDao{

	protected BidDaoImpl() {
		super(Bid.class);
	}

	@Override
	public BigDecimal getHighestBidAmount(Item item) {
		BigDecimal highest = em.createQuery("select max(b.amount) from Bid b where b.item.id = :itemId",BigDecimal.class).setParameter("itemId",item.getId()).getSingleResult();
		return highest;
	}

}
