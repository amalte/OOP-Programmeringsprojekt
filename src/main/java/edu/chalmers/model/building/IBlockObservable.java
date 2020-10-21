package edu.chalmers.model.building;

import java.util.ArrayList;
import java.util.List;

public interface IBlockObservable {
    List<IMapObserver> IMapObservers = new ArrayList<>();
    void notifyObservers();
    void addObserver(IMapObserver IMapObserver);
}
