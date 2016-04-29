package dg.squash.ecs.systems;

import dg.squash.ecs.SystemEngine;

/**
 * Default implementation of the {@link dg.squash.ecs.systems.ECSystem} interface.
 * Any class which want's to be a system should extend this class.
 */
public abstract class AbstractECSystem implements ECSystem {

    // holds a reference for parent systemEngine
    private SystemEngine systemEngine;

    /**
     * Creates system with it's parent SystemEngine
     * @param systemEngine parent for systems
     */
    public AbstractECSystem(SystemEngine systemEngine) {
        this.systemEngine = systemEngine;
    }

    /**
     * Returns a reference parent systemEngine
     * @return systemEngine
     */
    @Override
    public SystemEngine belongsTo() {
        return systemEngine;
    }
}
