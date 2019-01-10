package database;

import lombok.extern.java.Log;
import model.Word;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Log
public class FileDatabase implements DatabaseEditorAdapter {
    private final String filePath = "database.csv";

    public void addWord(Word word) {
        try {
            List<String> wordList = Files.readAllLines(Paths.get(filePath));
            if (wordList.isEmpty()) {
                log.info("List is emvpty ");
            }
            for (String wordInList : wordList) {
                String[] split = wordInList.split(",");
                if (split[0].replace(" ", "").equals(word.getWord()) || split[2].replace(" ", "").equals(word.getWord())) {
                    log.info("Object alredy exists in DB: " + word.toString());
                    return;
                }

            }
            wordList.add(word.getWord().concat(",").concat(word.getLanguage()).concat(",").concat(word.getTranslation().getWord()).concat(",").concat(word.getTranslation().getLanguage()));
            Files.write(Paths.get(filePath), String.join("\n", wordList).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            log.severe("Problem with DB file:" + e.toString());
        }

    }

    public void deleteWord(Word word) {

    }

    public List<Word> findByLanguage(String language) {
        return null;
    }

    public List<Word> getAllWords() {
        return null;
    }

    public Word getWord(String word) {
        return null;
    }

    public void changeWorldTranslation(Word word) {

    }
}
