package game;

import lombok.Data;

/**
 * The type Test game.
 */
@Data
public class TestGame extends LearningGame {
    private int point;

    public void incrementPoint() {
        point++;
    }
}
