package strategy;

import database.DatabaseEditor;
import model.Word;
import state.LanguageState;

import java.util.List;

public interface Difficulty {
    List<Word> getAnswerWords(Word word, DatabaseEditor editor, LanguageState state);
}
