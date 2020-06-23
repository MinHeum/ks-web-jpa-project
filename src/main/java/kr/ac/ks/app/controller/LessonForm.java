package kr.ac.ks.app.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LessonForm {
    private Long id;
    @NotEmpty(message = "이름은 필수입니다.")
    private String name;
    private int quota;
}
