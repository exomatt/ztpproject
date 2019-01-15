package model;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private Word wordToTranslate;
    private List<Word> anwswers;
    private Word pickByUser;
}
