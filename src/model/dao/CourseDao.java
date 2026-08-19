package model.dao;

import java.util.List;

import model.entities.Course;

public interface CourseDao {
	public void insert(Course obj);
	public void update(Course obj);
	public void deleteById(Integer id);
	public Course findById(Integer id);
	public List<Course> findAll();
}
