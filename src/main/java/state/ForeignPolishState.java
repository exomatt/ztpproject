package state;

import database.DatabaseEditor;
import model.Word;

import java.util.List;

/**
 * The type Foreign polish state.
 */
public class ForeignPolishState extends LanguageState {

    @Override
    public String getLanguage() {
        return "eng";
    }

    @Override
    public List<Word> getWordList(DatabaseEditor databaseEditor) {
        return databaseEditor.findByLanguage("pl");
    }
}
