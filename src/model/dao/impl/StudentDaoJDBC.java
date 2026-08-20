package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.StudentDao;
import model.entities.Student;

public class StudentDaoJDBC implements StudentDao {

	private Connection conn;

	public StudentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	public void insert(Student obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
						"INSERT INTO student (student_id, name, major, enrollment_date) "
						+ "VALUES(?, ?, ?, ?)"
					);
			st.setString(1, obj.getStudent_id());
			st.setString(2, obj.getName());
			st.setString(3, obj.getMajor());
			st.setDate(4, Date.valueOf(obj.getEnrollmentDate()));
			
			st.executeUpdate();
			
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	};

	public void update(Student obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE student "
					+ "SET name = ?, "
					+ "major = ?, "
					+ "enrollment_date = ? "
					+ "WHERE student_id = ?"
					
					);
			st.setString(1, obj.getName());
			st.setString(2, obj.getMajor());
			st.setDate(3, Date.valueOf(obj.getEnrollmentDate()));
			st.setString(4, obj.getStudent_id());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
			
	};

	public void deleteById(String id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM student "
					+ "WHERE student_id = ?"
				);
			
			st.setString(1, id);
			st.executeUpdate();
		
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	};

	public Student findById(String student_id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT * FROM student" 
					+ " WHERE student_id = ?"
			);
			
			st.setString(1, student_id);
			rs = st.executeQuery();

			Student student = null;

			if (rs.next()) {
				student = new Student();

				student.setStudent_id(rs.getString("student_id"));
				student.setName(rs.getString("name"));
				student.setMajor(rs.getString("major"));
				student.setEnrollmentDate(rs.getDate("enrollment_date").toLocalDate());
				student.setTranscript(null);
				
				return student;
			}
			return null;
				
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	};

	public List<Student> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Student> list = new ArrayList<>();
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM student"
				);
			rs = st.executeQuery();
			
			while (rs.next()) {
				Student temp = new Student();
				
				temp.setStudent_id(rs.getNString("student_id"));
				temp.setName(rs.getString("name"));
				temp.setMajor(rs.getNString("major"));
				temp.setEnrollmentDate(rs.getDate("enrollment_date").toLocalDate());
				temp.setTranscript(null);
				
				list.add(temp);
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
