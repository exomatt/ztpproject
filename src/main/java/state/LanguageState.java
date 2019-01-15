package state;

import database.DatabaseEditor;
import model.Word;

import java.util.List;

/**
 * The type Language state.
 */
public abstract class LanguageState {
    /**
     * Gets language.
     *
     * @return the language
     */
    public abstract String getLanguage();

    /**
     * Gets word list.
     *
     * @param databaseEditor the database editor
     * @return the word list
     */
    public abstract List<Word> getWordList(DatabaseEditor databaseEditor);
}
