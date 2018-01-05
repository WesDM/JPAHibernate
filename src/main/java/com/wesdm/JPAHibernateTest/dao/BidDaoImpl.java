package com.wesdm.JPAHibernateTest.dao;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.wesdm.JPAHibernateTest.model.Bid;
import com.wesdm.JPAHibernateTest.model.Item;
import com.wesdm.JPAHibernateTest.model.User;

@Repository
public class BidDaoImpl extends GenericDaoImpl<Bid, Long> implements BidDao {

	protected BidDaoImpl() {
		super(Bid.class);
	}

	@Override
	public BigDecimal getHighestBidAmount(Item item) {
		BigDecimal highest = em.createQuery("select max(b.amount) from Bid b where b.item.id = :itemId", BigDecimal.class)
				.setParameter("itemId", item.getId()).getSingleResult();
		return highest;
	}

	@Override
	public User getHighestBidder(Item item) {
		User highest = em.createQuery("select u from Bid b join b.bidder u where b.item.id = :itemId order by b.amount desc", User.class)
				.setParameter("itemId", item.getId()).setMaxResults(1).getSingleResult();
		return highest;
	}
}
