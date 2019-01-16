package strategy;

import database.DatabaseEditor;
import model.Word;
import state.LanguageState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Two word difficulty.
 */
public class TwoWordDifficulty implements Difficulty {

    @Override
    public List<Word> getAnswerWords(Word word, DatabaseEditor editor, LanguageState state) {
        List<Word> result = new ArrayList<>();
        Word correct = word.getTranslation();
        result.add(correct);
        List<Word> wordList = state.getWordList(editor);
        wordList.removeIf(p -> p.getWord().equals(correct.getWord()));
        result.addAll(WordRandomizer.getRandomWords(wordList, 1));
        Collections.shuffle(result);
        return result;
    }
}
