package database;

import lombok.extern.java.Log;
import model.Word;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

/**
 * The type Database repository.
 */
@Log
public class DatabaseRepository implements DatabaseEditor {
    public void addWord(Word word) {
        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
        log.info("In addWord function in DatabaseRepository Word: " + word.toString());
        if (checkIfInDB(word)) {
            log.info("Object alredy exists in DB: " + word.toString());
            return;
        }
        entityManager.getTransaction().begin();
        entityManager.persist(word);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteWord(Word word) {
        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Word translation = word.getTranslation();
        translation.setTranslation(null);
        word.setTranslation(null);
        Word merge = entityManager.merge(word);
        Word mergeTrans = entityManager.merge(translation);
        entityManager.remove(merge);
        entityManager.remove(mergeTrans);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Word> findByLanguage(String language) {
        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
        Query query = entityManager.createQuery("select c from Word  c where c.language = ?1", Word.class);
        query.setParameter(1, language);
        List<Word> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    public List<Word> getAllWords() {
        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
        Query query = entityManager.createQuery("select c from Word  c", Word.class);
        List<Word> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    public Word getWord(String word) {
        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
        Query query = entityManager.createQuery("select c from Word  c where c.word= ?1", Word.class);
        query.setParameter(1, word);
        Word firstResult = null;
        try {
            firstResult = (Word) query.getSingleResult();
        } catch (NoResultException e) {
            log.severe("No result" + e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
        return firstResult;
    }

    public void changeWorldTranslation(Word word) {
        Word newTranslation = word.getTranslation();
        Word oldTranslation = getWord(word.getWord()).getTranslation();
        word.setTranslation(oldTranslation);
        deleteWord(word);
        word.setTranslation(newTranslation);
        addWord(word);
//        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.merge(word);
//        entityManager.getTransaction().commit();
//        entityManager.close();
    }

    private boolean checkIfInDB(Word word) {
        List<Word> allWords = getAllWords();
        for (Word allWord : allWords) {
            if (allWord.getWord().equals(word.getWord())) {
                return true;
            }
        }
        return false;
    }

}
