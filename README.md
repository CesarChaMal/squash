# Squash

A *breakout* game clone written in JavaFX. 

## Splash screen

<img src="http://i.imgur.com/fHv35x0.jpg" width="500" height="400">

## Main screen

<img src="http://i.imgur.com/6eOf8qf.jpg" width="500" height="400">

## Gameplay

As per traditional *breakout* player must destroy all enemies (tomatoes in this case), to progress
to next level. Unfortunately, at the moment only one level exists. 

To destroy an enemy player must hit it with a ball witch bounces all over the screen.
If ball hits the ground player looses one life, but not a progress of the level. Some, balls 
have resistance, thus needs more hits to be destroyed.

For each destroyed tomato, player gets some points.

## Further work

This is by far not a complete game. Some possible extensions:

- Add more levels.
- Add bonus or other perks.
- Add ability to share score between players. 
- etc.

## Prerequisites

You will need to have latest [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) 
version 8 installed. To check run `java -version`.

## Building and running

1. First clone it `git clone https://github.com/grrinchas/squash`
2. If you don't have [gradle](https://gradle.org/install) installed. Then run 
`./gradlew`. It should download and install it.
3. Build it with `gradle build` and run it with `gradle run`.
4. Enjoy.

## Licence 

This project is licensed under the GNU General Public License v3.0 - see the 
[LICENSE](https://www.gnu.org/licenses/gpl-3.0.en.html) file for details.
