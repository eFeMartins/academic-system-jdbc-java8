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
			
			for (Course item : obj.getPrerequisites()) {
				st = conn.prepareStatement(
						"INSERT INTO course_prerequisite (course_code, prerequisite_code) "
						+ "VALUES (?, ?)"
						);
				
				st.setString(1, obj.getCode());
				st.setString(2, item.getCode());
				
				st.executeUpdate();
			}
			
			
					
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
							+ "SET name = ?, "
							+ "credits = ? "
							+ "WHERE code = ?"
					);
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getCredits());
			st.setString(2, obj.getCode());
			
			st.executeUpdate();
			
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	};
	public void deleteById(String code) {
		PreparedStatement st = null;
		PreparedStatement stPre = null;
		try {
			stPre = conn.prepareStatement(
						"DELETE FROM course_prerequisite "
						+ "WHERE course_code = ?"
					);
			stPre.setString(1, code);
			stPre.executeUpdate();
			
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
	    
	    PreparedStatement stPre = null; 
	    ResultSet rsPre = null;

	    try {
	        st = conn.prepareStatement("SELECT * FROM course WHERE code = ?");
	        st.setString(1, code);
	        rs = st.executeQuery();

	        if (rs.next()) {
	            Course course = new Course();
	            course.setCode(rs.getString("code"));
	            course.setName(rs.getString("name"));
	            course.setCredits(rs.getInt("credits"));
	            
	            
	            String sqlPre = "SELECT p.* "
	                          + "FROM course p " 
	                          + "JOIN course_prerequisite cp ON p.code = cp.prerequisite_code "
	                          + "WHERE cp.course_code = ?";
	                          
	            stPre = conn.prepareStatement(sqlPre);
	            stPre.setString(1, course.getCode());
	            rsPre = stPre.executeQuery(); 
	            
	            List<Course> prerequisites = new ArrayList<>();
	            while (rsPre.next()) {
	                Course pre = new Course();
	                pre.setCode(rsPre.getString("code"));
	                pre.setName(rsPre.getString("name"));
	                pre.setCredits(rsPre.getInt("credits"));
	                prerequisites.add(pre);
	            }
	            course.setPrerequisites(prerequisites);
	            
	            return course;
	        }
	        return null;
	    } catch(SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeResultSet(rsPre);
	        DB.closeStatement(stPre);
	        DB.closeResultSet(rs);
	        DB.closeStatement(st);
	    }
	}
	
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
