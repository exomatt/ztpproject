package database;

import model.Word;

import java.util.List;

public interface DataBaseEditorTarget {
    /**
     * Add word.
     *
     * @param word the word
     */
    public void addWord(Word word);

    /**
     * Delete word.
     *
     * @param word the word
     */
    public void deleteWord(Word word);

    /**
     * Find by language list.
     *
     * @param language the language
     * @return the list
     */
    public List<Word> findByLanguage(String language);

    /**
     * Gets all words.
     *
     * @return the all words
     */
    public List<Word> getAllWords();


    /**
     * Gets word.
     *
     * @param word the word
     * @return the word
     */
    public Word getWord(String word);


    /**
     * Change world translation.
     *
     * @param word the word
     */
    public void changeWorldTranslation(Word word);
}
