package state;

import database.DatabaseEditor;
import model.Word;

import java.util.List;

public class ForeignPolishState extends LanguageState {
    @Override
    List<Word> getWordList(DatabaseEditor databaseEditor) {
        return databaseEditor.findByLanguage("eng");
    }
}
