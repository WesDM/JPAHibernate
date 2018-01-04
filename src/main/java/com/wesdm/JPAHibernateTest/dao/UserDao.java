package com.wesdm.JPAHibernateTest.dao;

import java.util.List;

import com.wesdm.JPAHibernateTest.model.User;

public interface UserDao extends GenericDao<User, Long> {

	int[][] batchInsertJDBC(List<User> users, int batchSize);

}

//public interface UserDao extends JpaRepository<User, Long>{
//
//}
