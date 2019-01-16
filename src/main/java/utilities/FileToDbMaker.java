package utilities;

import database.DatabaseEditor;
import database.DatabaseRepository;
import database.FileDatabase;
import model.Word;

import java.util.List;

/**
 * The type File to db maker.
 */
public class FileToDbMaker {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
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
