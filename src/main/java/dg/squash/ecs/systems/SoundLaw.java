package dg.squash.ecs.systems;

import dg.squash.ecs.components.HealthComponent;

public class SoundLaw  {

    public void update(HealthComponent component) {
        int health = component.show();
        if (health > 0) {
         //  AssetManager.TOMATO_HIT.play();
        } else {
          //  AssetManager.SPLAT.play();
        }
    }
}
