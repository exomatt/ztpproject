package database;

import lombok.extern.java.Log;
import model.Word;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type File database.
 */
@Log
public class FileDatabaseAdaptee implements DatabaseEditorAdaptee {
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
            JOptionPane.showMessageDialog(new JFrame(), "Problem with saving file database", "Dialog",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void deleteWord(Word word) {
        try {
            List<String> wordList = Files.readAllLines(Paths.get(filePath));
            if (wordList.isEmpty()) {
                log.info("List is emvpty ");
                return;
            }
            List<String> toSave = new ArrayList<>();
            for (String wordInList : wordList) {
                String[] split = wordInList.split(",");
                if (split[0].replace(" ", "").equals(word.getWord()) || split[2].replace(" ", "").equals(word.getWord())) {
                    log.info("Object successfully delete : " + word.toString());
                    continue;
                }
                toSave.add(wordInList);
            }
            Files.write(Paths.get(filePath), String.join("\n", toSave).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Problem with saving file database", "Dialog",
                    JOptionPane.ERROR_MESSAGE);
            log.severe("Problem with DB file:" + e.toString());
        }
    }

    public List<Word> findByLanguage(String language) {
        List<Word> allWords = getAllWords();
        if (allWords.isEmpty()) {
            log.info("Empty list !! :( ");
            return null;
        }

        return allWords.stream().filter(p -> p.getLanguage().equals(language)).collect(Collectors.toList());
    }

    public List<Word> getAllWords() {
        List<Word> result = new ArrayList<>();
        try {
            List<String> wordList = Files.readAllLines(Paths.get(filePath));
            if (wordList.isEmpty()) {
                log.info("List is emvpty ");
                return Collections.emptyList();
            }
            for (String wordInList : wordList) {
                String[] split = wordInList.split(",");
                Word word = new Word();
                Word word1 = new Word();
                word.setWord(split[0].replace(" ", ""));
                word.setLanguage(split[1].replace(" ", ""));
                word1.setWord(split[2].replace(" ", ""));
                word1.setLanguage(split[3].replace(" ", ""));
                word1.setTranslation(word);
                word.setTranslation(word1);
                result.add(word);
                result.add(word1);
            }
        } catch (IOException e) {
            log.severe("Problem with DB file:" + e.toString() + " " + Arrays.toString(e.getStackTrace()));
            JOptionPane.showMessageDialog(new JFrame(), "Problem with file database", "Dialog",
                    JOptionPane.ERROR_MESSAGE);
            return Collections.emptyList();
        }
        return result;
    }

    public Word getWord(String word) {
        List<Word> allWords = getAllWords();
        if (allWords.isEmpty()) {
            log.info("Empty list !! :( ");
            return null;
        }
        for (Word allWord : allWords) {
            if (allWord.getWord().equals(word)) {
                return allWord;
            }
        }
        log.info("Not found that word in DB:" + word);
        return null;
    }

    public void changeWorldTranslation(Word word) {
        Word wordFromDB = getWord(word.getWord());
        if (wordFromDB == null) {
            log.info("Word non found in db");
            return;
        }
        deleteWord(wordFromDB);
        addWord(word);
        log.info("Update word: " + wordFromDB.toString() + "->" + word.toString());
    }
}
