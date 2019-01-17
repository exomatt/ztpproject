package database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Word;

import java.util.List;

/**
 * The type Database editor.
 */
@Getter
@Setter
@NoArgsConstructor
public class DatabaseEditorAdapter implements DataBaseEditorTarget {
    private DatabaseEditor database;

    public DatabaseEditorAdapter(DatabaseEditor database) {
        this.database = database;
    }

    /**
     * Add word.
     *
     * @param word the word
     */
    public void addWord(Word word) {
        database.addWord(word);
    }

    /**
     * Delete word.
     *
     * @param word the word
     */
    public void deleteWord(Word word) {
        database.deleteWord(word);
    }

    /**
     * Find by language list.
     *
     * @param language the language
     * @return the list
     */
    public List<Word> findByLanguage(String language) {
        return database.findByLanguage(language);
    }

    /**
     * Gets all words.
     *
     * @return the all words
     */
    public List<Word> getAllWords() {
        return database.getAllWords();
    }

    /**
     * Gets word.
     *
     * @param word the word
     * @return the word
     */
    public Word getWord(String word) {
        return database.getWord(word);
    }

    /**
     * Change world translation.
     *
     * @param word the word
     */
    public void changeWorldTranslation(Word word) {
        database.changeWorldTranslation(word);
    }
}
