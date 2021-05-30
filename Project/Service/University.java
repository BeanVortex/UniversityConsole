package Project.Service;

public class University {

    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();

    public StudentService getStudentService() {
        return this.studentService;
    }

    public CourseService getCourseService() {
        return this.courseService;
    }

}
