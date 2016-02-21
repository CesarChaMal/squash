package dg.squash.ecs.components;

import dg.squash.events.HealthListener;
import dg.squash.ecs.Entity;

import java.util.ArrayList;
import java.util.List;

public final class HealthComponent extends AbstractComponent {

    private int health = 0;
    public static final List<HealthListener> HEALTH_CHANGE_LISTENERS = new ArrayList<>();

    public HealthComponent(Entity parent) {
        super(parent);
    }

    public HealthComponent(Entity parent, int health) {
        super(parent);
        this.health = health;
    }

    public int show() {
        return health;
    }

    public void decrease(int health) {
        if (this.health - health < 0 || health == 0)
            return;
        this.health -= health;
        notifyListeners(HEALTH_CHANGE_LISTENERS);
    }

    private void notifyListeners(List<HealthListener> componentListeners) {
        for (HealthListener l : componentListeners)
            l.update(this);
    }
}
