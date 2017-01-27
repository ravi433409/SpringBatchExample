/*package com.fsfn.springbatch.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import com.fsfn.springbatch.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
  
	private JdbcTemplate jdbcTemplate;
    private DriverManagerDataSource dataSource;
    
    
    @Autowired
    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(User usr) {
        String sql = "INSERT INTO USER " +
                "(USR_ID, EMAIL, PHONE) VALUES (?, ?, ?)";

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(sql);
    }

    public void init() {
        String sql = "CREATE TABLE USER (USR_ID INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,EMAIL VARCHAR(30) NOT NULL,PHONE VARCHAR(15) NOT NULL)";
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(sql);
    }

	@Override
	public User select(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}*/