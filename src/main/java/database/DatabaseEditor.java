package database;

import lombok.Getter;
import lombok.Setter;
import model.Word;

import java.util.List;

@Getter
@Setter
public class DatabaseEditor {
    private DatabaseEditorAdapter database;

    void addWord(Word word) {
        database.addWord(word);
    }

    void deleteWord(Word word) {
        database.deleteWord(word);
    }

    List<Word> findByLanguage(String language) {
        return database.findByLanguage(language);
    }

    List<Word> getAllWords() {
        return database.getAllWords();
    }

    Word getWord(String word) {
        return database.getWord(word);
    }

    void changeWorldTranslation(Word word) {
        database.changeWorldTranslation(word);
    }
}
