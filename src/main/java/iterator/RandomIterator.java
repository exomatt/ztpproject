package iterator;

import model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RandomIterator implements Iterator<Word> {
    List<Word> wordList = new ArrayList<>();
    int position;

    public RandomIterator(List<Word> listFromDB, int number) {
        position = 0;
        Collections.shuffle(listFromDB);
        for (int i = 0; i < number; i++) {
            wordList.add(listFromDB.get(i));
        }
        Collections.shuffle(wordList);
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
