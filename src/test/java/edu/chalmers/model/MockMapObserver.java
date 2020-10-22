package edu.chalmers.model;

import edu.chalmers.model.building.IMapObserver;
import edu.chalmers.services.Coords;

public class MockMapObserver implements IMapObserver {
    private Coords tileToUpdate = new Coords(0, 0);

    @Override
    public void update(Coords tileRemoved) {
        tileToUpdate = tileRemoved;
    }

    public Coords getTileToUpdate() { return tileToUpdate; }
}
