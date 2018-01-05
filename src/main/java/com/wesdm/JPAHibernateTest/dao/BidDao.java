package com.wesdm.JPAHibernateTest.dao;

import java.math.BigDecimal;

import com.wesdm.JPAHibernateTest.model.Bid;
import com.wesdm.JPAHibernateTest.model.Item;
import com.wesdm.JPAHibernateTest.model.User;

public interface BidDao extends GenericDao<Bid,Long>{

	BigDecimal getHighestBidAmount(Item item);

	User getHighestBidder(Item item);
}
