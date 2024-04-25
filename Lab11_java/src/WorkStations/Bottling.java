package WorkStations;

import Zadanie3.Resources;
import akka.actor.ActorRef;

public class Bottling extends WorkStation {
    private static final int TIME_OF_PROCESSING = 1000 * 60 * 5;
    private static final int SLOTS_IN_TOTAL = 1;
    private static final double SUCCESS_CHANCE = 0.95;
    private static final Resources RESOURCES_REQUIRED = new Resources(0, 0, 0, 1, 0, 0, 0.75, 0);
    private static final Resources RESOURCES_REQUIRED_FROM_WAREHOUSE = new Resources(0, 0, 0, 1, 0, 0, 0, 0);
    private static final Resources RESOURCES_TO_GIVE = new Resources(0, 0, 0, 0, 0, 0, 0, 1);

    public Bottling(ActorRef warehouse, ActorRef nextWorkStation, double speedModifier) {
        super(warehouse, nextWorkStation, RESOURCES_REQUIRED, RESOURCES_REQUIRED_FROM_WAREHOUSE, RESOURCES_TO_GIVE, TIME_OF_PROCESSING, SLOTS_IN_TOTAL, SUCCESS_CHANCE, speedModifier);
    }
}
