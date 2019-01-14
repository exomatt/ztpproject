package state;

import database.DatabaseEditor;
import model.Word;

import java.util.List;

public abstract class LanguageState {
    public abstract String getLanguage();

    public abstract List<Word> getWordList(DatabaseEditor databaseEditor);
}
