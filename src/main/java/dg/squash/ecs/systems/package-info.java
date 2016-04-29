/**
 * Contains all systems for ECS framework. Every system must extend
 * {@link dg.squash.ecs.systems.AbstractECSystem}, if system
 * wants to notify {@link dg.squash.ecs.SystemEngine} about component changes
 * it has to call {@link dg.squash.ecs.SystemEngine#update(dg.squash.ecs.Entity, dg.squash.ecs.Entity)} or
 * {@link dg.squash.ecs.SystemEngine#update(dg.squash.ecs.Entity, java.lang.Class)} methods using
 * {@link dg.squash.ecs.systems.AbstractECSystem#belongsTo()} reference.
 */
package dg.squash.ecs.systems;