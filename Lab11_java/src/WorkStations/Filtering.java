package WorkStations;

import Zadanie3.Resources;
import akka.actor.ActorRef;

public class Filtering extends  WorkStation {
    private static final int TIME_OF_PROCESSING = 12 * 1000 * 3600;
    private static final int SLOTS_IN_TOTAL = 10;
    private static final double SUCCESS_CHANCE = 1.0;
    private static final Resources RESOURCES_REQUIRED = new Resources(0, 0, 0, 0, 0, 25, 0, 1);
    private static final Resources RESOURCES_REQUIRED_FROM_WAREHOUSE = null;
    private static final Resources RESOURCES_TO_GIVE = new Resources(0, 0, 0, 0, 0, 0, 24, 0);

    public Filtering(ActorRef warehouse, ActorRef nextWorkStation, double speedModifier) {
        super(warehouse, nextWorkStation, RESOURCES_REQUIRED, RESOURCES_REQUIRED_FROM_WAREHOUSE, RESOURCES_TO_GIVE, TIME_OF_PROCESSING, SLOTS_IN_TOTAL, SUCCESS_CHANCE, speedModifier);
    }
}
