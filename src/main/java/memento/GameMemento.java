package memento;

import lombok.Data;

import java.io.Serializable;

/**
 * The type Game memento.
 */
@Data
public class GameMemento implements Serializable {
    private GameToSave gameState;

}
