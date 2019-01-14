package client;

import java.awt.*;


public class Client {
    public static void main(String[] args) {
        //todo opisac biblioteke zewnetrzna swing, lombok

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu();
            }
        });

//
//        Word word = new Word("wisnia", null, "pl");
//        Word word1 = new Word("cherry", word, "eng");
//        word.setTranslation(word1);
//        Word word2 = new Word("pomarańcza", null, "pl");
//        Word toChange = new Word("cherry", word2, "eng");
//        word2.setTranslation(toChange);
//
//
//        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
//        Query query = entityManager.createQuery("select c from Word  c", Word.class);
//        List<Word> resultList = query.getResultList();
//        for (Word word22 : resultList) {
//            System.out.println(word22.toString());
//        }
//        DatabaseEditorAdapter databaseEditorAdapter = new DatabaseRepository();
//        System.out.println(databaseEditorAdapter.getWord("cherry"));
//        List<Word> pl = databaseEditorAdapter.findByLanguage("pl");
//        pl.forEach(System.out::println);
//        System.out.println("halko to tu " + entityManager.contains(word1));
//        databaseEditorAdapter.addWord(word1);
//        DatabaseEditorAdapter databaseFile = new FileDatabase();
////        databaseFile.addWord(word1);
//        System.out.println("test all:");
//        List<Word> allWords = databaseFile.getAllWords();
//        allWords.forEach(System.out::println);
//        System.out.println("test pl:");
//        List<Word> pl1 = databaseFile.findByLanguage("pl");
//        pl1.forEach(System.out::println);
//
//        Word wiśnia = databaseFile.getWord("wiśnia");
//        System.out.println("tesowe pojedyncze: " + wiśnia.toString());
//
//        Word czeresnia = databaseFile.getWord("czeresnia");
//        if (czeresnia == null) {
//            System.out.println("nie ma xd s");
//        }
//        databaseFile.changeWorldTranslation(toChange);
//        Word cheryy = databaseFile.getWord("cherry");
//        System.out.println(cheryy.toString());
//        databaseFile.deleteWord(word1);

//        try {
//            TxtBuilder pdfBuilder = new TxtBuilder();
//            pdfBuilder.addWordToTranslate(word.getWord());
//            pdfBuilder.addWordToTranslate(word1.getWord());
//            List<String> answers = new ArrayList<>(Arrays.asList("Yes","Perhaps","No",word.getTranslation().getWord(),"Tak"));
//            List<String> answers1 = new ArrayList<>(Arrays.asList(word1.getTranslation().getWord(),"No","Maybe","Tak"));
//            pdfBuilder.addAnswersList(answers);
//            pdfBuilder.addAnswersList(answers1);
//            pdfBuilder.addCorrectAnswer(word.getTranslation().getWord());
//            pdfBuilder.addCorrectAnswer(word1.getTranslation().getWord());
//            pdfBuilder.addUserAnswer(word.getTranslation().getWord());
//            pdfBuilder.addUserAnswer("Maybe");
//            pdfBuilder.buildTxt();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
