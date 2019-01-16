package iterator;

import lombok.extern.java.Log;
import model.Word;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * The type Alphabet iterator.
 */
@Log
public class AlphabetIterator implements Iterator<Word> {
    /**
     * The Word list.
     */
    List<Word> wordList;
    /**
     * The Position.
     */
    int position;

    /**
     * Instantiates a new Alphabet iterator.
     *
     * @param listFromDB the list from db
     * @param number     the number
     */
    public AlphabetIterator(List<Word> listFromDB, int number) {
        position = 0;
        if (listFromDB.size() > number) {
            log.severe("Number is greater than list size");
        }
        Collections.shuffle(listFromDB);
        for (int i = 0; i < number; i++) {
            wordList.add(listFromDB.get(i)); //todo - tu cos wywala nullpointera jak po starcie programu wybbierzesz alfabetyczny iterator
        }
        Collections.sort(wordList, Comparator.comparing(Word::getWord));
    }

    @Override
    public boolean hasNext() {
        if (position == wordList.size()) {
            return false;
        }
        return true;
    }

    @Override
    public Word next() {
        return wordList.get(position++);
    }
}
