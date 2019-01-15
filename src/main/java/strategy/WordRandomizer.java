package strategy;

import model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Word randomizer.
 */
public class WordRandomizer {
    //todo zeby sie nie powtarzalo ;)

    /**
     * Gets random words.
     *
     * @param listToRandom the list to random
     * @param number       the number
     * @return the random words
     */
    public static List<Word> getRandomWords(List<Word> listToRandom, int number) {
        List<Word> wordList = new ArrayList<>();
        Collections.shuffle(listToRandom);
        for (int i = 0; i < number; i++) {
            wordList.add(listToRandom.get(i));
        }
        return wordList;
    }
}
