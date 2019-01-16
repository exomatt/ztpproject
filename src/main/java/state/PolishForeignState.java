package state;

import database.DatabaseEditor;
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
    public List<Word> getWordList(DatabaseEditor databaseEditor) {
        return databaseEditor.findByLanguage("eng");
    }
}
