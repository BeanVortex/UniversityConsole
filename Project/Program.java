package Project;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Project.Models.Student;
import Project.Service.University;

public class Program {

    public static void main(String[] args) {
        boolean flag = true;
        Scanner scan = new Scanner(System.in);
        University uni = new University();
        do {
            //using flag to control program flow
            flag = menu(scan, uni);
        } while (flag);
        scan.close();

    }

    /** 
     * @param Scanner scan 
     * @param University uni
     * Prints main menu
     */
    public static boolean menu(Scanner scan, University uni) {
        try {
            System.out.println("Welcome to the university\n\nMenu:");
            System.out.println("1) Add Course");
            System.out.println("2) Show Courses");
            System.out.println("3) Update Courses");
            System.out.println("4) Delete Courses");
            System.out.println("5) Search in Courses\n");

            System.out.println("6) Add Student");
            System.out.println("7) Show Students");
            System.out.println("8) Update Student");
            System.out.println("9) Delete Student");
            System.out.println("10) Search in Students");

            System.out.println("11) Register a course for a student ");
            System.out.println("12) Delete a course for a student ");

            System.out.println("13) Exit \n");
            System.out.print("Choose one of the options above : ");

            int option = scan.nextInt();
            scan.nextLine();
            switch (option) {
            case 1:
                uni.getCourseService().createCourse(scan);
                return true;

            case 2:
                uni.getCourseService().readCourses(null);
                return true;

            case 3:
                uni.getCourseService().updateCourse(scan);
                return true;

            case 4:
                uni.getCourseService().deleteCourse(scan);
                return true;

            case 5:
                uni.getCourseService().searchInCourses(scan);
                return true;

            case 6:
                uni.getStudentService().createStudent(scan);
                return true;

            case 7:
                uni.getStudentService().readStudent(null);
                return true;

            case 8:
                uni.getStudentService().updateStudent(scan);
                return true;

            case 9:
                uni.getStudentService().deleteStudent(scan);
                return true;

            case 10:
                uni.getStudentService().searchInStudents(scan);
                return true;

            case 11:
                initCourseForStudent(uni, scan, "register");
                return true;

            case 12:
                initCourseForStudent(uni, scan, "delete");
                return true;

            case 13:
                return false;

            default:
                throw new Exception("Enter options as listed");
            }
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value: " + e.getMessage()
                    + " Enter integer value. Try running program again ---------");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something went wrong: " + e.getMessage() + " Try running program again ---------");
            return false;
        }
    }

    /**
     * @param University uni
     * @param Scanner scan 
     * @param String register
     * Register and delete implementation
     */
    private static void initCourseForStudent(University uni, Scanner scan, String register) {
        try {

            System.out.print("\nEnter membership id: ");
            Long membershipId = scan.nextLong();
            scan.nextLine();
            List<Student> students = uni.getStudentService().getStudents().stream().filter(student -> {
                return student.getMembershipId() == membershipId;
            }).collect(Collectors.toList());

            if (students.size() == 0) {
                throw new Exception("Student with id of " + membershipId + " does not exists in the university.");
            }

            if (register.equals("register"))
                uni.getCourseService().registerCourse(students.get(0), scan);
            else
                uni.getCourseService().deleteCourseForStudent(students.get(0), scan);
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Enter long value. Try again ---------\n");
            scan.nextLine();
            initCourseForStudent(uni, scan, register);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            initCourseForStudent(uni, scan, register);
        }
    }

}
