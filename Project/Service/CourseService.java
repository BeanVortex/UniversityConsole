package Project.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Project.Models.Course;
import Project.Models.Student;

public class CourseService {

    private final List<Course> courses = new ArrayList<>();
    private static Long idCounter = 0l;

    /** 
     * Create Courses
     * Inputs: course name, course id
     */
    public void createCourse(Scanner scan) {
        try {
            Course course = new Course();
            idCounter++;
            System.out.print("Enter course name (-1 to exit): ");
            String name = scan.nextLine();

            try {
                if (Integer.valueOf(name) == -1)
                    return;
            } catch (Exception e) {
            }

            course.setName(name);
            course.setId(idCounter);
            courses.add(course);
            System.out.println("Successfully created course with id of " + course.getId() + " ---------\n");
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Try again ---------\n");
            idCounter--;
            createCourse(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            idCounter--;
            createCourse(scan);
        }
    }

    /**
     * @param List<Course> courses
     * Iterates courses and prints its data 
     * */
    public void readCourses(List<Course> courses) {
        System.out.println("Courses data ---------\n");
        if (courses == null) {
            courses = this.courses;
        }
        // Used Java 8 feature called lambda expression
        courses.forEach(course -> {
            System.out.println("Course id: " + course.getId());
            System.out.println("Course name: " + course.getName());
            System.out.println();
        });
        if (courses.size() == 0)
            System.out.println("No courses added to the university");
        System.out.println("---------\n");

    }

    /** 
     * @param Long id
     * Finds course with its id and updates its data with new inputs
     */
    public void updateCourse(Scanner scan) {

        try {
            System.out.print("Enter course id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1)
                return;
            // Used lambda expression to filter and find a course with given id
            List<Course> filteredCourses = courses.stream().filter(course -> {
                return course.getId() == id;
            }).collect(Collectors.toList());

            Course course = null;
            // Checks if List of filtered courses is not empty
            if (filteredCourses.size() == 0)
                throw new Exception("Course with id of " + id + " does not exists.");
            else
                course = filteredCourses.get(0);

            System.out.print("Enter new name for Course: ");
            course.setName(scan.nextLine());

            // finding course index in the list using indexOf() method
            Integer index = courses.indexOf(course);
            courses.set(index, course);
            System.out.println("Successfully updated course with id of " + id + " ---------\n");
        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Try again ---------\n");
            scan.nextLine();
            updateCourse(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            updateCourse(scan);
        }

    }

    /** 
     * @param Long id
     * Takes an id and find course with that id and deletes it
     */
    public void deleteCourse(Scanner scan) {
        try {
            System.out.print("Enter course id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1)
                return;
            // Used lambda expression to filter and find a course with given id
            List<Course> filteredCourses = courses.stream().filter(course -> {
                return course.getId() == id;
            }).collect(Collectors.toList());

            Course course = null;
            // Checks if List of filtered courses is not empty
            if (filteredCourses.size() == 0)
                throw new Exception("Course with id of " + id + " does not exists.");
            else
                course = filteredCourses.get(0);

            // Removes course. if returns true that means it was successful
            if (courses.remove(course))
                System.out.println("Successfully deleted course with id of " + id + " ---------\n");
            else
                throw new Exception("Something went wrong.\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Try again ---------\n");
            scan.nextLine();
            deleteCourse(scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            deleteCourse(scan);
        }
    }

    /** 
     * Search in courses with 2 options: 1. Name, 2. Id
     */
    public void searchInCourses(Scanner scan) {
        try {
            System.out.println("Search by: (enter option)");
            System.out.println("1) Name\n2) Id\n-1) Exit");
            // external private method to avoid extending this method
            List<Course> foundCourses = findCourses(scan);
            if (foundCourses == null)
                return;
            System.out.println("Founded courses data: ---------\n");
            // Printing foundedCourses data with lambda 
            foundCourses.forEach(course -> {
                System.out.println("Course id: " + course.getId());
                System.out.println("Course name: " + course.getName());
                System.out.println();
            });
            System.out.println("---------\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for option. Enter integer value. Try again ---------\n");
            scan.nextLine();
            searchInCourses(scan);
        }
    }

    /**
     * @param Scanner scan
     */
    private List<Course> findCourses(Scanner scan) throws InputMismatchException {

        final List<Course> searchCourses = new ArrayList<>();
        // getting user input. if wrong input entered, throws InputMismatchException and handles it in searchInCourses() method
        Integer option = scan.nextInt();
        scan.nextLine();
        switch (option) {
        case 1:
            System.out.print("Enter course name: ");
            String name = scan.nextLine();
            courses.forEach(course -> {
                // adds courses which contain name or is equal to name
                if (course.getName().equals(name) || course.getName().contains(name)) {
                    searchCourses.add(course);
                }
            });
            break;
        case 2:
            System.out.print("Enter course id: ");
            Long id = scan.nextLong();
            scan.nextLine();
            courses.forEach(course -> {
                // returns courses which id is equal course id
                if (course.getId() == id) {
                    searchCourses.add(course);
                }
            });
            break;

        case -1:
            return null;

        default:
            System.err.println("There is no +" + option + " option. Try entering option again  ---------\n");
            return findCourses(scan);
        }

        return searchCourses;
    }

    /** 
     * @param Student student
     * registers a course to student
     */
    public void registerCourse(Student student, Scanner scan) {
        try {
            // reads courses to show student's options
            readCourses(null);
            System.out.print("Which course to register? Select by id (-1 to exit): ");
            // if not matchable input entered, throws InputMismatchException and will handle it
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1L)
                return;

            // Filters courses and finds courses which id is equal to course id and is not borrowed
            List<Course> filteredCourses = courses.stream().filter(course -> {
                return (course.getId() == id);
            }).collect(Collectors.toList());

            // Checks if List of filtered courses is not empty
            if (filteredCourses.size() == 0) {
                throw new Exception("Course with id of " + id + " does not exists in the university.");
            }

            Course course = filteredCourses.get(0);
            student.getCourses().add(course);
            System.out.println(
                    "Successfully course with id of " + id + " registered by " + course.getName() + " ---------\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Enter long value. Try again ---------\n");
            scan.nextLine();
            registerCourse(student, scan);
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            registerCourse(student, scan);
        }

    }

    /** 
    * @param Student student
    * deletes a course for a student
    */
    public void deleteCourseForStudent(Student student, Scanner scan) {

        try {
            // Checks if student has registered any course before
            if (student.getCourses() == null || student.getCourses().size() == 0) {
                throw new IndexOutOfBoundsException("This student has no courses registered!!");
            }
            // Reads students registered courses 
            readCourses(student.getCourses());

            System.out.print("Which course to delete? Select by id (-1 to exit): ");
            Long id = scan.nextLong();
            scan.nextLine();
            if (id == -1L)
                return;
            // Finds courses if id is equal to course id
            List<Course> filteredCourses = courses.stream().filter(course -> {
                return (course.getId() == id);
            }).collect(Collectors.toList());

            // Checks if List of filtered courses is not empty
            if (filteredCourses.size() == 0) {
                throw new Exception("Course with id of " + id + " does not exists in the university.");
            }

            Course course = filteredCourses.get(0);

            student.getCourses().remove(course);
            System.out.println(
                    "Successfully course with id of " + id + " removed for " + student.getName() + " ---------\n");

        } catch (InputMismatchException e) {
            System.err.println("You have entered wrong value for id. Enter long value. Try again ---------\n");
            scan.nextLine();
            deleteCourseForStudent(student, scan);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage() + " returning main menu ---------\n");
        } catch (Exception e) {
            System.err.println(e.getMessage() + " Try again ---------\n");
            deleteCourseForStudent(student, scan);
        }

    }

    public List<Course> getCourses() {
        return this.courses;
    }

}