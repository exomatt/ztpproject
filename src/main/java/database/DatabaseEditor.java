package database;

import lombok.Getter;
import lombok.Setter;
import model.Word;

import java.util.List;

@Getter
@Setter
public class DatabaseEditor {
    private DatabaseEditorAdapter database;

    public void addWord(Word word) {
        database.addWord(word);
    }

    public void deleteWord(Word word) {
        database.deleteWord(word);
    }

    public List<Word> findByLanguage(String language) {
        return database.findByLanguage(language);
    }

    public List<Word> getAllWords() {
        return database.getAllWords();
    }

    public Word getWord(String word) {
        return database.getWord(word);
    }

    public void changeWorldTranslation(Word word) {
        database.changeWorldTranslation(word);
    }
}
