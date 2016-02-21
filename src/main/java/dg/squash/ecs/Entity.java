package dg.squash.ecs;

import dg.squash.ecs.components.Component;
import dg.squash.exceptions.DuplicateComponentException;
import dg.squash.exceptions.NoSuchComponentException;

import java.util.HashSet;
import java.util.Set;

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

    public final <T extends Component> boolean hasAllComponents(final Class<T>[] types) {
        for (final Class<T> t : types)
            if (!this.hasComponent(t))
                return false;
        return true;
    }

    public void addComponent(final Component component) {
        this.checkForDuplicate(component);
        this.components.add(component);
    }

    private void checkForDuplicate(final Component component) {
        for (final Component c : this.components) {
            if (component.getClass().equals(c.getClass()))
                throw new DuplicateComponentException(c.getClass());
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ID: ").append("\n");
        //   builder.append("Name: " + type + "\n");
        for (final Component c : this.components)
            builder.append(c.getClass().getSimpleName()).append(": ").append(c).append("\n");
        return builder.toString();
    }


}

