package client;

import model.Word;

import javax.swing.*;
import java.util.List;

/**
 * The type Custom list model.
 */
public class CustomListModel extends AbstractListModel {

    private List<Word> arrayList;
    /**
     * Instantiates a new Custom list model.
     *
     * @param arrayList the array list
     */
    public CustomListModel(List<Word> arrayList) {
        this.arrayList = arrayList;
    }

    /**
     * Add.
     *
     * @param word the word
     */
    public void add(Word word) {
        arrayList.add(word);
        fireIntervalAdded(arrayList, arrayList.indexOf(word), arrayList.indexOf(word));
    }
    public int getSize() {
        return arrayList.size();
    }

    public Object getElementAt(int index) {
        return arrayList.get(index).getWord();
    }

}
