package observer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * The type Observable user.
 */
@Data
@NoArgsConstructor
public class ObservableUser extends Observable implements ActionListener {

    private int timeAmount;

    private List<Observer> observers = new ArrayList<>();
    private JLabel timeLabel;
    private int elapsedSeconds;
    private String seconds = Integer.toString(elapsedSeconds);


    /**
     * Instantiates a new Observable user.
     *
     * @param timeLabel     the time label
     * @param numberOfWords the number of words
     */
    public ObservableUser(JLabel timeLabel, int numberOfWords) {
        this.timeLabel = timeLabel;
        timeAmount = 20 * numberOfWords;
        elapsedSeconds = timeAmount;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = observers.get(i);
            observer.update(this, "");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (elapsedSeconds == 0) {
            notifyObservers();
        } else {
            elapsedSeconds--;
        }
        timeLabel.setText("Seconds left: " + elapsedSeconds);
    }
}
