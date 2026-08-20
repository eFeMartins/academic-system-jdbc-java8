package model.dao;

import java.util.List;

import model.entities.Course;

public interface CourseDao {
	public void insert(Course obj);
	public void update(Course obj);
	public void deleteById(String id);
	public Course findById(String id);
	public List<Course> findAll();
}
