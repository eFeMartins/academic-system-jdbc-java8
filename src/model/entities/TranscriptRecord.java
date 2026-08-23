package model.entities;

import java.util.Objects;

public class TranscriptRecord {
	private Course course;
	private Double finalGrade;
	private ApprovalStatus approvalStatus;
	
	public TranscriptRecord() {
		
	}
	public TranscriptRecord(Course course, Double finalGrade, ApprovalStatus approvalStatus) {
		super();
		this.course = course;
		this.finalGrade = finalGrade;
		this.approvalStatus = approvalStatus;
	}
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public Double getFinalGrade() {
		return finalGrade;
	}
	public void setFinalGrade(Double finalGrade) {
		this.finalGrade = finalGrade;
	}
	
	public ApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(ApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(course);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TranscriptRecord other = (TranscriptRecord) obj;
		return Objects.equals(course, other.course);
	}
	@Override
	public String toString() {
		return "TranscriptRecord [course=" + course + ", finalGrade=" + finalGrade + ", approvalStatus="
				+ approvalStatus + "]";
	}
	
	
	
}
