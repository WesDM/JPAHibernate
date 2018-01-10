package com.wesdm.JPAHibernateTest.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.wesdm.JPAHibernateTest.dao.UserDao;
import com.wesdm.JPAHibernateTest.model.Address;
import com.wesdm.JPAHibernateTest.model.User;

@Service
@Transactional
public class UserService {
	Logger LOGGER = LogManager.getLogger(UserService.class);	    
	
	private UserDao userDao;
	
	@Autowired
	private Environment env;
	
	
	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	public void batchInsert(int batchSize, int totalUsers) {
		//LOGGER.info(env.getProperty("hibernate.jdbc.fetch_size"));
		
		
		for(int i = 1; i <= totalUsers; i++) {
			User user = new User();
			user.setUsername("USERNAME"+i);
			user.setBillingAddress(new Address("SESAME ST", "10108", "NY"));
			user.setHomeAddress(new Address("SESAME ST", "10108", "NY"));
			userDao.makePersistent(user);
			if(i % batchSize == 0) {
				userDao.getEntityManager().flush();
				userDao.getEntityManager().clear();
			}
		}
		userDao.getEntityManager().flush();
		userDao.getEntityManager().clear();
	}
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	public void batchInsertJdbc(int batchSize, int totalUsers) {
		List<User> users = Lists.newArrayList();
		for(int i = 1; i <= totalUsers; i++) {
			User user = new User();
			user.setUsername("USERNAME"+i);
			user.setBillingAddress(new Address("SESAME ST", "10108", "NY"));
			user.setHomeAddress(new Address("SESAME ST", "10108", "NY"));
			users.add(user);
		}
		userDao.batchInsertJDBC(users, batchSize);
	}


	public int deleteAll() {
		return userDao.deleteAll();
	}
}
