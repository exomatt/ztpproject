package strategy;

import database.DataBaseEditorTarget;
import model.Word;
import state.LanguageState;

import java.util.List;

/**
 * The interface Difficulty.
 */
public interface Difficulty {
    /**
     * Gets answer words.
     *
     * @param word   the word
     * @param editor the editor
     * @param state  the state
     * @return the answer words
     */
    List<Word> getAnswerWords(Word word, DataBaseEditorTarget editor, LanguageState state);
}
