package utils;

import database.DatabaseEditor;
import database.DatabaseRepository;
import database.FileDatabase;
import model.Word;

import java.util.List;

public class FileToDbMaker {
    public static void main(String[] args) {
        DatabaseEditor editorFile = new DatabaseEditor();
        DatabaseEditor editorDB = new DatabaseEditor();
        editorFile.setDatabase(new FileDatabase());
        editorDB.setDatabase(new DatabaseRepository());
        List<Word> wordList = editorFile.findByLanguage("pl");
        for (Word word : wordList) {
            editorDB.addWord(word);
        }
    }
}
