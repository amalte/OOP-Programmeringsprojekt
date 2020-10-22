package edu.chalmers.model;

/**
 * @author Oscar Arvidson
 * <p>
 * MockObserver test class used to test observer pattern
 */
public class MockObserver implements IObserver {

    private boolean test = false;

    @Override
    public void update() {
        test = true;
    }

    public boolean isTest() {
        return test;
    }
}
