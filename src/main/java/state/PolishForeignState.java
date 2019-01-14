package state;

import database.DatabaseEditor;
import model.Word;

import java.util.List;

public class PolishForeignState extends LanguageState {
    @Override
    public String getLanguage() {
        return "pl";
    }

    @Override
    public List<Word> getWordList(DatabaseEditor databaseEditor) {
        return databaseEditor.findByLanguage("eng");
    }
}
