package Zadanie3;

public class Resources {
    private double grapes;
    private double water;
    private double sugar;
    private int bottles;
    private double grapeJuice;
    private double unfilteredWine;
    private double filteredWine;
    private int wineBottles;

    public Resources(double grapes, double water, double sugar, int bottles, double grapeJuice, double unfilteredWine, double filteredWine, int wineBottles) {
        this.grapes = grapes;
        this.water = water;
        this.sugar = sugar;
        this.bottles = bottles;
        this.grapeJuice = grapeJuice;
        this.unfilteredWine = unfilteredWine;
        this.filteredWine = filteredWine;
        this.wineBottles = wineBottles;
    }

    public Resources() {
        this(0, 0, 0, 0, 0, 0, 0, 0);
    }

    public synchronized boolean removeResources(Resources amount){
        if (hasResources(amount)) {
            grapes -= amount.grapes;
            water -= amount.water;
            sugar -= amount.sugar;
            bottles -= amount.bottles;
            grapeJuice -= amount.grapeJuice;
            unfilteredWine -= amount.unfilteredWine;
            filteredWine -= amount.filteredWine;
            wineBottles -= amount.wineBottles;
            return true;
        }
        return false;
    }

    public void addResources(Resources amount) {
        Resources temp = new Resources();
        temp.grapes = -amount.grapes;
        temp.water = -amount.water;
        temp.sugar = -amount.sugar;
        temp.bottles = -amount.bottles;
        temp.grapeJuice = -amount.grapeJuice;
        temp.unfilteredWine = -amount.unfilteredWine;
        temp.filteredWine = -amount.filteredWine;
        temp.wineBottles = -amount.wineBottles;

        removeResources(temp);
    }

    public boolean hasResources(Resources amount){
        return grapes >= amount.grapes &&
                water >= amount.water &&
                sugar >= amount.sugar &&
                bottles >= amount.bottles &&
                grapeJuice >= amount.grapeJuice &&
                unfilteredWine >= amount.unfilteredWine &&
                filteredWine >= amount.filteredWine &&
                wineBottles >= amount.wineBottles;
    }

    public Resources sum (Resources amount){
        return new Resources(grapes + amount.grapes, water + amount.water, sugar + amount.sugar, bottles + amount.bottles, grapeJuice + amount.grapeJuice, unfilteredWine + amount.unfilteredWine, filteredWine + amount.filteredWine, wineBottles + amount.wineBottles);
    }

    public int getWineBottles() {
        return wineBottles;
    }
}
