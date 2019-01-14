package state;

import database.DatabaseEditor;
import model.Word;

import java.util.List;

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
