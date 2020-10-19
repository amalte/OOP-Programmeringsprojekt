package edu.chalmers.model;

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
