package edu.cloudtech.FoodBolt.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.cloudtech.FoodBolt.dao.CustomerDetails;
import edu.cloudtech.FoodBolt.dao.ServiceProvider;

public class ServiceProvideRowMapper implements RowMapper<ServiceProvider> {
	
	@Override
	public ServiceProvider mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println(rowNum + " test test test test");
		ServiceProvider serviceProvider = new ServiceProvider();
		serviceProvider.setEmail(rs.getString("Email"));
		serviceProvider.setPassword(rs.getString("Password"));
		serviceProvider.setRestaurant_id(rs.getInt("Restaurant_id"));
		
		return serviceProvider;
	}

}
