package model.dao;

import java.util.List;

import model.entities.Student;

public interface StudentDao {
	public void insert(Student obj);
	public void update(Student obj);
	public void deleteById(String id);
	public Student findById(String id);
	public List<Student> findAll();
}
