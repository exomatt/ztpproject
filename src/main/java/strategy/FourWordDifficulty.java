package strategy;

import database.DataBaseEditorTarget;
import model.Word;
import state.LanguageState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Four word difficulty.
 */
public class FourWordDifficulty implements Difficulty {
    @Override
    public List<Word> getAnswerWords(Word word, DataBaseEditorTarget editor, LanguageState state) {
        List<Word> result = new ArrayList<>();
        Word correct = word.getTranslation();
        result.add(correct);
        List<Word> wordList = state.getWordList(editor);
        wordList.removeIf(p -> p.getWord().equals(correct.getWord()));
        result.addAll(WordRandomizer.getRandomWords(wordList, 3));
        Collections.shuffle(result);
        return result;
    }
}
