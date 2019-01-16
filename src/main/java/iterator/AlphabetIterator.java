package iterator;

import lombok.extern.java.Log;
import model.Word;

import java.util.*;

/**
 * The type Alphabet iterator.
 */
@Log
public class AlphabetIterator implements Iterator<Word> {
    private List<Word> wordList = new ArrayList<>();
    private int position;

    /**
     * Instantiates a new Alphabet iterator.
     *
     * @param listFromDB the list from db
     * @param number     the number
     */
    public AlphabetIterator(List<Word> listFromDB, int number) {
        position = 0;
        Collections.shuffle(listFromDB);
        for (int i = 0; i < number; i++) {
            wordList.add(listFromDB.get(i));
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
