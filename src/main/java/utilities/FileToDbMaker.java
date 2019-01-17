package utilities;

import database.DataBaseEditorTarget;
import database.DatabaseEditorAdapter;
import database.DatabaseRepositoryAdaptee;
import database.FileDatabaseAdaptee;
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
        DataBaseEditorTarget editorFile = new DatabaseEditorAdapter(new FileDatabaseAdaptee());
        DataBaseEditorTarget editorDB = new DatabaseEditorAdapter(new DatabaseRepositoryAdaptee());
        List<Word> wordList = editorFile.findByLanguage("pl");
        for (Word word : wordList) {
            editorDB.addWord(word);
        }
    }
}
