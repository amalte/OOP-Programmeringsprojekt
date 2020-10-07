package edu.chalmers.model.building;

public interface IBlock {
    boolean canBeDestroyed();
    void remove();
    void inflictDamage(int damage);
}
