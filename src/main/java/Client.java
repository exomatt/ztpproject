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
        //todo opisac biblioteke zewnetrzna swing, lombok

        Word word = new Word("wiśnia", null, "pl");
        Word word1 = new Word("cherry", word, "eng");
        word.setTranslation(word1);
        Word word2 = new Word("pomarańcza", null, "pl");
        Word toChange = new Word("cherry", word2, "eng");
        word2.setTranslation(toChange);


        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
        Query query = entityManager.createQuery("select c from Word  c", Word.class);
        List<Word> resultList = query.getResultList();
        for (Word word22 : resultList) {
            System.out.println(word22.toString());
        }
        DatabaseEditorAdapter databaseEditorAdapter = new DatabaseRepository();
        System.out.println(databaseEditorAdapter.getWord("cherry"));
        List<Word> pl = databaseEditorAdapter.findByLanguage("pl");
        pl.forEach(System.out::println);
        System.out.println("halko to tu " + entityManager.contains(word1));
        databaseEditorAdapter.addWord(word1);
        DatabaseEditorAdapter databaseFile = new FileDatabase();
//        databaseFile.addWord(word1);
        System.out.println("test all:");
        List<Word> allWords = databaseFile.getAllWords();
        allWords.forEach(System.out::println);
        System.out.println("test pl:");
        List<Word> pl1 = databaseFile.findByLanguage("pl");
        pl1.forEach(System.out::println);

        Word wiśnia = databaseFile.getWord("wiśnia");
        System.out.println("tesowe pojedyncze: " + wiśnia.toString());

        Word czeresnia = databaseFile.getWord("czeresnia");
        if (czeresnia == null) {
            System.out.println("nie ma xd s");
        }
        databaseFile.changeWorldTranslation(toChange);
        Word cheryy = databaseFile.getWord("cherry");
        System.out.println(cheryy.toString());
//        databaseFile.deleteWord(word1);
    }
}
