package strategy;

import database.DatabaseEditor;
import model.Word;
import state.LanguageState;

import java.util.ArrayList;
import java.util.List;

public class ThreeWordDifficulty implements Difficulty {
    @Override
    public List<Word> getAnswerWords(Word word, DatabaseEditor editor, LanguageState state) {
        List<Word> result = new ArrayList<>();
        result.add(word.getTranslation());
        List<Word> wordList = editor.findByLanguage(state.language);
        result.addAll(WordRandomizer.getRandomWords(wordList, 2));
        return result;
    }
}
