package iterator;

import model.Word;

import java.util.Iterator;

public class RandomIterator implements Iterator<Word> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Word next() {
        return null;
    }
}
