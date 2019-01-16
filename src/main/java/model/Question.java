package model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Question implements Serializable {
    private Word wordToTranslate;
    private List<Word> anwswers;
    private String pickByUser;
}
