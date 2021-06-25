package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Adiacenze;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> listAllCategoryId(){
		String sql = "SELECT DISTINCT e.offense_category_id "
				+ "FROM EVENTS  e " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_category_id"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listAllMonth(){
		String sql = "SELECT DISTINCT MONTH(e.reported_date) as mounth "
				+ "FROM EVENTS e "
				+ "order by MONTH(e.reported_date) ASC ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("mounth"));
				} catch (Throwable t) {
					t.printStackTrace();
					
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
		
		
		public List<String> listAllVertex( String cat_id, int month) {
			
			String sql = "SELECT distinct e.offense_type_id as offid "
					+ "FROM EVENTS e "
					+ "WHERE Month(e.reported_date)= ? "
					+ "AND e.offense_category_id = ? ";
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				
				List<String> list = new ArrayList<>() ;
				st.setInt(1, month);
				st.setString(2, cat_id);
				
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					
						list.add(res.getString("offid"));
					
				}
				
				conn.close();
				return list ;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null ;
			}
		}
		
		
		
		
		
		
public List<Adiacenze> listAllAdiacenze( String cat_id, int month) {
			
			String sql = "SELECT e1.offense_type_id AS off1,e2.offense_type_id AS off2, COUNT( distinct e1.neighborhood_id) AS peso "
					+ "FROM EVENTS e1, EVENTS e2 "
					+ "WHERE e1.offense_category_id=e2.offense_category_id "
					+ "AND e1.neighborhood_id=e2.neighborhood_id "
					+ "AND e1.offense_category_id= ? "
					+ "AND e1.offense_type_id > e2.offense_type_id "
					+ "AND MONTH(e1.reported_date)=MONTH(e2.reported_date) "
					+ "AND MONTH(e1.reported_date)=? "
					+ "GROUP BY off1, off2 "
					+ "HAVING COUNT(peso)>0";
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				
				List<Adiacenze> list = new ArrayList<>() ;
				st.setString(1, cat_id);
				st.setInt(2, month);
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					try {
						list.add(new Adiacenze(res.getString("off1"),res.getString("off2"),res.getInt("peso")));
					} catch (Throwable t) {
						t.printStackTrace();
						
					}
				}
				
				conn.close();
				return list ;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null ;
			}
		}
}

