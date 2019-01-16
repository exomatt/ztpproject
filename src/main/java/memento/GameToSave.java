package memento;

import lombok.Data;
import model.Question;

import java.io.Serializable;
import java.util.List;

/**
 * The type Game to save.
 */
@Data
public class GameToSave implements Serializable {
    private List<Question> questions;
    private int lastQuestionIndex;
    private String difficulty;
    private boolean ifTest;
    private int maxValue;

}
