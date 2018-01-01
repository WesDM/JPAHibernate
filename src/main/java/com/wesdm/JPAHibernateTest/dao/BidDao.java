package com.wesdm.JPAHibernateTest.dao;

import java.math.BigDecimal;

import com.wesdm.JPAHibernateTest.model.Bid;
import com.wesdm.JPAHibernateTest.model.Item;

public interface BidDao extends GenericDao<Bid,Long>{

	BigDecimal getHighestBidAmount(Item item);
}
