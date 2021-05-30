package Project.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Project.Models.Student;

public class StudentService {

    private final List<Student> students = new ArrayList<>();
    private static Long idCounter = 0l;

    /** 
    * Create Students
    * Inputs: name, age, gender
    */
    public void createStudent(Scanner scan) {
        try {
            Student student = new Student();
            idCounter++;

            System.out.print("Enter student name: ");
            student.setName(scan.nextLine());

            System.err.print("Enter student gender: ");
            char gender = scan.nextLine().charAt(0);
            // checks gender exists or not
            switch (gender) {
            case 'm':
            case 'f':
            case 'M':
            case 'F':
                break;
            default:
                throw new Exception("The gender " + gender + " does not exist");

            }
            student.setGender(gender);

            System.out.print("Enter student age (-1 to exit): ");
            Integer age = scan.nextInt();
            if (age == -1)
                return;
            scan.nextLine();
            student.setAge(age);
            student.setMembershipId(idCounter);

            // Registers student to the university
            students.add(student);

            System.out.println(
                    "Successfully created student with membership id of " + student.getMembershipId() + " ---------\n");
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for membership or age. Try again ---------\n");
            scan.nextLine();
            idCounter--;
            createStudent(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            idCounter--;
            createStudent(scan);
        }
    }

    /**
    * @param List<Student> students
    * Iterates students and prints its data 
    * */
    public void readStudent(List<Student> students) {
        System.out.println("Students data ---------\n");
        if (students == null) {
            students = this.students;
        }
        // Used Java 8 feature called lambda expression
        students.forEach(student -> {
            System.out.println("Student name: " + student.getName());
            System.out.println("Student gender: " + student.getGender());
            System.out.println("Student age: " + student.getAge());
            System.out.println("Student membership id: " + student.getMembershipId());
            System.out.println("Registered courses: ");
            // Checks if student has any course registered or not, if yes prints it
            if (student.getCourses().size() == 0)
                System.out.println("Student has not registered to any courses!!!");
            else {
                student.getCourses().forEach(course -> {
                    System.out.println("Course name: " + course.getName());
                    System.out.println("Course id: " + course.getId());
                });
            }
            System.out.println();
        });
        if (students.size() == 0)
            System.out.println("No students registered to the university");
        System.out.println("---------\n");
    }

    /** 
    * @param Long id
    * Finds student with its membership id and updates its data with new inputs
    */
    public void updateStudent(Scanner scan) {

        try {
            System.out.print("Enter student membership id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1)
                return;

            // Used lambda expression to filter and find a student with given id
            List<Student> filteredStudents = students.stream().filter(student -> {
                return student.getMembershipId() == id;
            }).collect(Collectors.toList());

            Student student = null;
            // Checks if List of filtered students is not empty
            if (filteredStudents.size() == 0)
                throw new Exception("Student with membership id of " + id + " does not exists.");
            else
                student = filteredStudents.get(0);

            System.out.print("Enter new name for student: ");
            student.setName(scan.nextLine());
            System.out.print("Enter new age for student: ");
            Integer age = scan.nextInt();
            scan.nextLine();
            student.setAge(age);
            // finding course index in the list using indexOf() method
            Integer index = students.indexOf(student);
            students.set(index, student);
            System.out.println("Successfully updated student with membership id of " + id + "---------\n");
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for age. Try again ---------\n");
            scan.nextLine();
            updateStudent(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            updateStudent(scan);
        }

    }

    /** 
    * @param Long id
    * Takes an membership id and find student with that id and deletes it
    */
    public void deleteStudent(Scanner scan) {
        try {
            System.out.print("Enter student membership id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1)
                return;

            // Used lambda expression to filter and find a student with given id
            List<Student> filteredStudents = students.stream().filter(student -> {
                return student.getMembershipId() == id;
            }).collect(Collectors.toList());

            Student student = null;
            // Checks if List of filtered students is not empty
            if (filteredStudents.size() == 0)
                throw new Exception("Student with id of " + id + " does not exists.");
            else
                student = filteredStudents.get(0);

            // Removes student. if returns true that means it was successful
            if (students.remove(student))
                System.out.println("Successfully deleted student with membership id of " + id + " ---------\n");
            else
                throw new Exception("Something went wrong.\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for age. Try again ---------\n");
            scan.nextLine();
            deleteStudent(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            deleteStudent(scan);
        }
    }

    /** 
    * Search in students with 3 options: 1. Name 2. Gender Male 3. Gender Female
    */
    public void searchInStudents(Scanner scan) {
        try {
            System.out.println("Search by: (enter option)");
            System.out.println("1) Name\n2) Male gender\n3) Female gender\n-1) Exit");
            // external private method to avoid extending this method
            List<Student> foundStudents = findStudents(scan);
            if (foundStudents == null)
                return;
            System.out.println("Founded students data ---------\n");
            // Printing foundStudents data with lambda 
            foundStudents.forEach(student -> {
                System.out.println("Student name: " + student.getName());
                System.out.println("Student gender: " + student.getGender());
                System.out.println("Student age: " + student.getAge());
                System.out.println("Student membership id: " + student.getMembershipId());
                System.out.println("Registered courses: ");
                // Checks if student has any course registered or not, if yes prints it
                if (student.getCourses().size() == 0)
                    System.out.println("Student has not registered to any course!!!");
                else {
                    student.getCourses().forEach(course -> {
                        System.out.println("Course id: " + course.getId());
                        System.out.println("Course name: " + course.getName());
                    });
                }
                System.out.println();
            });
            System.out.println("---------\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for option. Enter integer value. Try again ---------\n");
            scan.nextLine();
            searchInStudents(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            searchInStudents(scan);
        }
    }

    /**
     * @param Scanner scan
     */
    private List<Student> findStudents(Scanner scan) throws InputMismatchException {

        final List<Student> searchStudents = new ArrayList<>();
        // getting user input. if wrong input entered, throws InputMismatchException and handles it in searchInStudents() method
        Integer option = scan.nextInt();
        scan.nextLine();
        switch (option) {
        case 1:
            System.out.print("Enter student's name: ");
            String name = scan.nextLine();
            students.forEach(student -> {
                // adds students which contain name or is equal to name
                if (student.getName().equals(name) || student.getName().contains(name)) {
                    searchStudents.add(student);
                }
            });
            break;
        case 2:
            students.forEach(student -> {
                // adds students which gender is equal to male
                if (student.getGender() == 'm' || student.getGender() == 'M') {
                    searchStudents.add(student);
                }
            });
            break;
        case 3:
            students.forEach(student -> {
                // adds student which gender is equal to female
                if (student.getGender() == 'f' || student.getGender() == 'F') {
                    searchStudents.add(student);
                }
            });
            break;
        case -1:
            return null;
        default:
            System.err.println("There is no +" + option + " option. Try entering option again  ---------\n");
            return findStudents(scan);
        }

        return searchStudents;
    }

    public List<Student> getStudents() {
        return this.students;
    }

}
