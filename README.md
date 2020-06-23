## 웹프로그래밍 응용 과제

#### 수정 내역

1. controller/StudentController  
    1. Student 수정을 위한 showUpdateStudent, updateStudent
    2. Student 삭제를 위한 deleteStudent
2. controller/LessonController  
    1. Lesson 수정을 위한 showUpdateLesson, updateLesson
    2. Lesson 삭제를 위한 deleteLesson
3. controller/CourseController  
    1. Course 수정을 위한 showUpdateCourse, updateCourse
    2. Course 삭제를 위한 deleteCourse
4. domain/Student 와 domain/Lesson
    1. OneToMany 어노테이션에 mappedBy 추가.  
5. resources/templates/students/studentList.html, lessons/lessonList.html, courses/courseList.html 
    - 수정과 삭제를 위해 버튼에 URL 추가.  
    
#### 06/23 추가

1. 수강인원이 0보다 같거나 작을 경우 Course를 생성하지 않고 courseError 페이지를 출력하도록 변경.
2. Course에 등록된 Student를 삭제할 때, 오류를 발생시키지 않고 연결된 Courses를 삭제하도록 변경.
3. Course에 등록된 Lesson을 삭제할 때, 오류를 발생시키지 않고 연결된 Courses를 삭제하도록 변경.

