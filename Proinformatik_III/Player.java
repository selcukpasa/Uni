// import the API.
// See xxx for the javadocs.
//import DirectionFunctions;
import bc.*;

import java.util.*;

import static utility.BattleCodeUtils.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Function;


/**
 * @author
 * Maynard Koch and Julian Hesse
 */
public class Player {
    public static class SingleUnitMoveStatus {
        boolean moveFree;
        long distance;
        Direction directionOfObstacle;
        MapLocation goal;

        /**
         * @implNote 
         * This Method is overloaded
         * Only -goal- and -distance- need to be set
         * If so, -moveFree- equals true and -directionOfObstacle- is set to null
         * @param goal
         * -goal- is a MapLocation where the unit shall move to.
         * @param distance
         * long -distance- contains the current distance to the goal
         * If -goal- is null -> distance have to be null too.
         */
        SingleUnitMoveStatus(MapLocation goal,long distance){
            this.moveFree=true;
            this.directionOfObstacle=null;
            this.distance=distance;
            this.goal=goal;
        }
        /**
         * @implNote
         * This Method is overloaded
         * Only -goal- and -distance- need to be set
         * If so, -moveFree- equals true and -directionOfObstacle- is set to null
         * @param free
         * boolean -free- is set to True if the unit can move freely
         * When initialised it's recommended to set free True
         * @param distance
         * long -distance- contains the current distance to the goal
         * If -goal- is null -> distance have to be null too.
         * @param directionOfObstacle
         * Contains the direction of the current obstacle the unit moves around.
         * If free is True -> directionOfObstacle have to be null.
         * @param goal
         * -goal- is a MapLocation where the unit shall move to.
         */
        SingleUnitMoveStatus(boolean free, long distance, Direction directionOfObstacle, MapLocation goal){
            this.moveFree=free;
            this.directionOfObstacle=directionOfObstacle;
            this.distance=distance;
            this.goal=goal;
        }
    }
    public static GameController gc = new GameController();
    public static Direction[] directions = Direction.values();

    // more deterministic randomness
    public static Random random = new Random(6137);

    public static Team myTeam;
    public static Team enemyTeam;

    public static PlanetMap EarthMap;
    public static short EarthHeight;
    public static short EarthWidth;
    public static PlanetMap MarsMap;
    public static short MarsHeight;
    public static short MarsWidth;

    public static boolean passableTerrain[][];
    public static int karboniteMap[][];
    public static ArrayList<MapLocation> karboniteLocations;
    public static ArrayList<MapLocation> startingKarbonite;
    public static char[][] dangerMap;

    public static ArrayList<MapLocation> enemyPositions;
    public static ArrayList<Unit> rockets;
    public static ArrayList<Unit> rangers;

    public static ArrayList<Unit> bluePrints = new ArrayList<Unit>();

    public static boolean colonizeMars;
    public static int produceHealers;

    public static ArrayList<Object> reachedKarbonite;

    private static Direction direction;
    public static HashMap<Integer, Object> UnitStatus= new HashMap<Integer, Object>();
    private static class UnitComparator implements Comparator<Unit> {

        @Override
        public int compare(Unit u1, Unit u2) {
            return 1;
        }
    }
    public static Comparator<Unit> unitComp = new UnitComparator();
    public static PriorityQueue<Unit> myArmada = new PriorityQueue<Unit>(5, unitComp);

    public static int rocketCount;
    public static int factoriesCount;
    public static int workerCount;
    public static int knightCount;
    public static int rangerCount;
    public static int mageCount;
    public static int healerCount;

    public static int maxFactories;


