package utility;

import bc.*;

import java.util.ArrayList;
import java.util.Random;

public class BattleCodeUtils {
    public static Random random = new Random(6137);

    /**
     * Finds the karbonite closest to location of the worker
     *
     * @param unit gets the closest relative to this unit
     * @param planet the planet the unit is on
     * @param locations locations that are considered to be closest
     * @return this location is closest out of all locations
     */
    public static MapLocation findClosestKarbonite(Unit unit, ArrayList<MapLocation> locations, Planet planet) {
        MapLocation unitLocation = unit.location().mapLocation();
        MapLocation cloesesKarbonite = locations.get(0);
        int distance = (int) unitLocation.distanceSquaredTo(cloesesKarbonite);
        for (MapLocation loc : locations) {
            int newDistance = (int) unitLocation.distanceSquaredTo(loc);
            if (newDistance < distance) {
                distance = newDistance;
                cloesesKarbonite = loc;
            }
        }

        return cloesesKarbonite;
    }

    /**
     * A simple path-finding script that allows basic movement
     *
     * @param unit the unit you want to move
     * @param dest the location you want the unit to be
     */
    public static boolean dummyGoTo(Unit unit, MapLocation dest, GameController gc) {
        Direction dirToDest = unit.location().mapLocation().directionTo(dest);

        if (gc.canMove(unit.id(), dirToDest) && unit.movementHeat() == 0) {
            gc.moveRobot(unit.id(), dirToDest);
            return true;
        }
        if (unit.movementHeat() == 0) {
            for (Direction direction: Direction.values()) {
                if (dirToDest.compareTo(direction) == 1) {
                    if (gc.canMove(unit.id(), direction)) {
                        gc.moveRobot(unit.id(), direction);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * A simple path-finding script that allows basic movement
     *
     * @param unit the unit you want to move
     * @param dest the location you want the unit to be
     */
    public static boolean randomMove(Unit unit, GameController gc) {
        Direction randomDirection = Direction.values()[random.nextInt(7)];
        if (gc.canMove(unit.id(), randomDirection) && unit.movementHeat() == 0) {
            gc.moveRobot(unit.id(), randomDirection);
            return true;
        }
        if (unit.movementHeat() == 0) {
            for (Direction direction : Direction.values()) {
                if (direction.compareTo(direction) == 1) {
                    if (gc.canMove(unit.id(), direction)) {
                        gc.moveRobot(unit.id(), direction);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
