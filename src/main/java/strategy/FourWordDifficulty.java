package strategy;

import database.DatabaseEditor;
import model.Word;
import state.LanguageState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FourWordDifficulty implements Difficulty {
    @Override
    public List<Word> getAnswerWords(Word word, DatabaseEditor editor, LanguageState state) {
        List<Word> result = new ArrayList<>();
        result.add(word.getTranslation());
        List<Word> wordList = state.getWordList(editor);
        wordList.remove(word.getTranslation());
        result.addAll(WordRandomizer.getRandomWords(wordList, 3));
        Collections.shuffle(result);
        return result;
    }
}
