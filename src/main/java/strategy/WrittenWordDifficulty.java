package strategy;

import database.DatabaseEditor;
import model.Word;
import state.LanguageState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WrittenWordDifficulty implements Difficulty {

    @Override
    public List<Word> getAnswerWords(Word word, DatabaseEditor editor, LanguageState state) {
        return new ArrayList<>(Collections.singleton(word.getTranslation()));
    }
}
