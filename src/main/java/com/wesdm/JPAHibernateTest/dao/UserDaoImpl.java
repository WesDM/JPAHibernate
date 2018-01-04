package com.wesdm.JPAHibernateTest.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.wesdm.JPAHibernateTest.model.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//@Autowired
	public UserDaoImpl(JdbcTemplate jdbcTemplate) {
		super(User.class);
		this.jdbcTemplate = jdbcTemplate;
	}

//	@Override
//	public void batchInsertJDBC(List<User> users) {
//		// TODO Auto-generated method stub
//		
//	}
	
	@Override
	public int[][] batchInsertJDBC(List<User> users, int batchSize) {
		String sql = "insert into USERS (BILLING_CITY, BILLING_STREET, BILLING_ZIPCODE, city, street, zipcode, username, id) "
				+ "values (?, ?, ?, ?, ?, ?, ?, users_seq.nextval)";
		
        int[][] updateCounts = jdbcTemplate.batchUpdate(
                sql,
                users,
                batchSize,
                new ParameterizedPreparedStatementSetter<User>() {
                	public void setValues(PreparedStatement ps, User user) throws SQLException {
                        ps.setString(1, user.getBillingAddress().getCity());
                        ps.setString(2, user.getBillingAddress().getStreet());
                        ps.setString(3, user.getBillingAddress().getZipcode());
                        ps.setString(4, user.getHomeAddress().getCity());
                        ps.setString(5, user.getHomeAddress().getStreet());
                        ps.setString(6, user.getHomeAddress().getZipcode());
                        ps.setString(7, user.getUsername());
                    }
                });

        return updateCounts;
	}
}
