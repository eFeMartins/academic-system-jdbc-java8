package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	};

	public void update(Student obj) {

	};

	public void deleteById(Integer id) {

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

				student.setStudent_id(rs.getNString("student_id"));
				student.setName(rs.getString("name"));
				student.setMajor(rs.getNString("major"));
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
		return null;
	};
}
