package com.wesdm.JPAHibernateTest.service;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wesdm.JPAHibernateTest.dao.UserDao;
import com.wesdm.JPAHibernateTest.model.Address;
import com.wesdm.JPAHibernateTest.model.User;

@Service
public class UserService {
	Logger LOGGER = LogManager.getLogger(UserService.class);	    
	
	private UserDao userDao;
	
	@Autowired
	private Environment env;
	
	
	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Transactional
	public void batchInsertUsers(int batchSize, int totalUsers) {
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
}
