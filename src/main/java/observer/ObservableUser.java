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


    public ObservableUser(JLabel timeLabel, int numberOfWords) {
        this.timeLabel = timeLabel;
        timeAmount = 5 * numberOfWords;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this, "");
        }
        //todo dokonczyc :/

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (elapsedSeconds == 0) {
            notifyObservers();
            elapsedSeconds = timeAmount;
        } else {
            elapsedSeconds--;
        }
        timeLabel.setText("Seconds left: " + elapsedSeconds);
    }
}
