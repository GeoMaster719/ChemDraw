package structure.atomic;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Atom {
    private String element;
    private List<Pair<Bond, Atom>> bondPairs;
    private int molcIndex;

    public Atom(String element, int molcIndex){
        this.element = element;
        this.bondPairs = new ArrayList<Pair<Bond, Atom>>();
        this.molcIndex = molcIndex;
    }

    /*
    public Atom(String element, List<Pair<Bond, Atom>> bondPairs){
        this.element = element;
        this.bondPairs = bondPairs;
    }
    */

    public void addBondPair(Pair<Bond, Atom> pair){
        this.bondPairs.add(pair);
    }

    public List<Pair<Bond, Atom>> getBondPairs(){
        return bondPairs;
    }

    public String getElement(){
        return element;
    }

    public int getMolcIndex(){
        return molcIndex;
    }

    public List<Atom> getBondedAtoms(){
        List<Atom> bondedAtoms = new ArrayList<Atom>();
        for(Pair<Bond, Atom> pair : bondPairs){
            bondedAtoms.add(pair.getValue());
        }
        return bondedAtoms;
    }
}
