package bg.nbu.gradebook.services.classes;

public interface ClassService {
    void enrollStudentInClass(long studentId, long classId);

    void unenrollStudentFromClass(long studentId, long classId);
}
