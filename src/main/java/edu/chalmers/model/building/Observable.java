package edu.chalmers.model.building;

import java.util.ArrayList;
import java.util.List;

public interface Observable {
    List<Observer> observers = new ArrayList<>();
    void notifyObservers();
    void addObserver(Observer observer);
}
