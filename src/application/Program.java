package application;

import java.time.LocalDate;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.entities.Student;

public class Program {
	public static void main(String[] args) {
		
		StudentDao studentDao = DaoFactory.createStudentDao();
		
		System.out.println("==== findById ====");
		Student student = studentDao.findById("1240110875");
		System.out.println(student);
		
		System.out.println("==== findAll ====");
		List<Student> list = studentDao.findAll();
		
		for (Student std : list) {
			System.out.println(std);
		}
		
		System.out.println("==== insert ====");
		Student student2 = new Student("1111111111", "Fulano da Silva", "Matematica", LocalDate.now(), null);
		studentDao.insert(student2);
		System.out.println(student2);
		
		
		System.out.println("==== update ====");
		student2.setName("Ciclano Santos");
		studentDao.update(student2);
		System.out.println(student2);
		
		System.out.println("==== deleteById ====");
		studentDao.deleteById("1111111111");
		
		
	}
}
