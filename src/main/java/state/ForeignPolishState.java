package state;

import database.DataBaseEditorTarget;
import model.Word;

import java.util.List;

/**
 * The type Foreign polish state.
 */
public class ForeignPolishState implements LanguageState {

    @Override
    public String getLanguage() {
        return "eng";
    }

    @Override
    public List<Word> getWordList(DataBaseEditorTarget dataBaseEditorTarget) {
        return dataBaseEditorTarget.findByLanguage("pl");
    }
}
