package application;

import java.util.ArrayList;
import java.util.Arrays;

import model.entities.Course;
import model.services.AcademicService;
import model.services.EnrollmentService;

public class Program {
	public static void main(String[] args) {

		AcademicService academicService = new AcademicService();
		EnrollmentService enrollmentService = new EnrollmentService();

		String studentId = "1240110875";

		System.out.println("==== TEST 1: registerNewCourse ====");
		try {
			Course c1 = new Course("COMP10", "Operating Systems", 4, new ArrayList<>());
			academicService.registerNewCourse(c1, new ArrayList<>());
			System.out.println("Success: Base course (COMP10) registered.");

			Course c2 = new Course("COMP11", "Distributed Systems", 4, new ArrayList<>());
			academicService.registerNewCourse(c2, Arrays.asList("COMP10"));
			System.out.println("Success: Advanced course (COMP11) registered with dependency.");
		} catch (RuntimeException e) {
			System.out.println("Failure (Business/DB): " + e.getMessage());
		}

		System.out.println("\n==== TEST 2: Prerequisite Rule (enrollStudent) ====");
		try {
			System.out.println("Attempting to enroll in COMP11 (Advanced) without passing COMP10...");
			enrollmentService.enrollStudent(studentId, "COMP11");
			System.out.println("WARNING: Enrollment succeeded, but it should have been blocked!");
		} catch (RuntimeException e) {
			System.out.println("Functional Block OK: " + e.getMessage());
		}

		System.out.println("\n==== TEST 3: Enrollment and Grade Recording ====");
		try {
			System.out.println("Enrolling in COMP10 (Base)...");
			enrollmentService.enrollStudent(studentId, "COMP10");
			System.out.println("Success: Enrolled in COMP10 (Status: IN_PROGRESS).");

			System.out.println("Recording final grade 8.5 in COMP10...");
			academicService.recordFinalGrade(studentId, "COMP10", 8.5);
			System.out.println("Success: Grade recorded and status updated to APPROVED.");
		} catch (RuntimeException e) {
			System.out.println("Failure: " + e.getMessage());
		}

		System.out.println("\n==== TEST 4: Enrollment Cleared after Prerequisite ====");
		try {
			System.out.println("Attempting to enroll in COMP11 again (now has prerequisite)...");
			enrollmentService.enrollStudent(studentId, "COMP11");
			System.out.println("Success: Enrollment cleared! System recognized approval in COMP10.");
		} catch (RuntimeException e) {
			System.out.println("Failure: " + e.getMessage());
		}

		System.out.println("\n==== TEST 5: Course Withdrawal (withdrawStudent) ====");
		try {
			System.out.println("Withdrawing from COMP11 which is currently in progress...");
			enrollmentService.withdrawStudent(studentId, "COMP11");
			System.out.println("Success: Course withdrawn with status WITHDRAWN.");
		} catch (RuntimeException e) {
			System.out.println("Failure: " + e.getMessage());
		}


	}
}
