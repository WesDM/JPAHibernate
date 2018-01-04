package com.wesdm.JPAHibernateTest.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wesdm.JPAHibernateTest.dao.BidDao;
import com.wesdm.JPAHibernateTest.dao.ItemDao;
import com.wesdm.JPAHibernateTest.dao.UserDao;
import com.wesdm.JPAHibernateTest.model.Bid;
import com.wesdm.JPAHibernateTest.model.Image;
import com.wesdm.JPAHibernateTest.model.Item;

@Service
@Transactional
public class ItemService {
	private static final Logger LOGGER = LogManager.getLogger(ItemService.class);
	
	private ItemDao itemDao;
	private BidDao bidDao;
	private UserDao userDao;
	
	@Autowired
	public ItemService(ItemDao itemDao, BidDao bidDao, UserDao userDao) {
		this.itemDao = itemDao;
		this.bidDao = bidDao;
		this.userDao = userDao;
	}
	
	public void batchInsert(int batchSize, int totalUsers) {
		//LOGGER.info(env.getProperty("hibernate.jdbc.fetch_size"));
		

		for(int i = 1; i <= totalUsers; i++) {
			Set<Image> images = new HashSet<>();
			Image image = new Image("title"+i,"filename"+i,i,i);
			images.add(image);
			image = new Image("title0"+i,"filename0"+i,i,i);
			images.add(image);
			Item item = new Item();
			item.setName("Record");
			item.setSeller(userDao.findReferenceById((long) i));
			item.setImages(images);
			itemDao.makePersistent(item);
			if(i % batchSize == 0) {
				itemDao.getEntityManager().flush();
				itemDao.getEntityManager().clear();
			}
		}
		itemDao.getEntityManager().flush();
		itemDao.getEntityManager().clear();
	}
	
	public void placeBid(Item item, BigDecimal bidAmount, Long userId) {
		item = itemDao.getEntityManager().merge(item);
		Bid bid = item.placeBid(bidDao.getHighestBidAmount(item), bidAmount);
		bid.setBidder(userDao.findById(userId));
		item.addBid(bid);
		LOGGER.info("place bid finished");
	}
	
	public Item getItem(Long id) {
		return itemDao.findById(id);
	}
	
	public Item getItemWithBids(Long id) {
		return itemDao.findByIdWithBids(id);
	}
}
