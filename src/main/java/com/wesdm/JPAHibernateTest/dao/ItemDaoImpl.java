package com.wesdm.JPAHibernateTest.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import com.wesdm.JPAHibernateTest.model.Bid;
import com.wesdm.JPAHibernateTest.model.Item;
import com.wesdm.JPAHibernateTest.model.ItemBidSummary;

@Repository("itemDao")
public class ItemDaoImpl extends GenericDaoImpl<Item, Long> implements ItemDao {

	public ItemDaoImpl() {
		super(Item.class);
	}

	@Override
	public List<Item> findAll(boolean withBids) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Item> criteria = cb.createQuery(Item.class);
		Root<Item> i = criteria.from(Item.class);
		criteria.select(i).distinct(true) // In-memory "distinct"!
				.orderBy(cb.asc(i.get("auctionEnd")));
		if (withBids)
			i.fetch("bids", JoinType.LEFT);
		return em.createQuery(criteria).setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}
	
	@Override
	public List<Item> findAllHql(boolean withBids){
		String sql = "select i from Item i";
		if(withBids) {
			sql += " left join fetch i.bids";
		}
		return em.createQuery(sql,Item.class).getResultList();
	}
	
	@Override
	public Item findByIdWithBids(Long id) {
		return em.createQuery("select i from Item i left join fetch i.bids where i.id = :id",Item.class).setParameter("id", id).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> findByName(String name, boolean substring) {
		return em.createNamedQuery(substring ? "getItemsByNameSubstring" : "getItemsByName")
				.setParameter("itemName", substring ? ("%" + name + "%") : name).getResultList();
	}

	@Override
	public List<ItemBidSummary> findItemBidSummaries() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ItemBidSummary> criteria = cb.createQuery(ItemBidSummary.class);
		Root<Item> i = criteria.from(Item.class);
		Join<Item, Bid> b = i.join("bids", JoinType.LEFT);
		criteria.select(
				cb.construct(ItemBidSummary.class, i.get("id"), i.get("name"), i.get("auctionEnd"), cb.max(b.<BigDecimal>get("amount"))));
		criteria.orderBy(cb.asc(i.get("auctionEnd")));
		criteria.groupBy(i.get("id"), i.get("name"), i.get("auctionEnd"));
		return em.createQuery(criteria).getResultList();
	}
	
	@Override
	public List<ItemBidSummary> findSummaries(){
		//DTO (data transfer objects) are good for read only queries
		String sql = "select new com.wesdm.JPAHibernateTest.model.ItemBidSummary(i.id, i.name, i.auctionEnd, max(b.amount))"
				+ "from Item i left join i.bids b on i.id=b.item.id group by i.id";
		
		return em.createQuery(sql, ItemBidSummary.class).getResultList();
	}
}
