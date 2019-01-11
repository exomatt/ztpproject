package state;

import database.DatabaseEditor;
import model.Word;

import java.util.List;

public abstract class LanguageState {
    public String language;
    abstract List<Word> getWordList(DatabaseEditor databaseEditor);
}
