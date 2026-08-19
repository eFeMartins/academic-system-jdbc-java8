package application;

import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.entities.Student;

public class Program {
	public static void main(String[] args) {
		
		StudentDao sellerDao = DaoFactory.createStudentDao();
		
		Student student = sellerDao.findById("1240110875");
		
		System.out.println(student);
	}
}
