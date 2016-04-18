package dg.squash.ecs;

import dg.squash.ecs.components.Component;
import dg.squash.exceptions.DuplicateComponentException;
import dg.squash.exceptions.NoSuchComponentException;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
public class Entity {

    private final Set<Component> components;
    private static int ID = 0;

    public Entity() {
        this.components = new HashSet<>();
        ID++;
    }

    public int getID() {
        return ID;
    }

    public Set<Component> getComponents() {
        return this.components;
    }

    public void removeComponent(final Class component) {
        this.components.remove(this.getComponent(component));
    }

    public <T extends Component> T getComponent(final Class<T> type) {
        for (final Component component : this.components)
            if (component.getClass().equals(type))
                return (T) component;
        throw new NoSuchComponentException(type);
    }

    public <T extends Component> boolean hasComponent(final Class<T> type) {
        for (final Component component : this.components)
            if (component.getClass().equals(type))
                return true;
        return false;
    }

    /**
     * Checks if an entity has all the provided components.
     * @param types is an array of components to check if entity contains them all.
     * @return boolean true if an entity has all the components, false - otherwise
     */
    public <T extends Component> boolean hasAllComponents(Class<T>[] types) {
        for (Class<T> t : types)
            if (!this.hasComponent(t))
                return false;
        return true;
    }

    /**
     * Adds commponent to the entity. If entity already has this component, then
     * exception will be thrown.
     * @param component to be added
     * @throws DuplicateComponentException if entity already has a component
     */
    public void addComponent(Component component) {
        // first check if the entity already have this component
        this.checkForDuplicate(component);
        // if it doesn't have add it
        this.components.add(component);
    }

    /*
    * Checks if entity has a component and if it does it will throw a
    * DuplicateComponentException.
    */
    private void checkForDuplicate(Component component) {
        for (Component c : this.components) {
            if (component.getClass().equals(c.getClass()))
                throw new DuplicateComponentException(c.getClass());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: ").append("\n");
        for (Component c : this.components)
            builder.append(c.getClass().getSimpleName()).append(": ").append(c).append("\n");
        return builder.toString();
    }
}

