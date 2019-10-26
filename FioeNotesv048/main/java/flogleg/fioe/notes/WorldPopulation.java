package flogleg.fioe.notes;


public class WorldPopulation {
    private String title;

    public WorldPopulation(String country) {
        this.title = country;
    }

    public String getCountry() {
        return title;
    }

    public void setCountry(String country) {
        this.title = country;
    }


}