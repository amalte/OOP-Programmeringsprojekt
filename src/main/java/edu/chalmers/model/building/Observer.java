package edu.chalmers.model.building;

import edu.chalmers.services.Coords;

public interface Observer {
    void update(Coords tileRemoved);
}
