package WorkStations;

import Zadanie3.Resources;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import static WorkStations.WorkStation.TERMINATE;

public class Warehouse extends AbstractActor {
    public static final String START = "start";
    private static final Resources resourcesToGiveToPressing = new Resources(15, 0, 0, 0, 0, 0, 0, 0);
    private static final Resources finalResources = new Resources(0, 0, 0, 0, 0, 0, 0, 1);
    private Resources resourcesAvailable;
    private ActorRef nextWorkStation = null;
    private Long time;

    public Warehouse(Resources resourcesAvailable) {
        this.resourcesAvailable = resourcesAvailable;
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(Resources.class, resources -> {
                    if (resources.hasResources(finalResources)) {
                        resourcesAvailable.addResources(resources);
                        System.out.println("Warehouse received final resources from " + sender().path().name());
                    } else if (resourcesAvailable.removeResources(resources)) {
                        sender().tell(resources, self());
                        System.out.println("Warehouse sent resources to " + sender().path().name());
                    } else {
                        System.out.println("Warehouse doesn't have enough resources to send to " + sender().path().name());
                        sender().tell(TERMINATE, ActorRef.noSender());
                    }
                })
                .match(ActorRef.class, workStation -> {
                    if (nextWorkStation == null) {
                        nextWorkStation = workStation;
                    }
                })
                .match(String.class, message -> {
                    if (message.equals(START)) {
                        run();
                    } else if (message.equals(TERMINATE)) {
                        terminate();
                    }
                })
                .build();
    }

    private void run() {
        time = System.nanoTime();

        if (nextWorkStation == null) {
            System.out.println("Warehouse has no next work station");
            terminate();
            return;
        }

        while (resourcesAvailable.removeResources(resourcesToGiveToPressing)) {
            nextWorkStation.tell(resourcesToGiveToPressing, ActorRef.noSender());
        }
        nextWorkStation.tell(TERMINATE, ActorRef.noSender());
    }

    private void terminate() {
        System.out.println("Bottles with wine in warehouse: " + resourcesAvailable.getWineBottles());
        System.out.println("Time: " + (System.nanoTime() - time) / 1000000 + "ms");
        System.out.println("\u001B[31mWarehouse terminated\u001B[0m");
        getContext().getSystem().terminate();
    }
}
