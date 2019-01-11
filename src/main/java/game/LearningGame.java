package game;

import Builder.RaportBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Word;
import state.LanguageState;
import strategy.Difficulty;

import java.util.Iterator;
import java.util.List;

@NoArgsConstructor
@Data
public class LearningGame {
    private List<Word> questions;
    private Difficulty gameDifficulty;
    private LanguageState languageState;
    private RaportBuilder raportBuilder;
    private Iterator iterator;


}
