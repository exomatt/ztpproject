package Builder;

import model.Word;

import java.util.List;

/**
 * The interface Raport builder.
 */
public interface RaportBuilder {
    /**
     * Add word to translate.
     *
     * @param word the word
     */
    public void addWordToTranslate(Word word);

    /**
     * Add answers list.
     *
     * @param answers the answers
     */
    public void addAnswersList(List<Word> answers);

    /**
     * Add correct answer.
     *
     * @param correct the correct
     */
    public void addCorrectAnswer(Word correct);

    /**
     * Add user answer.
     *
     * @param userAnswer the user answer
     */
    public void addUserAnswer(Word userAnswer);
}
