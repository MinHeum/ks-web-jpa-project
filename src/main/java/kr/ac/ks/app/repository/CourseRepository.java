package kr.ac.ks.app.repository;

import kr.ac.ks.app.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByStudentIdIsLike(final Long id);
    List<Course> findAllByLessonIdIsLike(final Long id);
}
