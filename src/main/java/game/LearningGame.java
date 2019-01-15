package game;

import Builder.RaportBuilder;
import iterator.AlphabetIterator;
import iterator.RandomIterator;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Word;
import state.LanguageState;
import strategy.Difficulty;

import java.util.Iterator;
import java.util.List;

/**
 * The type Learning game.
 */
@NoArgsConstructor
@Data
public class LearningGame {
    private List<Word> questions;
    private Difficulty gameDifficulty;
    private LanguageState languageState;
    private RaportBuilder raportBuilder;
    private Iterator<Word> iterator;

    /**
     * Create iterator.
     *
     * @param mode   the mode ->  true value if random is on
     * @param number the number
     */
    public void createIterator(boolean mode, int number) {
        if (mode) {
            iterator = new RandomIterator(questions, number);
        } else {
            iterator = new AlphabetIterator(questions, number);
        }
    }


}
