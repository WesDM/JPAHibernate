package com.wesdm.JPAHibernateTest;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.wesdm.JPAHibernateTest.dao.ItemDao;
import com.wesdm.JPAHibernateTest.model.Bid;
import com.wesdm.JPAHibernateTest.model.Item;
import com.wesdm.JPAHibernateTest.service.ItemService;
import com.wesdm.JPAHibernateTest.service.UserService;

public class JpaHibernateTestApplication {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("persistenceJPA.xml");

		UserService userService = (UserService) context.getBean("userService");
		ItemService itemService = (ItemService) context.getBean("itemService");

		Logger LOGGER = LogManager.getLogger(JpaHibernateTestApplication.class);

		final long startTime = System.currentTimeMillis();

		int batchSize = 25;
		int numOfEntities = 100;

		// userService.batchInsertJdbc(batchSize, numOfEntities);

		userService.batchInsert(batchSize, numOfEntities);
		itemService.batchInsert(batchSize, numOfEntities);

		Item item = itemService.getItemWithBids(1L);

		itemService.placeBid(item, BigDecimal.valueOf(3.50), 1L);
		itemService.placeBid(item, BigDecimal.valueOf(3.60), 2L);
		itemService.placeBid(item, BigDecimal.valueOf(3.70), 3L);
		itemService.placeBid(item, BigDecimal.valueOf(3.80), 4L);
		itemService.placeBid(item, BigDecimal.valueOf(3.90), 5L);

		item = itemService.getItemWithBids(2L);

		itemService.placeBid(item, BigDecimal.valueOf(3.50), 1L);
		itemService.placeBid(item, BigDecimal.valueOf(3.60), 2L);
		itemService.placeBid(item, BigDecimal.valueOf(3.70), 3L);
		itemService.placeBid(item, BigDecimal.valueOf(3.80), 4L);
		itemService.placeBid(item, BigDecimal.valueOf(3.90), 5L);
		
		item = itemService.getItemWithBids(3L);

		itemService.placeBid(item, BigDecimal.valueOf(3.50), 1L);
		itemService.placeBid(item, BigDecimal.valueOf(3.60), 2L);
		itemService.placeBid(item, BigDecimal.valueOf(3.70), 3L);
		itemService.placeBid(item, BigDecimal.valueOf(3.80), 4L);
		itemService.placeBid(item, BigDecimal.valueOf(3.90), 5L);

		itemService.logBidAmounts();

		// itemService.endAuction(item);
		//
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

}
