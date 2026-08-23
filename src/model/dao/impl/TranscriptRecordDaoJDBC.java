package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
import model.dao.TranscriptRecordDao;
import model.entities.ApprovalStatus;
import model.entities.TranscriptRecord;

public class TranscriptRecordDaoJDBC implements TranscriptRecordDao {
	
	private Connection conn;
	
	public TranscriptRecordDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public void insert(TranscriptRecord obj, String student_id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO transcript_record (student_id, course_code, final_grade, approval_status) "
					+ "VALUES (?, ?, ?, ?) "
					);
			st.setString(1, student_id);
			st.setString(2, obj.getCourse().getCode());
			st.setDouble(3, obj.getFinalGrade());
			st.setString(4, obj.getApprovalStatus().name());
			
			st.executeUpdate();	
		} catch (SQLException e) {
			throw new DbException(e.getMessage()); 
		}finally{
			DB.closeStatement(st);
		}
		
	};
	public void update(TranscriptRecord obj, String student_id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE transcript_record "
					+ "SET final_grade = ?, "
					+ "approval_status = ? "
					+ "WHERE student_id = ? AND course_code = ?"
				);
				
		st.setDouble(1, obj.getFinalGrade());
		st.setString(2, obj.getApprovalStatus().name());
		st.setString(3, student_id);
		st.setString(4, obj.getCourse().getCode());
		
		st.executeUpdate();
		
		} catch (SQLException e) {
			throw new DbException(e.getMessage()); 
		}finally{
			DB.closeStatement(st);
		}
		
	};
	public void deleteById(String student_id, String course_code) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
							"DELETE FROM transcript_record "
							+ "WHERE student_id = ? AND course_code = ?"
					);
					
					st.setString(1, student_id);
					st.setString(2, course_code);
					st.executeUpdate();
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage()); 
		}finally{
			DB.closeStatement(st);
		}
	};
	public TranscriptRecord findById(String student_id, String course_code) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		TranscriptRecord aux = new TranscriptRecord();
		
		try {
			st = conn.prepareStatement(
							"SELECT * FROM transcript_record "
							+ "WHERE student_id = ? AND course_code = ?"
					);
					
					st.setString(1, student_id);
					st.setString(2, course_code);
					rs = st.executeQuery();
					
					if (rs.next()) {
						aux.setCourse(DaoFactory.createCourseDao().findById(course_code)); 
						aux.setFinalGrade(rs.getDouble("final_grade"));
						aux.setApprovalStatus(ApprovalStatus.valueOf(rs.getString("approval_status")));
						
						return aux;
					}
					return null;
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage()); 
		}finally{
			DB.closeResultSet(rs);
		    DB.closeStatement(st);
		}
		
	};
	public List<TranscriptRecord> findAll(){
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List<TranscriptRecord> list = new ArrayList<>();

		
		try {
			
			st = conn.prepareStatement(
						"SELECT * FROM transcript_record "
					);
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				TranscriptRecord aux = new TranscriptRecord();
				aux.setCourse(DaoFactory.createCourseDao().findById(rs.getString("course_code"))); 
				aux.setFinalGrade(rs.getDouble("final_grade"));
				
				String statusDb = rs.getString("approval_status");
				if (statusDb != null) {
				    aux.setApprovalStatus(ApprovalStatus.valueOf(statusDb.trim().toUpperCase()));
				}
				
				list.add(aux);
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage()); 
		}finally{
			DB.closeResultSet(rs);
		    DB.closeStatement(st);
		}
	};
	
}
