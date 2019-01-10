package database;

import model.Word;

import java.util.List;

public interface DatabaseEditorAdapter {
    void addWord(Word word);

    void deleteWord(Word word);

    List<Word> findByLanguage(String language);

    List<Word> getAllWords();

    Word getWord(String word);

    void changeWorldTranslation(Word word);
}
