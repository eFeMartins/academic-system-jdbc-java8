package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.TranscriptRecordDao;
import model.entities.ApprovalStatus;
import model.entities.Course;
import model.entities.TranscriptRecord;

public class Program {
	public static void main(String[] args) {

		TranscriptRecordDao transcriptDao = DaoFactory.createTranscriptRecordDao();

		String studentIdTeste = "2025001000";
		Course courseTeste = new Course("COMP10", "Estrutura de Dados", 4, null);

		System.out.println("==== TEST 1: insert ====");


		TranscriptRecord newRecord = new TranscriptRecord(courseTeste, 7.5, ApprovalStatus.APPROVED);
		transcriptDao.insert(newRecord, studentIdTeste);
		System.out.println("Registro inserido com sucesso!");

		System.out.println("\n==== TEST 2: findById ====");
		TranscriptRecord aux = transcriptDao.findById(studentIdTeste, courseTeste.getCode());
		if (aux != null) {
			System.out.println("Encontrado: " + aux);
		} else {
			System.out.println("Registro não encontrado.");
		}

		System.out.println("\n==== TEST 3: update ====");
		if (aux != null) {
			aux.setFinalGrade(8.5);
			transcriptDao.update(aux, studentIdTeste);

			TranscriptRecord updatedRecord = transcriptDao.findById(studentIdTeste, courseTeste.getCode());
			System.out.println("Atualizado para: " + updatedRecord);
		}

		System.out.println("\n==== TEST 4: findAll ====");
		List<TranscriptRecord> list = transcriptDao.findAll();
		if (list.isEmpty()) {
			System.out.println("Nenhum registro encontrado no banco.");
		} else {
			for (TranscriptRecord record : list) {
				System.out.println(record);
			}
		}

		System.out.println("\n==== TEST 5: deleteById ====");
		transcriptDao.deleteById(studentIdTeste, courseTeste.getCode());
		System.out.println("Registro deletado com sucesso!");

		TranscriptRecord deletedCheck = transcriptDao.findById(studentIdTeste, courseTeste.getCode());
		if (deletedCheck == null) {
			System.out.println("Confirmação: o registro não existe mais no banco.");
		}

//				CourseDao courseDao = DaoFactory.createCourseDao();
//
//				System.out.println("==== TEST 1: insert ====");
//				
//
//				Course baseCourse = new Course("COMP11", "Logica de Programacao", 4, new ArrayList<>());
//				courseDao.insert(baseCourse);
//				System.out.println("Curso base inserido: " + baseCourse.getCode());
//
//
//				List<Course> prereqs = new ArrayList<>();
//				prereqs.add(baseCourse);
//				Course advancedCourse = new Course("COMP12"
//						+ "", "Orientacao a Objetos", 4, prereqs);
//				
//				courseDao.insert(advancedCourse);
//				System.out.println("Curso avancado inserido com dependencias: " + advancedCourse.getCode());
//
//				System.out.println("\n==== TEST 2: findById ====");
//
//				Course aux = courseDao.findById(advancedCourse.getCode());
//				if (aux != null) {
//					System.out.println("Encontrado: " + aux.getName());
//					System.out.println("Quantidade de pre-requisitos carregados: " + aux.getPrerequisites().size());
//					for (Course pre : aux.getPrerequisites()) {
//						System.out.println(" -> Exige: " + pre.getName());
//					}
//				} else {
//					System.out.println("Registro nao encontrado.");
//				}
//
//				System.out.println("\n==== TEST 3: update ====");
//				if (aux != null) {
//
//					aux.setCredits(6);
//					courseDao.update(aux);
//
//
//					Course updatedCourse = courseDao.findById(aux.getCode());
//					System.out.println("Atualizado para: " + updatedCourse.getCredits() + " creditos.");
//				}
//
//				System.out.println("\n==== TEST 4: findAll ====");
//				List<Course> list = courseDao.findAll();
//				if (list.isEmpty()) {
//					System.out.println("Nenhum curso encontrado no banco.");
//				} else {
//					for (Course course : list) {
//						System.out.println(course.getCode() + " - " + course.getName());
//					}
//				}
//
//				System.out.println("\n==== TEST 5: deleteById ====");
//
//				courseDao.deleteById(advancedCourse.getCode());
//				courseDao.deleteById(baseCourse.getCode());
//				System.out.println("Cursos deletados com sucesso!");

	}
}
