package model.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Student {
	private String student_id;
	private String name;
	private String major;
	private LocalDate enrollmentDate;
	
	private List<TranscriptRecord> transcript;
	
	public Student() {
		
	}

	public Student(String student_id, String name, String major, LocalDate enrollmentDate,
			List<TranscriptRecord> transcript) {
		this.student_id = student_id;
		this.name = name;
		this.major = major;
		this.enrollmentDate = enrollmentDate;
		this.transcript = transcript;
	}

	public String getStudent_id() {
		return student_id;
	}
	
	public void setStudent_id(String student_id){
		this.student_id = student_id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}

	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}
	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public List<TranscriptRecord> getTranscript() {
		return transcript;
	}
	
	public void setTranscript(List<TranscriptRecord> transcript) {
		this.transcript = transcript;
	}

	@Override
	public int hashCode() {
		return Objects.hash(student_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(student_id, other.student_id);
	}

	@Override
	public String toString() {
		return "Student [student_id=" + student_id + ", name=" + name + ", major=" + major + ", enrollmentDate="
				+ enrollmentDate + ", transcript=" + transcript + "]";
	}
	
	
	
	
	
}