    public static void main(String[] args) {
        System.out.println("Java started");

        System.out.println(Arrays.toString(directions));

        // get team-info
        myTeam = gc.team();
        enemyTeam = (myTeam == Team.Red) ? Team.Blue : Team.Red;

        Planet planet = gc.planet();

        EarthMap = gc.startingMap(Planet.Earth);
        EarthHeight = (short) EarthMap.getHeight();
        EarthWidth = (short) EarthMap.getWidth();
        MarsMap = gc.startingMap(Planet.Mars);
        MarsHeight = (short) MarsMap.getHeight();
        MarsWidth = (short) MarsMap.getWidth();
        rockets=new ArrayList<Unit>();
        rangers=new ArrayList<Unit>();
        produceHealers=3;
        maxFactories = 4;

        if (planet == Planet.Earth) {
            gc.queueResearch(UnitType.Worker);
            gc.queueResearch(UnitType.Ranger);
            gc.queueResearch(UnitType.Healer);
            gc.queueResearch(UnitType.Rocket);
            //gc.queueResearch(UnitType.Factory);
            gc.queueResearch(UnitType.Healer);
            gc.queueResearch(UnitType.Ranger);
            gc.queueResearch(UnitType.Healer);
            gc.queueResearch(UnitType.Worker);
            gc.queueResearch(UnitType.Ranger);
            //gc.queueResearch(UnitType.Factory);
            gc.queueResearch(UnitType.Worker);
            gc.queueResearch(UnitType.Rocket);
            gc.queueResearch(UnitType.Rocket);
            //gc.queueResearch(UnitType.Factory);

            // get Map info
            initializeMap(EarthMap);

            // start with not colonizing Mars
            colonizeMars = false;

            VecUnit units;
            dangerMap = new char[EarthHeight][EarthWidth];

            for (int x = 0; x < EarthWidth; x++) for (int y = 0; y < EarthHeight; y++) {
                MapLocation current = new MapLocation(Planet.Earth, x, y);
                dangerMap[y][x] = 's';
                for (MapLocation enemy : enemyPositions) {
                    if (current.isWithinRange(7, enemy)) {
                        dangerMap[y][x] = 'd';
                    }
                }
            }

            /*for (char[] row : dangerMap) {
                System.out.println(Arrays.toString(row));
            }*/



            while (true) {
                //System.out.println("round: " + gc.round() + ", time left: " + gc.getTimeLeftMs() + "ms");
                //System.out.println("HashMap Groesse:"+UnitStatus.size());

                // start colonizing before water comes
                if (gc.round() > 500) {
                    colonizeMars = true;
                }

                // preventing ai from crashing
                try {
                    //System.out.println("RUNDE: "+gc.round());
                    units = gc.units();
                    // Gather data and preprocessing it to make smarter decisions
                    preTurn(units);

                    while (!myArmada.isEmpty()) {
                        //System.out.println("Runde: "+gc.round());
                        //System.out.println();
                        Unit unit = myArmada.remove();
                        //UnitStatus.clear();
                        if (!UnitStatus.containsKey(unit.id())){
                            UnitStatus.put(unit.id(), (Object) new SingleUnitMoveStatus(true,0,null,null));
                        }


                        switch (unit.unitType()) {
                            case Worker:
                                manageWorker(unit);
                                break;
                            case Knight:
                                manageKnight(unit);
                                break;
                            case Ranger:
                                manageRanger(unit);
                                break;
                            case Mage:
                                manageMage(unit);
                                break;
                            case Healer:
                                manageHealer(unit);
                                break;
                            case Factory:
                                manageFactory(unit);
                                break;
                            case Rocket:
                                manageRocket(unit);
                                break;
                        }
                        //System.out.println("ENDE SWITCH");
                    }
                    //System.out.println("ENDE WHILE: "+gc.round());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                catch (Error e) {
                    e.printStackTrace();
                }
                //System.out.println("ENDE RUNDE: "+gc.round());
                // submit changes and wait for next round
                //System.out.println("SENDE DATEN...");
                gc.nextTurn();
                //System.out.println("DATEN GESENDET!");
            }
        }
        else {
            // get Map info
            initializeMap(MarsMap);

            while (true) {
                System.out.println("round: " + gc.round() + ", time left: " + gc.getTimeLeftMs() + "ms");

                preTurn(gc.myUnits());
                // preventing ai from crashing
                try {
                    // ToDo: manage units on mars
                    while (!myArmada.isEmpty()) {
                        Unit unit = myArmada.remove();
                        if (!UnitStatus.containsKey(unit.id())){
                            UnitStatus.put(unit.id(), (Object) new SingleUnitMoveStatus(true,0,null,null));
                        }
                        switch (unit.unitType()) {
                            case Rocket:
                                manageMarsRocket(unit);
                                break;
                            case Ranger:
                                rangerCount++;
                                manageRanger(unit);
                                break;
                            case Worker:
                                workerCount++;
                                manageWorker(unit);
                                break;
                            case Healer:
                                healerCount++;
                                break;
                            case Knight:
                                knightCount++;
                                break;
                            case Mage:
                                mageCount++;
                                break;
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                catch (Error e) {
                    e.printStackTrace();
                }

                gc.nextTurn();
            }
        }

    }


    /**
     * Unload Units on Mars
     *
     * @param rocket a unit of UnitType Rocket
     */
    private static void manageMarsRocket(Unit rocket) {
        //System.out.println("Trying to unload...");
        if (rocket.location().isOnMap()) {
            System.out.println(rocket.structureGarrison());
            if (rocket.structureGarrison().size() > 0) {
                Direction dir=Direction.values()[random.nextInt(Direction.values().length)];
                if (gc.canUnload(rocket.id(), dir)) {
                    System.out.println("Trying to unload...");
                    gc.unload(rocket.id(), dir);
                    return;
                }
            }
        }
    }

    /**
     * Gets passable terrain and saves it in {@see passableTerrain}.
     * Gets starting position of the enemy and adds position to enemyPositions
     *
     * Runtime is height * width of the map
     *
     * @param planet Map of the planet you want intel about
     */
    private static void initializeMap(PlanetMap planet) {
        int height = (int) planet.getHeight();
        int width = (int) planet.getWidth();
        passableTerrain = new boolean[height][width];
        karboniteMap = new int[height][width];
        karboniteLocations = new ArrayList<MapLocation>();
        enemyPositions = new ArrayList<MapLocation>();
        startingKarbonite = new ArrayList<MapLocation>();
        for (int x = 0; x < width; x++) for (int y = 0; y < height; y++) {
                MapLocation loc = new MapLocation(planet.getPlanet(), x, y);
                passableTerrain[y][x] = planet.isPassableTerrainAt(loc) != 0;
                int karbAmount = (int) planet.initialKarboniteAt(loc);
                karboniteMap[y][x] = karbAmount;
                if (karbAmount > 0) {
                    karboniteLocations.add(loc);
                }
        }
        VecUnit startingUnits = planet.getInitial_units();
        enemyPositions = new ArrayList<MapLocation>();
        for (int i = 0; i < startingUnits.size(); i++){
            Unit unit = startingUnits.get(i);
            if (unit.team() == enemyTeam) {
                enemyPositions.add(unit.location().mapLocation());
            }
            if (unit.team() == myTeam) {
                MapLocation test = findClosestKarbonite(unit, karboniteLocations, planet.getPlanet());
                System.out.println("Karbonite x: " + test.getX() + ", y: " + test.getY());
                startingKarbonite.add(findClosestKarbonite(unit, karboniteLocations, planet.getPlanet()));
            }
        }

        for (MapLocation location : enemyPositions) {
            System.out.println("Enemy at x: " + location.getX() + ", y: " + location.getY());
        }

        // print passableTerrain
//      for (int x = 0; x < EarthWidth; x++) {
//          for (int y = 0; y < EarthHeight; y++) {
//              System.out.print(" " + (passableTerrain[y][x] ? 1 : 0) + " ");
//          }
//          System.out.print("\n");
//      }

//      for (int x = 0; x < EarthWidth; x++) {
//          for (int y = 0; y < EarthHeight; y++) {
//              System.out.print(" " + karboniteMap[y][x] + " ");
//          }
//          System.out.print("\n");
//      }
    }


    /**
     * Handles rocketlaunch and loading the rocket
     *
     * @param rocket a unit of type rocket
     */
    private static void manageRocket(Unit rocket) {
        if (rocket.structureIsBuilt() == 0) {
            return;
        }
        // Launch rocket in a specific round or if rocket is full
        boolean fullRocket = (rocket.structureGarrison().size() == rocket.structureMaxCapacity());
        if (gc.round() > 740 || fullRocket || gc.round()>=300 && gc.round()<=350) {
            Random random = new Random();
            MapLocation landing;
            //choose random location to land
            do {
                int x = random.nextInt(MarsWidth);
                int y = random.nextInt(MarsHeight);

                landing = new MapLocation(Planet.Mars, x, y);
            } while (MarsMap.isPassableTerrainAt(landing) !=1);

            if (gc.canLaunchRocket(rocket.id(), landing)) {
                System.out.println("Launching Rocket with " + rocket.structureGarrison().size() + " Units");
                System.out.println("Launching to: x->"+landing.getX()+" y->"+landing.getY());
                gc.launchRocket(rocket.id(), landing);
                rockets.remove(rocket);
                return;
            }

        }
        //If no rocket launched,try to load one
        if (gc.round() > 520 || gc.round()>=120 && gc.round()<=300) {
            int maxi=4;
            VecUnit loadableUnits = gc.senseNearbyUnitsByTeam(rocket.location().mapLocation(), 2, myTeam);
            for (int i = 0; i < loadableUnits.size(); i++) {
                Unit unit = loadableUnits.get(i);
                if (unit.unitType()!=UnitType.Worker || maxi==0) {
                    if (gc.canLoad(rocket.id(), unit.id())) {
                        gc.load(rocket.id(), unit.id());
                        maxi+=4;
                        return;
                    }
                }
                maxi--;
            }
        }
    }

    /**
     * Decides when to produce which rocket
     *
     * @param factory
     */
    private static void manageFactory(Unit factory) {
        //First -> Try to unload!
        for (Direction dir : directions) {
            if (gc.canUnload(factory.id(), dir)) {
                gc.unload(factory.id(), dir);
            }
        }
        //Build Units-> Ranger or Healer
        //On Healer per three Ranger
        boolean karboniteCondition = (gc.karbonite() > 200 + 40 || factoriesCount == maxFactories) && gc.karbonite() >= 40;
        boolean prepeareColonization = (gc.karbonite() > 150 + 40 || !colonizeMars);
        if (produceHealers>0) {
            if (gc.canProduceRobot(factory.id(), UnitType.Ranger)) {
                gc.produceRobot(factory.id(), UnitType.Ranger);
                produceHealers--;
                return;
            }
        }
        else {
            if (karboniteCondition && prepeareColonization && gc.canProduceRobot(factory.id(), UnitType.Healer)) {
                gc.produceRobot(factory.id(), UnitType.Healer);
                produceHealers=3;
                return;
            }
        }
    }


    /**
     *
     * @param healer
     */
    private static void manageHealer(Unit healer) {
        /*if (!rockets.isEmpty()){
            if (healer.movementHeat()==0){
                moveAction(healer,rockets.get(0).location().mapLocation());
                return;
            }
        }*/
        //Look for team mates
        VecUnit teamNearBy=gc.senseNearbyUnitsByTeam(healer.location().mapLocation(),50,myTeam);
        for (int i=0;i<teamNearBy.size();i++){
            //Found ranger? Try to heal!
            if(teamNearBy.get(i).unitType()==UnitType.Ranger && gc.canHeal(healer.id(),teamNearBy.get(i).id())){
                if(healer.attackHeat()==0){
                    if (healer.isAbilityUnlocked()==1 && gc.canOvercharge(healer.id(),teamNearBy.get(i).id())&&healer.abilityCooldown()==0){
                        gc.overcharge(healer.id(),teamNearBy.get(i).id());
                    }
                    else {
                        gc.heal(healer.id(), teamNearBy.get(i).id());
                    }
                    return;
                }
            }
            //Or move in the ranger direction
            else if(teamNearBy.get(i).unitType()==UnitType.Ranger){
                if(healer.movementHeat()==0){
                    moveAction(healer,teamNearBy.get(i).location().mapLocation());
                    return;
                }
            }
            //Found Worker? Try to heal!
            if(teamNearBy.get(i).unitType()==UnitType.Worker && gc.canHeal(healer.id(),teamNearBy.get(i).id())){
                if(healer.attackHeat()==0){
                    gc.heal(healer.id(),teamNearBy.get(i).id());
                    return;
                }
            }
        }
        //Don't just stand there, operate!
        if(healer.movementHeat()==0){
            randomMove(healer,gc);
            return;
        }
        //If None of the actions before have took place -> search for nearby rockets
        for (Unit rocket:rockets){
            if (gc.canSenseUnit(rocket.id()) && healer.movementHeat()==0){
                moveAction(healer,rocket.location().mapLocation());
                return;
            }
        }
    }

    /**
     *
     * @param mage
     */
    private static void manageMage(Unit mage) {
        if(mage.movementHeat()==0){
            randomMove(mage,gc);
        }
    }

    /**
     *
     * @param ranger
     */
    private static void manageRanger(Unit ranger) {
        //If there are rockets on the map-> try to go to these ones so they can get loaded
        if (!rockets.isEmpty()){
            if (ranger.movementHeat()==0){
                moveAction(ranger,rockets.get(random.nextInt(rockets.size())).location().mapLocation());
                return;
            }
        }
        //Otherwise scan for enemies and...
        VecUnit enemys = gc.senseNearbyUnitsByTeam(ranger.location().mapLocation(), 70 , enemyTeam);
        for (int i = 0; i < enemys.size(); i++) {
            //...attack them or...
            if (gc.canAttack(ranger.id(), enemys.get(i).id())) {
                if (ranger.attackHeat() == 0) {
                    gc.attack(ranger.id(), enemys.get(i).id());
                }
                return;
            }
            //...move in the enemy direction
            if (gc.canSenseUnit(enemys.get(i).id()) && ranger.movementHeat()==0) {
                moveAction(ranger,enemys.get(i).location().mapLocation());
                return;
            }
        }
        //Look again for rockets, but only for nearby
        for (Unit rocket:rockets){
            if (gc.canSenseUnit(rocket.id()) && ranger.movementHeat()==0){
                moveAction(ranger,rocket.location().mapLocation());
                return;
            }
        }
        //Move to enemy or...
        if (ranger.movementHeat()==0 && !enemyPositions.isEmpty()) {
            moveAction(ranger, enemyPositions.get(random.nextInt(enemyPositions.size())));
        }
        //...build a formation by following other rangers
        else if(ranger.movementHeat()==0){
            MapLocation nextRanger=rangers.get(random.nextInt(rangers.size())).location().mapLocation();
            moveAction(ranger,nextRanger);
        }
    }

    /**
     *
     * @param knight
     */
    private static void manageKnight(Unit knight) {
    }

    /**
     * @implSpec
     * manageWorker do manage a single action for every worker
     * per turn.
     * @param worker
     *
     */
    private static void manageWorker(Unit worker) {
        //System.out.println("Worker, id: " + worker.id() + ", health: " + worker.health());
        int maxWorkers = 7;
        boolean shouldReplicate = false;

        //First: Try to build blueprints
        for (Unit bluePrint : bluePrints ) {
            if (gc.canBuild(worker.id(), bluePrint.id())) {
                gc.build(worker.id(), bluePrint.id());
                return;
            }
            if (bluePrint.structureIsBuilt() == 1) {
                bluePrints.remove(bluePrint);
                if (bluePrint.unitType()==UnitType.Rocket){
                    rockets.add(bluePrint);
                }
            }
        }
        //If it's time for rockets -> lay down blueprints for them
        if (colonizeMars && rocketCount <= 4 || gc.round()>=130 &&gc.round()<=180) {
            for (Direction dir : directions) {
                if (gc.canBlueprint(worker.id(), UnitType.Rocket, dir) && gc.karbonite() > 150) {
                    gc.blueprint(worker.id(), UnitType.Rocket, dir);
                    rocketCount++;
                    return;
                }
            }
        }
        //Some karbonite left on the map -> go and find it!
        if (!startingKarbonite.isEmpty()) {
            MapLocation closest = findClosestKarbonite(worker, startingKarbonite, gc.planet());
            if (worker.location().mapLocation().equals(closest)) {
                System.out.println("I am at my destination!!!");
                startingKarbonite.remove(closest);
            }
            if (worker.movementHeat()==0) {
                moveAction(worker, closest);
            }
        }

        // conditions for replicating workers
        if (workerCount < maxWorkers && gc.karbonite() > 60) {
            shouldReplicate = true;
        }

        // conditions for factory blueprints:
        // condition to make sure we got workers and have enough karbonite
        boolean condition = workerCount >= 3 || gc.karbonite() > 200;
        // condition2 to prevent making factories when there are no fighters
        int fighters = knightCount + mageCount + rangerCount;
        //If factories are needed -> lay down blueprints
        boolean condition2 = factoriesCount == 0 || fighters / factoriesCount <= 3 || gc.karbonite() > 260;
        if (condition && factoriesCount < 3 && condition2) {
            for (int i = 0; i < 8; i += 2) {
                if (gc.canBlueprint(worker.id(), UnitType.Factory, directions[i])) {
                    boolean preventBlocking = gc.canBlueprint(worker.id(), UnitType.Factory, directions[i == 0 ? 7 : i-1]) || gc.canBlueprint(worker.id(), UnitType.Factory, directions[i == 7 ? 0 : i+1]);
                    if (preventBlocking) {
                        gc.blueprint(worker.id(), UnitType.Factory, directions[i]);
                        factoriesCount++;
                        return;
                    }
                }
            }
        }
        //Not enough worker yet -> replicate
        if (shouldReplicate) {
            for (int i = 0; i < 8; i++) {
                if (gc.canReplicate(worker.id(), directions[i])) {
                    gc.replicate(worker.id(), directions[i]);
                    return;
                }
            }
        }
        //If None of the actions before have took place, try to harvest karbonite...
        for (Direction dir : directions) {
            if (gc.canHarvest(worker.id(), dir)) {
                gc.harvest(worker.id(), dir);
                return;
            }
        }
        //or move in direction of next Ranger -> it's better if rockets are build near to rangers
        if (worker.movementHeat()==0) {
            if(!rangers.isEmpty()){
                MapLocation nextRanger=rangers.get(random.nextInt(rangers.size())).location().mapLocation();
                moveAction(worker,nextRanger);
            }
            else{
                randomMove(worker,gc);
            }
        }

    }


    /**
     * Pre Turn counts my units, to make more informed decisions.
     * And it adds all units to a PriorityQueue
     *
     * @param myUnits Needs all units of my team
     */
    private static void preTurn(VecUnit myUnits) {
        myArmada.clear();

        workerCount = 0;
        knightCount = 0;
        mageCount = 0;
        rangerCount = 0;
        healerCount = 0;
        factoriesCount = 0;
        rocketCount = 0;

        rangers.clear();
        for (int i = 0; i < myUnits.size(); i++) {
            Unit unit = myUnits.get(i);
            if (unit.location().isOnMap()) {
                switch (unit.unitType()) {
                    case Worker:
                        workerCount++;
                        break;
                    case Mage:
                        mageCount++;
                        break;
                    case Knight:
                        knightCount++;
                        break;
                    case Ranger:
                        rangerCount++;
                        if(!rangers.contains(unit)){
                            rangers.add(unit);
                        }
                        break;
                    case Healer:
                        healerCount++;
                        break;
                    case Factory:
                        factoriesCount++;
                        if (unit.structureIsBuilt() == 0) {
                            bluePrints.add(unit);
                        }
                        break;
                    case Rocket:
                        rocketCount++;
                        if (unit.structureIsBuilt() == 0) {
                            bluePrints.add(unit);
                        }
                        break;
                }
                myArmada.add(myUnits.get(i));
            }
        }
        /*
        To scan range of unit:
        gc.senseNearbyUnits(unit.location(),unit.attackRange());
        gc.senseNearbyUnitsByTeam(unit.location(),unit.attackRange(),myTeam);
        gc.senseNearbyUnitsByTeam(unit.location(),unit.attackRange(),enemyTeam);
        gc.senseNearbyUnitsByType(unit.location(),unit.attackRange(),$type$);
         */
    }
    /**
     * @param unit
     *     -unit- is of type Unit and a robot, not
     *     a structure
     * @param directionFunction
     *     -directionFunction- is a method with input variable
     *     of type Unit and returns an Integer
     * @return
     *     A direction of type Direction is returned
     *
     * @implSpec
     * The function shall choose smartly a direction
     * which fits best for the Unit -unit- to go to next
     * @// TODO: 25.09.18 Improve Moving -> choose smart direction
     */
    private static Direction ChooseDirection(Unit unit, Function <Unit,Integer> directionFunction){
        //ToDo: Improve Moving -> Choose smart direction
        int pick=directionFunction.apply(unit);
        return Direction.values()[pick];
    }

    /**
     *
     * @param unit
     * -unit- is of type Unit and a robot, not
     * a structure.
     * @implNote
     *     If all conditions are evaluated true,
     *     the robot is moved into direction -direction-
     * @implSpec
     * The function is a general move function.
     * You pass in a robot and if it is ready to move
     * (cooldown rate <10, choosen direction is passable)
     * the robot is moved into the choosen direction.
     */
    private static void moveAction(Unit unit,MapLocation goal){
        //System.out.println(unit.id()+" versucht nach "+goal+" zu gehen!");
        if (gc.isMoveReady(unit.id())){
            Direction direction = bug0Direction(unit,goal);//ChooseDirection(unit, goal, DirectionFunctions::randomDirection);
            if (gc.canMove(unit.id(),direction)){
                gc.moveRobot(unit.id(),direction);
                //System.out.println("UNIT "+unit.id()+" MOVED TO "+direction);
            }
        }
    }

    /**
     * 
     * @param unit
     * -unit- is of type Unit but the unit types rocket and factory are not allowed
     * @param goal
     * -goal- is a valid MapLocation where the unit -unit- shall move to
     * @return
     * A direction of type Direction is returned, the direction is the next possible
     * direction to move towards the goal. If SingleUnitMoveStatus.moveFree of the Unit
     * -unit- is False, then the next direction to move around the obstacle is returned.
     * If -goal- is null -> Direction.Center is returned.
     */
    public static Direction bug0Direction(Unit unit, MapLocation goal){
        //System.out.println("ZIEL:"+goal);
        if (goal!=null){
            MapLocation current=unit.location().mapLocation();
            if(current.equals(goal)){
                System.out.println("Unit "+unit.id()+" hat als erster das Ziel erreicht!");
                return Direction.values()[random.nextInt(Direction.values().length)];
            }
            Direction direction=current.directionTo(goal);
            MapLocation nextLoc;
            SingleUnitMoveStatus result=(SingleUnitMoveStatus) UnitStatus.get(unit.id());
            /*System.out.println("Abstand Start-Ziel: "+result.distance);
            System.out.println("Ziel: "+goal);
            System.out.println("Aktuelle Pos.: "+current);*/
            System.out.println(result.moveFree);
            if (result.moveFree){
                nextLoc=current.add(direction);
                if ((gc.isOccupiable(nextLoc))==1){
                    return direction;
                }
                else{
                    //System.out.println("ENDLOS! Unit: "+unit.id());
                    SingleUnitMoveStatus unitStatus=(SingleUnitMoveStatus) UnitStatus.get(unit.id());
                    /*System.out.println("Vorher Hindernis: "+unitStatus.directionOfObstacle);
                    System.out.println("Ziel: "+unitStatus.goal);
                    System.out.println("Abstand: "+unitStatus.distance);
                    System.out.println("Bis hier moveFree: "+unitStatus.moveFree);*/
                    unitStatus.moveFree=false;
                    unitStatus.directionOfObstacle=direction;
                    unitStatus.goal=goal;
                    unitStatus.distance=current.distanceSquaredTo(goal);
                    /*System.out.println("Nachher Hindernis: "+unitStatus.directionOfObstacle);
                    System.out.println("Ziel: "+unitStatus.goal);
                    System.out.println("Abstand: "+unitStatus.distance);
                    System.out.println("bisHier moveFree: "+unitStatus.moveFree);*/
                    UnitStatus.put(unit.id(),unitStatus);
                    SingleUnitMoveStatus test=(SingleUnitMoveStatus) UnitStatus.get(unit.id());
                    /*System.out.println("Hindernis: "+test.directionOfObstacle);
                    System.out.println("Ziel: "+test.goal);
                    System.out.println("Abstand: "+test.distance);
                    System.out.println("moveFree: "+test.moveFree);*/
                    return bug0Direction(unit,goal);
                }
            }
            else{
                SingleUnitMoveStatus unitStatus=(SingleUnitMoveStatus) UnitStatus.get(unit.id());
                direction=unitStatus.directionOfObstacle;
                nextLoc=current.add(direction);
                boolean onMap = gc.planet() == Planet.Earth ? nextLoc.getX() < EarthWidth && nextLoc.getY() < EarthHeight : nextLoc.getX() < MarsWidth && nextLoc.getY() < MarsHeight;
                boolean biggerThan0 = nextLoc.getX() >= 0 && nextLoc.getY() >= 0;
                if (gc.canSenseLocation(nextLoc)) {
                    if ((gc.isOccupiable(nextLoc)) == 1 && biggerThan0) {
                        if (unitStatus.distance < current.distanceSquaredTo(goal)) {
                            unitStatus.moveFree = true;
                            UnitStatus.put(unit.id(), unitStatus);
                        } else {
                            int turn = computeTurn(direction);
                            unitStatus.directionOfObstacle = getNextDirection(getNextDirection(getNextDirection(direction, turn), turn), turn);
                            UnitStatus.put(unit.id(), unitStatus);
                        }
                        return direction;
                    } else {
                        Direction obstacle = direction;
                        int maxLoop = Direction.values().length;
                        while (maxLoop > 0) {
                            nextLoc.subtract(direction);
                            //System.out.println("OUUUUUUUHHH");
                            direction = getNextDirection(direction, 0);
                            nextLoc = current.add(direction);
                            //System.out.println("Besetzbar: "+gc.isOccupiable(nextLoc));
                            if (gc.canSenseLocation(nextLoc)) {
                                if (gc.isOccupiable(nextLoc) != 1) obstacle = direction;
                                else {
                                    break;
                                }
                            }
                            maxLoop--;
                        }
                        unitStatus.directionOfObstacle = obstacle;
                        UnitStatus.put(unit.id(), unitStatus);
                        if (maxLoop == 0) {
                            return Direction.Center;
                        }
                        return direction;
                    }
                }
                else{
                    return Direction.values()[random.nextInt(Direction.values().length)];
                }
            }
        }
        return Direction.values()[random.nextInt(Direction.values().length)];
    }

    /**
     *
     * @param direction
     * -direction- is of type Direction
     * @return
     * The Integer 1 is returned if -direction- was a direction between North - East - South
     * Otherwise 0
     */
    private static int computeTurn(Direction direction){
        switch(direction){
            case North:
            case Northeast:
            case East:
            case Southeast:
            case South:
                return 1;
            default: return 0;
        }
    }

    /**
     *
     * @param direction
     * -direction- is of type Direction
     * @param TURN
     * -TURN- is an Integer and is set to 0 or 1.
     * @return
     * If -TURN- is set to 0 , the next direction (having a compass in mind)
     * turned one direction left to the given direction -direction- is returned.
     * Otherwise the next direction turned one direction right to the given
     * direction -direction- is returned.
     */
    private static Direction getNextDirection(Direction direction,int TURN){
        switch (direction){
            case North:
                if (TURN==0) return Direction.Northwest;
                else return Direction.Northeast;
            case East:
                if (TURN==0) return Direction.Northeast;
                else return Direction.Southeast;
            case South:
                if (TURN==0) return Direction.Southeast;
                else return Direction.Southwest;
            case West:
                if (TURN==0) return Direction.Southwest;
                else return Direction.Northwest;
            case Northeast:
                if (TURN==0) return Direction.North;
                else return Direction.East;
            case Southeast:
                if (TURN==0) return Direction.East;
                else return Direction.South;
            case Northwest:
                if (TURN==0) return Direction.West;
                else return Direction.North;
            case Southwest:
                if (TURN==0) return Direction.South;
                else return Direction.West;
            default: return Direction.North;
        }
    }
}
