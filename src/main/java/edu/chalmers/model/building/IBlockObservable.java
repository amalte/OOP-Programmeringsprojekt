package edu.chalmers.model.building;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Malte Åkvist
 * <p>
 * Interface for a BlockObservable
 */
public interface IBlockObservable {

    /**
     * List of IMapObservers.
     */
    List<IMapObserver> IMapObservers = new ArrayList<>();

    /**
     * Notifies observers
     */
    void notifyObservers();

    /**
     * Adds an IMapObserver to the observer list
     */
    void addObserver(IMapObserver IMapObserver);

}
