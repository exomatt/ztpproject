import database.ConnectionSingleton;
import database.DatabaseEditorAdapter;
import database.DatabaseRepository;
import database.FileDatabase;
import model.Word;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class Client {
    public static void main(String[] args) {
        Word word = new Word("wiśnia", null, "pl");
        Word word1 = new Word("cherry", word, "eng");
        word.setTranslation(word1);
        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
        Query query = entityManager.createQuery("select c from Word  c", Word.class);
        List<Word> resultList = query.getResultList();
        for (Word word2 : resultList) {
            System.out.println(word2.toString());
        }
        DatabaseEditorAdapter databaseEditorAdapter = new DatabaseRepository();
        System.out.println(databaseEditorAdapter.getWord("cherry"));
        List<Word> pl = databaseEditorAdapter.findByLanguage("pl");
        pl.forEach(System.out::println);
//        databaseEditorAdapter.addWord(word1);
        DatabaseEditorAdapter databaseEditorAdapter1 = new FileDatabase();
//        databaseEditorAdapter1.addWord(word1);
        System.out.println("test all:");
        List<Word> allWords = databaseEditorAdapter1.getAllWords();
        allWords.forEach(System.out::println);
        System.out.println("test pl:");
        List<Word> pl1 = databaseEditorAdapter1.findByLanguage("pl");
        pl1.forEach(System.out::println);
//        databaseEditorAdapter1.deleteWord(word1);
    }
}
