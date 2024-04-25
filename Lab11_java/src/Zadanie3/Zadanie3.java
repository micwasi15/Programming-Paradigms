package Zadanie3;

import WorkStations.Warehouse;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Zadanie3 {
    private final double speedModifier;
    private final Resources startingResources = new Resources(100, 100, 100, 150, 0, 0, 0, 0);

    public Zadanie3(double speedModifier) {
        this.speedModifier = speedModifier;
    }

    public void run() {
        ActorSystem system = ActorSystem.create("Zadanie3");

        ActorRef warehouse = system.actorOf(Props.create(Warehouse.class, startingResources), "warehouse");

        ActorRef bottling = system.actorOf(Props.create(WorkStations.Bottling.class, warehouse, warehouse, speedModifier), "bottling");
        ActorRef filtering = system.actorOf(Props.create(WorkStations.Filtering.class, warehouse, bottling, speedModifier), "filtering");
        ActorRef fermentation = system.actorOf(Props.create(WorkStations.Fermentation.class, warehouse, filtering, speedModifier), "fermentation");
        ActorRef pressing = system.actorOf(Props.create(WorkStations.Pressing.class, warehouse, fermentation, speedModifier), "pressing");

        warehouse.tell(pressing, ActorRef.noSender());
        warehouse.tell(Warehouse.START, ActorRef.noSender());
    }
}
