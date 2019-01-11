package state;

import database.DatabaseEditor;
import model.Word;

import java.util.List;

public class PolishForeignState extends LanguageState {
    @Override
    List<Word> getWordList(DatabaseEditor databaseEditor) {
        return databaseEditor.findByLanguage("pl");
    }
}
