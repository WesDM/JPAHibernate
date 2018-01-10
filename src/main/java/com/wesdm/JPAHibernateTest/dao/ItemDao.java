package com.wesdm.JPAHibernateTest.dao;

import java.util.List;

import com.wesdm.JPAHibernateTest.model.Item;
import com.wesdm.JPAHibernateTest.model.ItemBidSummary;

public interface ItemDao extends GenericDao<Item, Long> {

    List<Item> findAll(boolean withBids);

    List<Item> findByName(String name, boolean fuzzy);

    List<ItemBidSummary> findItemBidSummaries();

	Item findByIdWithBids(Long id);


	List<Item> findAllHql(boolean withBids);

	List<ItemBidSummary> findSummaries();

}

//public interface ItemDao extends JpaRepository<Item, Long>{
//	
////	@Query("select i from Item i")
////	List<Item> findAllItems();
//}
