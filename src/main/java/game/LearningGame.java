package game;

import builder.RaportBuilder;
import iterator.AlphabetIterator;
import iterator.RandomIterator;
import lombok.Data;
import lombok.NoArgsConstructor;
import memento.GameMemento;
import memento.GameToSave;
import model.Question;
import model.Word;
import state.LanguageState;
import strategy.*;

import java.util.Iterator;
import java.util.List;

/**
 * The type Learning game.
 */
@NoArgsConstructor
@Data
public class LearningGame {
    private List<Word> wordList;
    private Difficulty gameDifficulty;
    private LanguageState languageState;
    private RaportBuilder raportBuilder;
    private Iterator<Word> iterator;
    private boolean ifTest;
    private int lastQuestionIndex;
    private List<Question> questions;
    private int maxValue;
    private String diff;
    /**
     * Create iterator.
     *
     * @param mode   the mode ->  true value if random is on
     * @param number the number
     */
    public void createIterator(boolean mode, int number) {
        if (mode) {
            iterator = new RandomIterator(wordList, number);
        } else {
            iterator = new AlphabetIterator(wordList, number);
        }
    }

    public GameMemento createMemento() {
        GameMemento gameState = new GameMemento();
        GameToSave gameToSave = new GameToSave();
        gameToSave.setIfTest(ifTest);
        gameToSave.setMaxValue(maxValue);
        gameToSave.setQuestions(questions);
        gameToSave.setLastQuestionIndex(lastQuestionIndex);
        gameToSave.setDifficulty(diff);
        gameState.setGameState(gameToSave);
        return gameState;
    }

    public void setMemento(GameMemento memento) {
        GameToSave gameState = memento.getGameState();
        questions = gameState.getQuestions();
        ifTest = gameState.isIfTest();
        maxValue = gameState.getMaxValue();
        diff = gameState.getDifficulty();
        lastQuestionIndex = gameState.getLastQuestionIndex();
        switch (gameState.getDifficulty()) {
            case "2 words":
                gameDifficulty = new TwoWordDifficulty();
                break;
            case "3 words":
                gameDifficulty = new ThreeWordDifficulty();
                break;
            case "4 words":
                gameDifficulty = new FourWordDifficulty();
                break;
            case "5 words":
                gameDifficulty = new FiveWordDifficulty();
                break;
            case "Write words":
                gameDifficulty = new WrittenWordDifficulty();
                break;
            default:
                gameDifficulty = new TwoWordDifficulty();

        }
    }

}
