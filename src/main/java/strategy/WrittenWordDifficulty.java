package strategy;

import database.DataBaseEditorTarget;
import model.Word;
import state.LanguageState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Written word difficulty.
 */
public class WrittenWordDifficulty implements Difficulty {

    @Override
    public List<Word> getAnswerWords(Word word, DataBaseEditorTarget editor, LanguageState state) {
        return new ArrayList<>(Collections.singleton(word.getTranslation()));
    }
}
