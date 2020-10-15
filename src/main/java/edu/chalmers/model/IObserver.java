package edu.chalmers.model;

public interface IObserver {
    /**
     * Updates everything that should be updated when notified by an observer.
     */
    void update();
}
