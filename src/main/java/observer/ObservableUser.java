package observer;

import lombok.Data;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * The type Observable user.
 */
@Data
public class ObservableUser extends Observable {
    private List<Observer> observers;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
        //todo dokonczyc :/

    }
}
