package edu.chalmers.model;

import java.util.ArrayList;
import java.util.List;

public interface IObservable {

    List<IObserver> observers = new ArrayList<>();
    void addObserver(IObserver o);
    void notifyObserver();
}
