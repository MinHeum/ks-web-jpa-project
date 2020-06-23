package kr.ac.ks.app.controller;

import kr.ac.ks.app.domain.Course;
import kr.ac.ks.app.domain.Lesson;
import kr.ac.ks.app.domain.Student;
import kr.ac.ks.app.repository.CourseRepository;
import kr.ac.ks.app.repository.LessonRepository;
import kr.ac.ks.app.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class CourseController {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public CourseController(StudentRepository studentRepository, CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    @GetMapping("/course")
    public String showCourseForm(Model model) {
        List<Student> students = studentRepository.findAll();
        List<Lesson> lessons = lessonRepository.findAll();
        model.addAttribute("students", students);
        model.addAttribute("lessons", lessons);
        return "courses/courseForm";
    }

    @PostMapping("/course")
    public String createCourse(@RequestParam("studentId") Long studentId,
                               @RequestParam("lessonId") Long lessonId
                               ) {
        Student student = studentRepository.findById(studentId).get();
        Lesson lesson = lessonRepository.findById(lessonId).get();
        Course course = Course.createCourse(student,lesson);
        Course savedCourse = courseRepository.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses")
    public String courseList(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses/courseList";
    }

    @GetMapping("/courses/{id}")
    public String showUpdateStudent(@PathVariable("id") Long id, Model model) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new  IllegalArgumentException("Invalid Course Id:" + id));
        List<Student> students = studentRepository.findAll();
        List<Lesson> lessons = lessonRepository.findAll();

        model.addAttribute("students", students);
        model.addAttribute("lessons", lessons);
        model.addAttribute("studentId", course.getId());
        model.addAttribute("lessonId", course.getLesson().getId());
        return "courses/courseForm";
    }

    @PostMapping("/courses/{id}")
    public String updateCourse(@Valid Course course, BindingResult result, @RequestParam Long studentId, @RequestParam Long lessonId) {
        if (result.hasErrors() ) {
            return "courses/courseForm";
        }

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + studentId));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new  IllegalArgumentException("Invalid Lesson Id:" + lessonId));

        course.setStudent(student);
        course.setLesson(lesson);

        courseRepository.save(course);
        return "redirect:/courses";
    }

    @GetMapping("courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, Model model) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        
        //Todo: 값이 지워졌을 때 lesson의 Quata 값 하나 증가시키기



        //
        courseRepository.delete(course);
        model.addAttribute("courses", courseRepository.findAll());
        return "redirect:/courses";
    }

}
