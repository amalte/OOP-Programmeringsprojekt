package edu.chalmers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oscar Arvidson
 *
 * IObservable interface observable interface.
 */
public interface IObservable {
    /**
     * List of IObservers.
     */
    List<IObserver> observers = new ArrayList<>();

    /**
     * Adds an IObserver to observers.
     * @param o IObserver to be added to List observers.
     */
    void addObserver(IObserver o);

    /**
     * Notify all IObservers in List observers.
     */
    void notifyObserver();

    /**
     * Removes an IObserver from observers.
     * @param o IObserver to be removed form list observers.
     */
    void removeObserver(IObserver o);
}
