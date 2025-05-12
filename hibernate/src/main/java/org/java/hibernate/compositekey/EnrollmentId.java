package org.java.hibernate.compositekey;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EnrollmentId implements Serializable {

	private Long studentId;
	private Long courseId;

	public EnrollmentId() {
	}

	public EnrollmentId(Long studentId, Long courseId) {
		this.studentId = studentId;
		this.courseId = courseId;
	}

	// Getters and Setters
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	// equals and hashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof EnrollmentId)) return false;
		EnrollmentId that = (EnrollmentId) o;
		return Objects.equals(studentId, that.studentId) &&
				Objects.equals(courseId, that.courseId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentId, courseId);
	}
}
