package edu.chalmers.model.building;

import edu.chalmers.services.Coords;

public interface IMapObserver {
    void update(Coords tileRemoved);
}
