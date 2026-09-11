package game.actions;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Fire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An action represent teleportation of player's actor to teleport between locations/maps using different teleport types: Door, Circle, and Cube.
 * Each behaves uniquely:
 * - Door: teleports to a fixed destination and ignites all nearby tiles.
 * - Circle: teleports to a fixed destination and burns one random nearby tile.
 * - Cube: a portable teleporter that can malfunction, sending the user to a random spot.
 *
 * Handles teleport logic, safety checks, and fire effects for each teleport type.
 */
public class TeleportAction extends Action {

    /**
     * Enumerates teleport modes supported by this action.
     */
    public enum Mode { DOOR, CIRCLE, CUBE }

    private final Mode mode;
    private final Object sourceObj;         // TeleportDoor | TeleportCircle | TeleportCube
    private final GameMap destMap;          // intended destination map (for DOOR/CIRCLE and cube success)
    private final Location destLoc;         // intended destination location
    private final Location sourceLoc;       // where the actor starts this action
    private final Random rng = new Random();

    /**
     * Constructor for {@code TeleportAction}.
     *
     * @param mode       the teleport mode (DOOR, CIRCLE, or CUBE)
     * @param sourceObj  the source teleport object
     * @param destMap    the destination map
     * @param destLoc    the destination location
     * @param sourceLoc  the source location (may be null for Cube)
     */
    public TeleportAction(Mode mode, Object sourceObj, GameMap destMap, Location destLoc, Location sourceLoc) {
        this.mode = mode;
        this.sourceObj = sourceObj;
        this.destMap = destMap;
        this.destLoc = destLoc;
        this.sourceLoc = sourceLoc;
    }

    /**
     * Executes the teleportation action according to its mode:
     * - DOOR: ignites all tiles around the destination.
     * - CIRCLE: burns one random tile near the source location.
     * - CUBE: has a 50% chance to malfunction, teleporting randomly on the same map.
     *
     * @param actor      the actor performing the teleport
     * @param currentMap the current map where the teleport occurs
     * @return a descriptive message of the teleport result
     */
    @Override
    public String execute(Actor actor, GameMap currentMap) {
        // side effects before moving
        String burnMessage = switch (mode) {
            case DOOR -> burnAroundDestination(destLoc);
            case CIRCLE -> burnOneAroundSource(sourceLoc);
            case CUBE -> "";
        };

        // resolve final destination (cube can malfunction)
        GameMap finalMap = destMap;
        Location finalLoc = destLoc;

        // 50% malfunction for the cube (adjust to your existing boolean)
        if (mode == Mode.CUBE && !rng.nextBoolean()) {
            finalMap = currentMap;                 // same map on malfunction
            finalLoc = randomEmptyLocation(currentMap);
        }

        // ensure destination is free (avoid addActor exception)
        if (finalLoc.containsAnActor()) {
            finalLoc = randomEmptyLocation(finalMap);
        }

        try {
            if (finalMap == currentMap) {
                // same map: safe helper (no checked exception)
                currentMap.moveActor(actor, finalLoc);
            } else {
                // cross-map: remove then add (addActor throws GameEngineException)
                currentMap.removeActor(actor);
                finalMap.addActor(actor, finalLoc);
            }
        } catch (GameEngineException e) {
            return "Teleport failed: " + e.getMessage();
        }

        String result = switch (mode) {
            case DOOR   -> actor + " uses Teleport Door to teleport to (" + finalLoc.x() + ", " + finalLoc.y() + ") on " + finalMap;
            case CIRCLE -> actor + " uses Teleport Circle to teleport to (" + finalLoc.x() + ", " + finalLoc.y() + ") on " + finalMap;
            case CUBE   -> actor + " uses Teleport Cube to teleport to (" + finalLoc.x() + ", " + finalLoc.y() + ") on " + finalMap
                    + (finalMap == currentMap ? " (malfunction!)" : "");
        };

        return burnMessage.isEmpty() ? result : result + "\n" + burnMessage;
    }

    /**
     * Provides a description of the teleport option in the game menu.
     *
     * @param actor the actor performing the action
     * @return a readable menu description for the teleport
     */
    @Override
    public String menuDescription(Actor actor) {
        return switch (mode) {
            case CUBE -> "Use Teleport Cube to teleport to (" + destLoc.x() + "," + destLoc.y() + ") on " + destMap;
            case DOOR -> "Use Teleport Door to teleport to (" + destLoc.x() + "," + destLoc.y() + ") on " + destMap;
            case CIRCLE -> "Use Teleport Circle to teleport to (" + destLoc.x() + "," + destLoc.y() + ") on " + destMap;
        };
    }

    /**
     * Burns all surrounding tiles around the destination location (for DOOR).
     *
     * @param dest the destination location
     * @return a description of the fire, for the player
     */
    private String burnAroundDestination(Location dest) {

        //get the destination (dest) map location, and store the coordinate
        GameMap map = dest.map();
        int cx = dest.x(), cy = dest.y();

        //iterate through the 3x3 area centered around the destination
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; //skip the center tile

                //calculate coordinate of surrounding tiles
                int x = cx + dx, y = cy + dy;

                //if within map bounds, set that tile on fire
                if (inBounds(map, x, y)) map.at(x, y).setGround(new Fire());
            }
        }

        return "The Teleport Door scorches the ground around ("
                + cx + ", " + cy + ") on " + map + ".";
    }

    /**
     * Burns one random valid tile around the source location (for CIRCLE).
     *
     * @param src the source location of the teleport
     * @return a description of the fire, for the player
     */
    private String burnOneAroundSource(Location src) {

        //get the source (src) map location, store any valid adjacent tiles
        GameMap map = src.map();
        List<Location> neighbours = new ArrayList<>();

        //iterate through all adjacent tiles around the source location
        for (var exit : src.getExits()) {
            Location neighbour = exit.getDestination();
            if (neighbour != null && inBounds(map, neighbour.x(), neighbour.y())) {
                neighbours.add(neighbour);
            }
        }

        // a location with no valid neighbours cannot ignite anything
        if (neighbours.isEmpty()) {
            return "";
        }

        //random pick 1 out of valid neighbours, and replace it with FireGround ground type
        Location randomNeighbour = neighbours.get(rng.nextInt(neighbours.size()));
        randomNeighbour.setGround(new Fire());

        return "The Teleport Circle ignites a fire at ("
                + randomNeighbour.x() + ", " + randomNeighbour.y() + ") on " + map + ".";
    }

    /**
     * Checks if the specified coordinates are within the map boundaries.
     *
     * @param map the game map
     * @param x   the x-coordinate
     * @param y   the y-coordinate
     * @return true if coordinates are valid, false otherwise
     */
    private boolean inBounds(GameMap map, int x, int y) {
        return x >= 0 && x <= map.getXRange().max() && y >= 0 && y <= map.getYRange().max();
    }

    /**
     * Finds a random empty location on the map that does not contain an actor.
     * Used for cube malfunctions or occupied destination tiles.
     *
     * @param map the map to search
     * @return a valid empty location
     */
    private Location randomEmptyLocation(GameMap map) {
        for (int tries = 0; tries < 200; tries++) {
            int x = rng.nextInt(map.getXRange().max() + 1);
            int y = rng.nextInt(map.getYRange().max() + 1);
            Location loc = map.at(x, y);
            if (!loc.containsAnActor()) return loc;
        }
        // fallback to origin if somehow full
        return map.at(0, 0);
    }
}