package model.services;

import java.util.List;

import model.dao.CourseDao;
import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.dao.TranscriptRecordDao;
import model.entities.ApprovalStatus;
import model.entities.Course;
import model.entities.TranscriptRecord;

public class AcademicService {

    private StudentDao studentDao;
    private TranscriptRecordDao transcriptDao;
    private CourseDao courseDao;
    
    public AcademicService() {
        studentDao = DaoFactory.createStudentDao();
        transcriptDao = DaoFactory.createTranscriptRecordDao();
        courseDao = DaoFactory.createCourseDao();
    }

    public void recordFinalGrade(String studentId, String courseCode, double finalGrade) {
        if (finalGrade >= 0.0 && finalGrade <= 10.0) {
            TranscriptRecord transcript = transcriptDao.findById(studentId, courseCode);
            if (transcript != null) {
                if (transcript.getApprovalStatus() == ApprovalStatus.IN_PROGRESS) {
                    transcript.setFinalGrade(finalGrade);
                    if (finalGrade >= 6.0) {
                        transcript.setApprovalStatus(ApprovalStatus.APPROVED);
                    } else {
                        transcript.setApprovalStatus(ApprovalStatus.FAILED);
                    }
                    transcriptDao.update(transcript, studentId);
                } else {
                    throw new ServiceException("Cannot record grade: course is not in progress.");
                }
            } else {
                throw new ServiceException("Enrollment record not found for the given student and course.");
            }
        } else {
            throw new ServiceException("Grade must be between 0.0 and 10.0.");
        }
    }
    
    public void registerNewCourse(Course newCourse, List<String> prerequisiteCodes) {
        if (newCourse.getName() != null && newCourse.getCode() != null && newCourse.getCredits() > 0) {
            if (courseDao.findById(newCourse.getCode()) == null) {
                
                if (prerequisiteCodes != null) {
                    if (newCourse.getPrerequisites() == null) {
                        newCourse.setPrerequisites(new java.util.ArrayList<>());
                    }
                    
                    for (String prerequisite : prerequisiteCodes) {
                        if (!prerequisite.equals(newCourse.getCode())) {
                            Course preReqCourse = courseDao.findById(prerequisite);                            
                            if (preReqCourse == null) {
                                throw new ServiceException("Prerequisite course not found: " + prerequisite);
                            }
                            newCourse.getPrerequisites().add(preReqCourse);
                            
                        } else {
                            throw new ServiceException("A course cannot be a prerequisite for itself.");
                        }
                    }
                }
                courseDao.insert(newCourse);
                
            } else {
                throw new ServiceException("Course already exists.");
            }
        } else {
            throw new ServiceException("Course has invalid fields.");
        }
    }
}