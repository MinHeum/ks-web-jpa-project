package kr.ac.ks.app.controller;

import kr.ac.ks.app.domain.Course;
import kr.ac.ks.app.domain.Student;
import kr.ac.ks.app.repository.CourseRepository;
import kr.ac.ks.app.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentController(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/students/new")
    public String showStudentForm(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        return "students/studentForm";
    }

    @PostMapping("/students/new")
    public String createStudent(@Valid StudentForm studentForm, BindingResult result) {
        if (result.hasErrors()) {
            return "students/studentForm";
        }
        Student student = new Student();
        student.setName(studentForm.getName());
        student.setEmail(studentForm.getEmail());
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String list(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students/studentList";
    }

    @GetMapping("/students/{id}")
    public String showUpdateStudent(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new  IllegalArgumentException("Invalid Student Id:" + id));
        StudentForm studentForm = new StudentForm();
        studentForm.setName(student.getName());
        studentForm.setEmail(student.getEmail());
        model.addAttribute("studentForm", studentForm);
        return "students/studentForm";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@Valid Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "students/studentForm";
        }
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        List<Course> courseList = courseRepository.findAllByStudentIdIsLike(id);
        for (Course c: courseList
        ) {
            c.getLesson().setQuota(c.getLesson().getQuota()+1);
            courseRepository.delete(c);
        }
        studentRepository.delete(student);
        model.addAttribute("students", studentRepository.findAll());
        return "redirect:/";
    }

}
