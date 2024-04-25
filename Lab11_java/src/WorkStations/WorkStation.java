package WorkStations;

import Zadanie3.Resources;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.util.Random;

public abstract class WorkStation extends AbstractActor {
    public static final String TERMINATE = "terminate";

    private final ActorRef warehouse;
    private final ActorRef nextWorkStation;
    private Resources resourcesAvailable;
    private final Resources resourcesRequired;
    private final Resources resourcesRequiredFromWarehouse;
    private final Resources resourcesToGive;
    private int timeOfProcessing;
    private int slotsAvailable;
    private final int slotsInTotal;
    private final double successChance;
    private boolean terminate = false;

    public WorkStation(ActorRef warehouse, ActorRef nextWorkStation, Resources resourcesRequired, Resources resourcesRequiredFromWarehouse, Resources resourcesToGive, int timeOfProcessing, int slotsInTotal, double successChance, double speedModifier) {
        this.warehouse = warehouse;
        this.nextWorkStation = nextWorkStation;
        this.resourcesAvailable = new Resources();
        this.resourcesRequired = resourcesRequired;
        this.resourcesRequiredFromWarehouse = resourcesRequiredFromWarehouse;
        this.resourcesToGive = resourcesToGive;
        this.timeOfProcessing = (int) (timeOfProcessing / speedModifier);
        this.slotsAvailable = slotsInTotal;
        this.slotsInTotal = slotsInTotal;
        this.successChance = successChance;
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(Resources.class, resources -> {
                    resourcesAvailable.addResources(resources);
                    if (resourcesAvailable.hasResources(resourcesRequired) && changeSlotsAvailable(-1)) {
                        //resourcesAvailable.removeResources(resourcesRequired);
                        getContext().getSystem().dispatcher().execute(this::startProcessing);
                    } else if (slotsAvailable > 0) {
                        requestResourcesFromWarehouseIfNeeded();
                    }
                })
                .match(String.class, message -> {
                    if (message.equals(TERMINATE)) {
                        terminate = true;
                        terminateIfNoRes();
                    }
                })
                .build();
    }

    public void startProcessing() {
        if (!resourcesAvailable.removeResources(resourcesRequired)) {
            changeSlotsAvailable(1);
            return;
        }

        if (resourcesAvailable.hasResources(resourcesRequired) && changeSlotsAvailable(-1)) {
            getContext().getSystem().dispatcher().execute(this::startProcessing);
        }

        try {
            Thread.sleep(timeOfProcessing);
        } catch (InterruptedException ignored) {
        }
        Random random = new Random();
        System.out.print("Workstation " + self().path().name() + " processed resources");
        if (random.nextDouble() <= successChance) {
            nextWorkStation.tell(resourcesToGive, self());
            System.out.print(" and sent them to " + nextWorkStation.path().name());
        }
        System.out.println();

        requestResourcesFromWarehouseIfNeeded();
        startProcessing();
    }

    private void terminateIfNoRes() {
        if (terminate && slotsAvailable == slotsInTotal && !needResourcesFromWarehouse()) {
            System.out.println("\u001B[31mWorkstation " + self().path().name() + " finished work\u001B[0m");
            nextWorkStation.tell(TERMINATE, ActorRef.noSender());
        }
    }

    private synchronized boolean changeSlotsAvailable(int value) {
        slotsAvailable += value;
        //System.out.println("\u001B[32mWorkstation " + self().path().name() + " has " + slotsAvailable + " slots available\u001B[0m");

        terminateIfNoRes();

        if (slotsAvailable < 0) {
            slotsAvailable = 0;
            return false;
        }
        return true;
    }

    private void requestResourcesFromWarehouseIfNeeded() {
        if (needResourcesFromWarehouse()) {
            warehouse.tell(resourcesRequiredFromWarehouse, self());
            System.out.println("Workstation " + self().path().name() + " requested resources from warehouse");
        }
    }

    private boolean needResourcesFromWarehouse() {
        return resourcesRequiredFromWarehouse != null && resourcesAvailable.sum(resourcesRequiredFromWarehouse).hasResources(resourcesRequired);
    }
}
