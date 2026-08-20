package application;

import java.util.List;

import model.dao.CourseDao;
import model.dao.DaoFactory;
import model.entities.Course;

public class Program {
	public static void main(String[] args) {

		CourseDao courseDao = DaoFactory.createCourseDao();
		
		System.out.println("==== findById ====");
		Course aux = courseDao.findById("COMP02");
		System.out.println(aux);

		System.out.println("==== insert ====");
		Course aux2 = new Course("COMP06", "UX/UI", 3, null);
		courseDao.insert(aux2);
		
		System.out.println("==== findAll ====");
		List<Course> list = courseDao.findAll();
		for (Course course : list) {
			System.out.println(course);
		}
		
		System.out.println("==== update ====");
		aux2.setCredits(2);
		courseDao.update(aux2);
		System.out.println(aux2);
		
		System.out.println("==== deleteById ====");
		courseDao.deleteById("COMP06");
		
//		StudentDao studentDao = DaoFactory.createStudentDao();
//		
//		System.out.println("==== findById ====");
//		Student student = studentDao.findById("1240110875");
//		System.out.println(student);
//		
//		System.out.println("==== findAll ====");
//		List<Student> list = studentDao.findAll();
//		
//		for (Student std : list) {
//			System.out.println(std);
//		}
//		
//		System.out.println("==== insert ====");
//		Student student2 = new Student("1111111111", "Fulano da Silva", "Matematica", LocalDate.now(), null);
//		studentDao.insert(student2);
//		System.out.println(student2);
//		
//		
//		System.out.println("==== update ====");
//		student2.setName("Ciclano Santos");
//		studentDao.update(student2);
//		System.out.println(student2);
//		
//		System.out.println("==== deleteById ====");
//		studentDao.deleteById("1111111111");
//		
		
	}
}
