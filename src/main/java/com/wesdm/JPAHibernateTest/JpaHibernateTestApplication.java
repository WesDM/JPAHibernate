package com.wesdm.JPAHibernateTest;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wesdm.JPAHibernateTest.dao.ItemDao;
import com.wesdm.JPAHibernateTest.model.Item;
import com.wesdm.JPAHibernateTest.model.ItemBidSummary;
import com.wesdm.JPAHibernateTest.service.ItemService;
import com.wesdm.JPAHibernateTest.service.UserService;

public class JpaHibernateTestApplication {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("persistenceJPA.xml");

		UserService userService = (UserService) context.getBean("userService");
		ItemService itemService = (ItemService) context.getBean("itemService");
		ItemDao itemDao = (ItemDao) context.getBean("itemDao");
		Logger LOGGER = LogManager.getLogger(JpaHibernateTestApplication.class);

		final long startTime = System.currentTimeMillis();

		int batchSize = 25;
		int numOfEntities = 25;

//		userService.batchInsertJdbc(batchSize, numOfEntities);
//		int d = userService.deleteAll();
//
//		assert d == numOfEntities : "Delete not equal to total";

		userService.batchInsert(batchSize, numOfEntities);
		itemService.batchInsert(batchSize, numOfEntities);

		Item item = placeBidsOnItems(itemService, 3, 9);
		
		for(ItemBidSummary s : itemDao.findSummaries()) {
			LOGGER.info(s);
		}
		//itemService.logBidAmounts();

		itemService.endAuction(item);

		final long endTime = System.currentTimeMillis();

		LOGGER.info("Total execution time: " + (endTime - startTime));

		if (Boolean.valueOf(args[0])) {
			try {
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				context.registerShutdownHook();
			}
		}
	}

	private static Item placeBidsOnItems(ItemService itemService, int numOfItems, int numOfBids) {
		Item item = null;
		for (int i = 1; i <= numOfItems; i++) {
			for (int j = 1; j <= numOfBids; j++) {
				item = itemService.getItemWithBids((long) i);
				itemService.placeBid(item, BigDecimal.valueOf(3.00 + j), (long) j);
			}
		}
		return item;
	}

}
