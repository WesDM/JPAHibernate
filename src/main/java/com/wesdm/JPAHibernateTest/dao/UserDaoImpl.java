package com.wesdm.JPAHibernateTest.dao;

import org.springframework.stereotype.Repository;

import com.wesdm.JPAHibernateTest.model.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}
}
