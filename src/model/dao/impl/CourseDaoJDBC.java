package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.CourseDao;
import model.entities.Course;

public class CourseDaoJDBC implements CourseDao{
	
	private Connection conn;
	
	public CourseDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public void insert(Course obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
						"INSERT INTO course (code, name, credits) "
						+ "VALUES(?, ?, ?)"
					);
			
			st.setString(1, obj.getCode());
			st.setString(2, obj.getName());
			st.setInt(3, obj.getCredits());
			
			st.executeUpdate();
			
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	};
	public void update(Course obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE course "
							+ "SET "
							+ "name = ?, "
							+ "credits = ? "
					);
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getCredits());
			
			st.executeUpdate();
			
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	};
	public void deleteById(String code) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM course "
					+ "WHERE code = ?"
				);
			
			st.setString(1, code);
			st.executeUpdate();
		
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	};
	public Course findById(String code) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT * FROM course" 
					+ " WHERE code = ?"
			);
			
			st.setString(1, code);
			rs = st.executeQuery();

			Course aux = null;

			if (rs.next()) {
				aux = new Course();

				aux.setCode(rs.getString("code"));
				aux.setName(rs.getString("name"));
				aux.setCredits(rs.getInt("credits"));
				aux.setPrerequisites(null);
				
				return aux;
			}
			return null;
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	};
	
	public List<Course> findAll(){
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Course> list = new ArrayList<>();
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM course"
				);
			rs = st.executeQuery();
			
			while (rs.next()) {
				Course aux = new Course();
				
				aux.setCode(rs.getString("code"));
				aux.setName(rs.getString("name"));
				aux.setCredits(rs.getInt("credits"));
				aux.setPrerequisites(null);
				
				list.add(aux);
			}
			
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	};
}
