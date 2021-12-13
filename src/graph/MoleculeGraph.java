package graph;
import javafx.util.Pair;
import structure.atomic.*;

import java.util.*;


public class MoleculeGraph {
    private HashMap<Atom, List<Atom>> adjList;

    public MoleculeGraph(List<Atom> atomList){
        this.adjList = new HashMap<Atom, List<Atom>>();

        for(Atom atom : atomList){ // Fills the adjacency list
            List<Atom> bondedAtoms = atom.getBondedAtoms(); // Grabs the list of other atoms bonded to the atom being accessed from the atomList
            adjList.put(atom, bondedAtoms); // Puts the accessed atom and the bonded atoms into the hashmap of adjacent atoms
        }
    }

    public void walkGraph(Atom startPoint){
        boolean active = true; // The active variable indicates whether there are still more atoms to look for in a given branch; if the end of the branch is reached, this becomes false
        int highestIndex = startPoint.getMolcIndex(); // This is used to ensure that no bonds are repeated in mapping the structure by disregarding any potential bonds that connect to atoms which has already had all their bonds mapped
        while(active) { // Continues looking so long as the atom has > 0 neighbors that have not been fully mapped
            List<Atom> bondedAtoms = adjList.get(startPoint);
            if(bondedAtoms.size() == 0){ // Ends search if the branch has no new neighbors
                active = false;
            }
            else if(bondedAtoms.size() > 1){ // Recursively maps each branch individually if there is more than one unmapped neighbor
                active = false;
                for(Atom endPoint : bondedAtoms){
                    if(endPoint.getMolcIndex() > highestIndex){
                        System.out.println(startPoint.getMolcIndex() + " " + endPoint.getMolcIndex());
                        walkGraph(endPoint);
                        highestIndex = endPoint.getMolcIndex();
                    }
                }
            }
            else {
                Atom endPoint = bondedAtoms.get(0);
                if (endPoint.getMolcIndex() < highestIndex) {
                    active = false;
                    break;
                }
                System.out.println(startPoint.getMolcIndex() + " " + endPoint.getMolcIndex());
                startPoint = endPoint;
            }
            System.out.println("END: " + startPoint.getMolcIndex() + " " + bondedAtoms.size());
        }

    }

    public static void main(String[] args){
        List<Atom> atomList = new ArrayList<Atom>(Arrays.asList(new Atom("C0", 0), new Atom("C1", 1), new Atom("C2", 2), new Atom("C3", 3), new Atom("C4", 4), new Atom("C5", 5), new Atom("C6", 6))); // Initializes a list of the atoms inside the sample molecule
        List<Pair<Integer, Integer>> connectivity = new ArrayList<>(Arrays.asList(new Pair<>(0, 1), new Pair<>(1, 2), new Pair<>(2, 3), new Pair<>(3, 4), new Pair<>(4, 5), new Pair<>(4, 6), new Pair<>(6, 0))); // Initializes a list of pairs indicating which indeces of the atom list connect with each other (i.e (1,2) means atom index 1 goes to atom index 2)
        for(Pair<Integer, Integer> bondPair : connectivity){ // Cycles through the bond pairs and modifies the bondPair list of each atom to include the new bond
            Atom startPoint = atomList.get(bondPair.getKey()); // Grabs the first of the two atoms in the connectivity pair
            Atom endPoint = atomList.get(bondPair.getValue()); // Grabs the latter atom in the connectivity pair
            Bond bond = new Bond("s", new Pair<>(startPoint, endPoint)); // Creates a bond connecting the two atoms
            startPoint.addBondPair(new Pair<>(bond, endPoint)); // Adds a bond to the endPoint in the startPoint atom object
            endPoint.addBondPair(new Pair<>(bond, startPoint)); // Adds a bond to the startPoint in the endPoint atom object
        }

        MoleculeGraph graph = new MoleculeGraph(atomList);
        graph.walkGraph(atomList.get(0)); // Walks the molecule graph starting at the first atom in the atom list
    }
}