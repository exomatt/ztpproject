package database;

import model.Word;

import java.util.List;

/**
 * The interface Database editor adapter.
 */
public interface DatabaseEditorAdapter {
    /**
     * Add word.
     *
     * @param word the word
     */
    void addWord(Word word);

    /**
     * Delete word.
     *
     * @param word the word
     */
    void deleteWord(Word word);

    /**
     * Find by language list.
     *
     * @param language the language
     * @return the list
     */
    List<Word> findByLanguage(String language);

    /**
     * Gets all words.
     *
     * @return the all words
     */
    List<Word> getAllWords();

    /**
     * Gets word.
     *
     * @param word the word
     * @return the word
     */
    Word getWord(String word);

    /**
     * Change world translation.
     *
     * @param word the word
     */
    void changeWorldTranslation(Word word);
}
