package model.services;

import java.util.List;

import model.dao.CourseDao;
import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.dao.TranscriptRecordDao;
import model.entities.ApprovalStatus;
import model.entities.Course;
import model.entities.Student;
import model.entities.TranscriptRecord;

public class EnrollmentService {
    
    private StudentDao studentDao;
    private CourseDao courseDao;
    private TranscriptRecordDao transcriptRecordDao;
    
    public EnrollmentService() {
        studentDao = DaoFactory.createStudentDao();
        courseDao = DaoFactory.createCourseDao();
        transcriptRecordDao = DaoFactory.createTranscriptRecordDao();
    }
    
    public void enrollStudent(String studentId, String courseCode) {
        Student student = studentDao.findById(studentId);
        Course course = courseDao.findById(courseCode);
        
        if (student != null && course != null) {
            TranscriptRecord transcript = transcriptRecordDao.findById(studentId, courseCode);
            
            if (transcript == null || (transcript.getApprovalStatus() != ApprovalStatus.IN_PROGRESS && transcript.getApprovalStatus() != ApprovalStatus.APPROVED)) {
                
                if (course.getPrerequisites() != null && !course.getPrerequisites().isEmpty()) {
                    int totalPrerequisites = course.getPrerequisites().size();
                    int countPrerequisites = 0;
                    List<TranscriptRecord> transcriptList = transcriptRecordDao.findByStudent(studentId);
                    
                    for (Course prerequisite : course.getPrerequisites()) {
                        for (TranscriptRecord transcriptItem : transcriptList) {
                            if (prerequisite.getCode().equals(transcriptItem.getCourse().getCode()) 
                                    && transcriptItem.getApprovalStatus() == ApprovalStatus.APPROVED) {
                                countPrerequisites++;
                                break;
                            }
                        }
                    }
                    
                    if (countPrerequisites < totalPrerequisites) {
                        throw new ServiceException("Student doesn't have the required pre-requisites.");
                    }
                } 
                
                // Cria e salva o registro
                TranscriptRecord record = new TranscriptRecord(course, null, ApprovalStatus.IN_PROGRESS);
                transcriptRecordDao.insert(record, studentId);
                
            } else {
                throw new ServiceException("Student is already in this course or has already succeeded.");
            }
        } else {
            throw new ServiceException("Student ID or course code is not valid.");
        }
    }
    
    public void withdrawStudent(String studentId, String courseCode) {
        TranscriptRecord transcript = transcriptRecordDao.findById(studentId, courseCode);
        
        if (transcript != null) {
            if (transcript.getApprovalStatus() == ApprovalStatus.IN_PROGRESS) {
                transcript.setApprovalStatus(ApprovalStatus.WITHDRAWN);
                transcriptRecordDao.update(transcript, studentId);
            } else {
                throw new ServiceException("Course is not in progress, cannot withdraw.");
            }
        } else {
            throw new ServiceException("Enrollment record not found.");
        }
    }
}