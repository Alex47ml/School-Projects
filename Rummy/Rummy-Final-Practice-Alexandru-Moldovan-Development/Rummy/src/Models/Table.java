package Models;

import CombinationModels.Combination;

import java.util.ArrayList;

public class Table {
    private ArrayList<Combination> table = new ArrayList<Combination>();

    public void addCombination(Combination comb){
        table.add(comb);
    }

    public ArrayList<Combination> getTable() {
        return table;
    }
}
