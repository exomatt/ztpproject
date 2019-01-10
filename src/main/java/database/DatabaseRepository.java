package database;

import lombok.extern.java.Log;
import model.Word;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Log
public class DatabaseRepository implements DatabaseEditorAdapter {
    public void addWord(Word word) {
        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
        if (!entityManager.contains(word)) {
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
        entityManager.remove(word);
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
        Word firstResult = (Word) query.getSingleResult();
        return firstResult;
    }

    public void changeWorldTranslation(Word word) {
        EntityManager entityManager = ConnectionSingleton.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(word);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
