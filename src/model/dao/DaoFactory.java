package model.dao;

import db.DB;
import model.dao.impl.CourseDaoJDBC;
import model.dao.impl.StudentDaoJDBC;

public class DaoFactory {
	public static StudentDao createStudentDao() {
		return new StudentDaoJDBC(DB.getConnection());
	}
	public static CourseDao createCourseDao() {
		return new CourseDaoJDBC(DB.getConnection());
	}
}
