package model.dao;

import java.util.List;

import model.entities.TranscriptRecord;

public interface TranscriptRecordDao {
	public void insert(TranscriptRecord obj, String student_id);
	public void update(TranscriptRecord obj, String student_id);
	public void deleteById(String student_id, String course_code);
	public TranscriptRecord findById(String student_id, String course_code);
	public List<TranscriptRecord> findByStudent(String studentId);
	public List<TranscriptRecord> findByCourse(String course_code);
	public List<TranscriptRecord> findAll();
}
