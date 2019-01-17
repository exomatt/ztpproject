package state;

import database.DataBaseEditorTarget;
import model.Word;

import java.util.List;

/**
 * The type Polish foreign state.
 */
public class PolishForeignState implements LanguageState {
    @Override
    public String getLanguage() {
        return "pl";
    }

    @Override
    public List<Word> getWordList(DataBaseEditorTarget dataBaseEditorTarget) {
        return dataBaseEditorTarget.findByLanguage("eng");
    }
}
