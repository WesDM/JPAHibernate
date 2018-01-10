package com.wesdm.JPAHibernateTest.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
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
		// LOGGER.info(env.getProperty("hibernate.jdbc.fetch_size"));

		for (int i = 1; i <= totalUsers; i++) {
			Set<Image> images = new HashSet<>();
			Image image = new Image("title" + i, "filename" + i, i, i);
			images.add(image);
			image = new Image("title0" + i, "filename0" + i, i, i);
			images.add(image);
			Item item = new Item();
			item.setName("Record");
			item.setSeller(userDao.findReferenceById((long) i));
			item.setImages(images);
			item.setAuctionStart(LocalDateTime.now());
			item.setAuctionEnd(LocalDateTime.now().plusDays(1));
			itemDao.makePersistent(item);
			if (i % batchSize == 0) {
				itemDao.getEntityManager().flush();
				itemDao.getEntityManager().clear();
			}
		}
		itemDao.getEntityManager().flush();
		itemDao.getEntityManager().clear();
	}

	public void placeBid(Item item, BigDecimal bidAmount, Long userId) {
		if (!item.hasAuctionEnded()) {
			item = itemDao.getEntityManager().merge(item);
			Bid bid = item.placeBid(bidDao.getHighestBidAmount(item), bidAmount);
			if (bid == null) {
				LOGGER.info("Bid was too low");
				return;
			}
			bid.setBidder(userDao.findReferenceById(userId));
			item.addBid(bid);
			LOGGER.info("place bid finished");
		} else {
			LOGGER.info("auction has ended");
		}
	}

	public Item getItem(Long id) {
		return itemDao.findById(id);
	}

	public Item getItemWithBids(Long id) {
		return itemDao.findByIdWithBids(id);
	}

	public void endAuction(Item item) {
		item = itemDao.getEntityManager().merge(item);
		item.setBuyer(bidDao.getHighestBidder(item));
	}

	public void logBidAmounts() {
		List<Item> items = itemDao.findAllHql(true);
		// Set<Item> items = new LinkedHashSet<>(itemDao.findAllHql(true)); //get rid of dupes
		LOGGER.info(items.size());
		// for(Item item : items)
		// LOGGER.info(item.getBids().size());
	}


}
