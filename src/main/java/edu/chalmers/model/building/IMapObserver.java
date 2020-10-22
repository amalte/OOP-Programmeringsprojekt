package edu.chalmers.model.building;

import edu.chalmers.services.Coords;

/**
 * @author Malte Åkvist
 * <p>
 * Interface for a MapObserver
 */
public interface IMapObserver {
    /**
     * Updates by using blocks position to remove from the map when notified by an observer.
     */
    void update(Coords tileRemoved);
}
