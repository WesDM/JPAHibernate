package com.wesdm.JPAHibernateTest;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
		userService.batchInsertUsers(batchSize,numOfEntities);
		itemService.batchInsertItems(batchSize, numOfEntities);
		Item item = itemService.getItemWithBids(1L);
		itemService.placeBid(item, BigDecimal.valueOf(3.50), 1L);
		final long endTime = System.currentTimeMillis();

		LOGGER.info("Total execution time: " + (endTime - startTime));

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
