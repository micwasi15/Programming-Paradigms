package WorkStations;

import Zadanie3.Resources;
import akka.actor.ActorRef;

public class Pressing extends WorkStation {
    private static final int TIME_OF_PROCESSING = 12 * 1000 * 3600;
    private static final int SLOTS_IN_TOTAL = 1;
    private static final double SUCCESS_CHANCE = 1.0;
    private static final Resources RESOURCES_REQUIRED = new Resources(15, 0, 0, 0, 0, 0, 0, 0);
    private static final Resources RESOURCES_REQUIRED_FROM_WAREHOUSE = null;
    private static final Resources RESOURCES_TO_GIVE = new Resources(0, 0, 0, 0, 10, 0, 0, 0);

    public Pressing(ActorRef warehouse, ActorRef nextWorkStation, double speedModifier) {
        super(warehouse, nextWorkStation, RESOURCES_REQUIRED, RESOURCES_REQUIRED_FROM_WAREHOUSE, RESOURCES_TO_GIVE, TIME_OF_PROCESSING, SLOTS_IN_TOTAL, SUCCESS_CHANCE, speedModifier);
    }
}
