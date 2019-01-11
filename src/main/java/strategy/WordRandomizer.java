package strategy;

import model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordRandomizer {
    public static List<Word> getRandomWords(List<Word> listToRandom, int number) {
        List<Word> wordList = new ArrayList<>();
        Collections.shuffle(listToRandom);
        for (int i = 0; i < number; i++) {
            wordList.add(listToRandom.get(i));
        }
        return wordList;
    }
}
