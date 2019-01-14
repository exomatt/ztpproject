package client;

import model.Word;

import javax.swing.*;
import java.util.ArrayList;

public class CustomListModel extends AbstractListModel {

    private ArrayList<Word> arrayList;

    public CustomListModel() {
        this.arrayList = new ArrayList<>();
    }

    public CustomListModel(ArrayList<Word> arrayList) {
        this.arrayList = arrayList;
    }

    public void add(Word word) {
        arrayList.add(word);
        fireIntervalAdded(arrayList, arrayList.indexOf(word), arrayList.indexOf(word));
    }

    public void remove(int idx) {
        arrayList.remove(idx);
        fireIntervalRemoved(arrayList, idx, idx);
    }

    public int getSize() {
        return arrayList.size();
    }

    public Object getElementAt(int index) {
        return arrayList.get(index).getWord();
    }
}
