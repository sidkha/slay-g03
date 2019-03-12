package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

public class Soldier extends MapElement implements Controlable {
    private int level;
    private boolean hasMoved = false;

    public Soldier(int maintenanceCost, int creationCost, Player owner, int level, boolean hasMoved) {
        super(maintenanceCost, creationCost, owner);
        this.level = level;
        this.hasMoved = hasMoved;
    }

    public boolean attack(Cell cellAttacker, Cell cellDefender) {
        int levelAttacker = cellAttacker.getElementOn().getLevel();
        int levelDefender = cellDefender.getElementOn().getLevel();


        if (levelAttacker != 3) {
            if (levelAttacker > levelDefender){
                cellDefender.setElementOn(null);
                return true;
            }
        }
        else if (levelDefender == 3){
            cellAttacker.setElementOn(null);
            cellDefender.setElementOn(null);

            return false;

        }

        else {
            cellDefender.setElementOn(null);
            return true;
        }
        return false;
    }

    public boolean merge(Cell himself,Cell allySoldier) {

        if ((this.level == allySoldier.getElementOn().getLevel()) && this.level != 3){

            Soldier upSoldier = null;

            switch (this.level){
                case 0: upSoldier = new Soldier(5,20,this.getOwner(),1,true); break;
                case 1: upSoldier = new Soldier(14,40,this.getOwner(),2,true); break;
                case 2: upSoldier = new Soldier(41,80,this.getOwner(),3,true); break;
            }

            allySoldier.setElementOn(upSoldier);
            himself.setElementOn(null);
            return true;
        }

        return false;

    }

    private void checkNewTerritory() {

    }
    @Override
    public int getLevel() {
        return level;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }


    @Override
    public boolean belongsTo() {
        return false;
    }

    @Override
    public void move(Cell cell) {
        /*if (cell.getElementOn().getClass() == Soldier.class) {
            attack((Soldier) cell.getElementOn());
        }*/
    }

    @Override
    public ArrayList<Cell> accessibleCell() {
        return null;
    }


    @Override
    public void select() {

    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Soldier)) {
            return false;
        }
        Soldier that = (Soldier) other;
        return this.getMaintenanceCost()==that.getMaintenanceCost()
                && this.getCreationCost() == that.getCreationCost()
                && this.getLevel() == that.getLevel()
                && this.getOwner().equals(that.getOwner())
                && this.isHasMoved() == that.isHasMoved();
    }
}
