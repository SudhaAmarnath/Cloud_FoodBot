package edu.cloudtech.FoodBolt.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import edu.cloudtech.FoodBolt.dao.TableReservation;
import edu.cloudtech.FoodBolt.dao.TableReservationDAO;




@Service
public class ReservationService {
	
	@Autowired 
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TableReservationDAO tableReservationDAO;
	
	public void reserveTable(TableReservation tableReserve) {
		System.out.println("REserve Table" + tableReserve.getBookingDate());
		System.out.println("REserve Table TIME STAMP from User" + tableReserve.getBookingTime());
		tableReservationDAO.save(tableReserve);
	}
	
	public TableReservation getReservationByID(int BookingID) {
		System.out.println("Booking ID " + BookingID);
		return tableReservationDAO.findById(BookingID).orElse(null);
	}
	
	
	public List<TableReservation> getReservationByRestaurantID(int restID) {
		/*System.out.println("Booking ID " + BookingID);
		return tableReservationDAO.findById(restID).orElse(null);*/
		
		Date date = new Date();
		
		System.out.println("UserID" + restID);
		List<TableReservation> reservationList= new ArrayList<TableReservation>();
		Connection connection = null;
		
		try {
		
		String SELECT_SQL = "SELECT * FROM TABLE_RESERVATION where RESTAURANT_ID = ? ";
		
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_SQL, restID);
		System.out.println("Title  -----" );
		
			for(Map reservation: rows) {
				TableReservation reserv = new TableReservation();
				reserv.setBookingID((int)reservation.get("BOOKING_ID"));
				reserv.setBookingDate((Date)reservation.get("BOOKING_DATE"));
				reserv.setBookingTime((Date)reservation.get("BOOKING_TIME"));
				reserv.setStatus((String)reservation.get("STATUS"));
				reserv.setNo_of_guests((int)reservation.get("NO_OF_GUESTS"));
				
			
				reservationList.add(reserv);
			}
		}catch (Exception e) {

	        throw new RuntimeException(e);

	    } finally {
	        if (connection != null) {
	            try {
	            	connection.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	            }

	        }
	    }	
		
		return reservationList;	
	}
	
	public void cancelReservation(int BookingID) {
		System.out.println("Booking ID " + BookingID);
		
		Connection connection = null;
		System.out.println("Cancell Booking for" + BookingID);
	
		
		String UPDT_SQL = "UPDATE TABLE_RESERVATION SET STATUS = ?  where BOOKING_ID= ? ";
		
		try{
		connection = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement preparedstatement = connection.prepareStatement(UPDT_SQL);
		preparedstatement.setString(1, "CANCELLED");
		preparedstatement.setInt(2, BookingID);
		
		preparedstatement.executeUpdate();
		preparedstatement.close();
		}catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {
            if (connection != null) {
                try {
                	connection.close();
                } catch (SQLException e) {}

            }
        }
	}
	
}
		
		
		
		
