package state;

import database.DatabaseEditor;
import model.Word;

import java.util.List;

/**
 * The type Language state.
 */
public interface LanguageState {
    /**
     * Gets language.
     *
     * @return the language
     */
    String getLanguage();

    /**
     * Gets word list.
     *
     * @param databaseEditor the database editor
     * @return the word list
     */
    List<Word> getWordList(DatabaseEditor databaseEditor);
}
