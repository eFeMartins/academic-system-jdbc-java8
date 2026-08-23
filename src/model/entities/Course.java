package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
	private String code;
	private String name;
	private int credits;
	
	private List<Course> prerequisites = new ArrayList<>();
	
	public Course() {
		
	}

	public Course(String code, String name, int credits, List<Course> prerequisites) {
		super();
		this.code = code;
		this.name = name;
		this.credits = credits;
		this.prerequisites = prerequisites;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public List<Course> getPrerequisites() {
		return prerequisites;
	}
	
	public void setPrerequisites(List<Course> prerequisites) {
		this.prerequisites = prerequisites;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "Course [code=" + code + ", name=" + name + ", credits=" + credits + ", prerequisites=" + prerequisites
				+ "]";
	}

	
	
}
