package game.actions;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.FireGround;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeleportAction extends Action {

    public enum Mode { DOOR, CIRCLE, CUBE }

    private final Mode mode;
    private final Object sourceObj;         // TeleportDoor | TeleportCircle | TeleportCube
    private final GameMap destMap;          // intended destination map (for DOOR/CIRCLE and cube success)
    private final Location destLoc;         // intended destination location
    private final Location sourceLoc;       // where the actor starts this action
    private final Random rng = new Random();

    public TeleportAction(Mode mode, Object sourceObj, GameMap destMap, Location destLoc, Location sourceLoc) {
        this.mode = mode;
        this.sourceObj = sourceObj;
        this.destMap = destMap;
        this.destLoc = destLoc;
        this.sourceLoc = sourceLoc;
    }

    @Override
    public String execute(Actor actor, GameMap currentMap) {
        // side effects before moving
        switch (mode) {
            case DOOR -> burnAroundDestination(destLoc);
            case CIRCLE -> burnOneAroundSource(sourceLoc);
            case CUBE -> { /* handled below after malfunction roll */ }
        }

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

        return switch (mode) {
            case DOOR   -> actor + " uses Teleport Door to teleport to (" + finalLoc.x() + ", " + finalLoc.y() + ") on " + finalMap;
            case CIRCLE -> actor + " uses Teleport Circle to teleport to (" + finalLoc.x() + ", " + finalLoc.y() + ") on " + finalMap;
            case CUBE   -> actor + " uses Teleport Cube to teleport to (" + finalLoc.x() + ", " + finalLoc.y() + ") on " + finalMap
                    + (finalMap == currentMap ? " (malfunction!)" : "");
        };
    }

    @Override
    public String menuDescription(Actor actor) {
        return switch (mode) {
            case CUBE -> "Use Teleport Cube to teleport to (" + destLoc.x() + "," + destLoc.y() + ") on " + destMap;
            case DOOR -> "Use Teleport Door to teleport to (" + destLoc.x() + "," + destLoc.y() + ") on " + destMap;
            case CIRCLE -> "Use Teleport Circle to teleport to (" + destLoc.x() + "," + destLoc.y() + ") on " + destMap;
        };
    }

    //helper supporting functions
    private void burnAroundDestination(Location dest) {
        GameMap map = dest.map();
        int cx = dest.x(), cy = dest.y();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int x = cx + dx, y = cy + dy;
                if (inBounds(map, x, y)) map.at(x, y).setGround(new FireGround());
            }
        }
    }

    private void burnOneAroundSource(Location src) {
        GameMap map = src.map();
        List<Location> neighbours = new ArrayList<>();

        for (var exit : src.getExits()) {
            Location neighbour = exit.getDestination();
            if (neighbour != null && inBounds(map, neighbour.x(), neighbour.y())) {
                neighbours.add(neighbour);
            }
        }
        Location randomNeighbour = neighbours.get(rng.nextInt(neighbours.size()));
        randomNeighbour.setGround(new FireGround());
        System.out.println("TeleportCircle ignited fire at (" +
                randomNeighbour.x() + "," + randomNeighbour.y() + ") on " + src.map().toString());
    }


    private boolean inBounds(GameMap map, int x, int y) {
        return x >= 0 && x <= map.getXRange().max() && y >= 0 && y <= map.getYRange().max();
    }

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
