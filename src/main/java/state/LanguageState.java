package state;

import database.DatabaseEditor;
import model.Word;

import java.util.List;

public abstract class LanguageState {
    abstract List<Word> getWordList(DatabaseEditor databaseEditor);
}
