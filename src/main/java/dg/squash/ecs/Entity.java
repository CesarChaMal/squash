package dg.squash.ecs;

import dg.squash.ecs.components.Component;
import dg.squash.exceptions.DuplicateComponentException;
import dg.squash.exceptions.NoSuchComponentException;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity is a container for components. Different components makes an entity unique object. An entity cannot
 * have duplicate components, so inserting same type of component in the entity will produce an exception. All the methods in
 * entity class is for adding, removing and checking for component existence, there is no behaviour methods at all.
 * ALL entities are controlled through systems. So to create an entity which would represent <em>ball</em> object we
 * would do like this
 * <p>
 *     Entity ball = new Entity(); <br><br>
 *     <em> // give name "Ball" to the entity </em> <br>
 *     ball.addComponent(new NameComponent(ball, "Ball")); <br><br>
 *     <em>// adds circle shape component for the ball at 10, 10 position and radius 20 </em> <br>
 *     ball.addComponent(new ShapeComponent(ball, new CircleShape(new Vector2D(10, 10), 20));
 * </p>
 */
public class Entity {

    /* Entity cannot contain duplicate components and doesn't matter in which order they are stored.
    *  The best data structure to store components is hash set.
    */
    private final Set<Component> components;

    /**
     * Creates default entity without any components.
     */
    public Entity() {
        this.components = new HashSet<>();
    }

    /**
     * Returns all the components which entity has.
     * @return list of components
     */
    public Set<Component> getComponents() {
        return this.components;
    }

    /**
     * Removes supplied component from the entity.
     * @param component to be removed
     */
    public void removeComponent(Class component) {
        this.components.remove(this.getComponent(component));
    }

    /**
     * Returns particular type of the component from the entity.
     * @param type of component to be return from an entity
     * @param <T> type of component to be returned
     * @return a component of that type
     * @throws NoSuchComponentException if entity doesn't have
     * this type of component
     */
    public <T extends Component> T getComponent(Class<T> type) {
        for (Component component : this.components)
            if (component.getClass().equals(type))
                return (T) component;
        throw new NoSuchComponentException(type);
    }

    /**
     * Checks if entity has a particular component.
     * @param type is component to be checked in entity
     * @param <T> type of the component
     * @return true if entity has this component otherwise return false
     */
    public <T extends Component> boolean hasComponent(Class<T> type) {
        for (Component component : this.components)
            if (component.getClass().equals(type))
                return true;
        return false;
    }

    /**
     * Checks if an entity has all the provided components.
     * @param types is an array of components to check if entity contains them all
     * @param <T> type of component
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

