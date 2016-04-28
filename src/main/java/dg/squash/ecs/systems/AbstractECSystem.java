package dg.squash.ecs.systems;

import dg.squash.ecs.SystemEngine;

public abstract class AbstractECSystem implements ECSystem {

    private SystemEngine systemEngine;

    public AbstractECSystem(SystemEngine systemEngine) {
        this.systemEngine = systemEngine;
    }

    @Override
    public SystemEngine belongsTo() {
        return systemEngine;
    }
}
