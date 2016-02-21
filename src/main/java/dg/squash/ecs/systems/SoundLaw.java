package dg.squash.ecs.systems;

import dg.squash.ecs.components.HealthComponent;
import dg.squash.events.HealthListener;

public class SoundLaw implements HealthListener {

    @Override
    public void update(HealthComponent component) {
        int health = component.show();
        if (health > 0) {
         //  AssetManager.TOMATO_HIT.play();
        } else {
          //  AssetManager.SPLAT.play();
        }
    }
}
