package model.dao;

import java.util.List;

import model.entities.TranscriptRecord;

public interface TranscriptRecordDao {
	public void insert(TranscriptRecord obj);
	public void update(TranscriptRecord obj);
	public void deleteById(Integer id);
	public TranscriptRecord findById(Integer id);
	public List<TranscriptRecord> findAll();
}
