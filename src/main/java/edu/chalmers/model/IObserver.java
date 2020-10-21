package edu.chalmers.model;

/**
 * @author Oscar Arvidson
 *
 * IObserver interface observer interface.
 */
public interface IObserver {
    /**
     * Updates everything that should be updated when notified by an observer.
     */
    void update();
}
