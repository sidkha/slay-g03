package be.ac.umons.slay.g03.Core;

import java.util.ArrayList;

public class Territory {
    private int money;
    private ArrayList<Cell> cells;
    public int gain(){
        return 0;
    }
    public Territory(ArrayList<Cell> cells){
        this.cells = cells;
    }

    private int checkcost(){
        return 0;
    }
    private void bankrupt(){

    }
    public boolean checkSplit(ArrayList<Territory> territoryArray){
        return false;
    }
    public boolean checkMerge(ArrayList<Territory> territoryArray){
        return false;
    }
    public void split(){

    }
    public void merge(Territory territory){

    }
    public boolean addCell(Cell cell){
        return false;
    }
    public void addMoney(int gain){

    }


}