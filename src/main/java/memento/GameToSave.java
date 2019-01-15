package memento;

import lombok.Data;
import model.Word;
import state.LanguageState;
import strategy.Difficulty;

import java.util.List;

/**
 * The type Game to save.
 */
@Data
public class GameToSave {
    private List<Word> questions;
    private Difficulty difficulty;
    private LanguageState state;

}
