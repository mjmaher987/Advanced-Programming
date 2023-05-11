import java.util.ArrayList;

public class MapHome {

    private String username;
    private int shipCodeNumber;
    private int shipTypeNumber;
    private boolean isInvisible;
    private boolean doWeHaveShip;
    private boolean doWeHaveMine;
    private boolean destroyedMine;
    private boolean destroyedShip;
    private boolean completeShipDestroyed;
    private boolean bombInSea;
    private ArrayList<Integer> antiAirCrafts;

    public MapHome(String username) {
        this.username = username;
        this.shipCodeNumber = 0;
        this.shipTypeNumber = 0;
        this.isInvisible = false;
        this.doWeHaveMine = false;
        this.doWeHaveShip = false;
        this.destroyedMine = false;
        this.destroyedShip = false;
        this.completeShipDestroyed = false;
        this.bombInSea = false;
        antiAirCrafts = new ArrayList<>();
    }

    public int getAntiAirCraftNumber() {
        return antiAirCrafts.get(0);
    }

    public int getShipCodeNumber() {
        return this.shipCodeNumber;
    }

    public int getShipTypeNumber() {
        return this.shipTypeNumber;
    }

    public void setShipCodeNumber(int shipNumber) {
        this.shipCodeNumber = shipNumber;
        this.doWeHaveShip = true;
    }

    public void setShipTypeNumber(int typeNumber) {
        this.shipTypeNumber = typeNumber;
    }

    public void setMine() {
        this.doWeHaveMine = true;
    }

    public void setBombInSea() {
        this.bombInSea = true;
    }

    public void setCompleteShipDestroyed() {
        this.completeShipDestroyed = true;
    }

    public void setDestroyedShip() {
        this.destroyedShip = true;
    }

    public void setDestroyedMine() {
        this.destroyedMine = true;
    }

    public void addAirCraft(int aaCode) {
        antiAirCrafts.add(aaCode);
    }

    public void makeInvisible() {
        this.isInvisible = true;
    }

    public void removeThisAntiAirCraft(int code) {
        for (int i = 0; i < antiAirCrafts.size(); i++) {
            if (antiAirCrafts.get(i) == code) antiAirCrafts.remove(i);
        }
    }

    public boolean doesAnyAirCraftsExists() {
        return antiAirCrafts.size() > 0;
    }

    public boolean isBombInSea() {
        return this.bombInSea;
    }

    public boolean isCompleteShipDestroyed() {
        return this.completeShipDestroyed;
    }

    public boolean isItFree() {
        if (doWeHaveShip) return false;
        if (doWeHaveMine) return false;
        return true;
    }

    public boolean doesItHaveAnyShip() {
        return doWeHaveShip;
    }

    public boolean doesItHaveAnyMine() {
        return doWeHaveMine;
    }

    public boolean isItVisible() {
        return !isInvisible;
    }

    public boolean destroyedBefore() {
        if (destroyedShip) return true;
        if (destroyedMine) return true;
        if (bombInSea) return true;
        return false;
    }

}
