package structure.atomic;
import javafx.util.Pair;

public class Bond {
    private String bond_type;
    private Pair<Atom, Atom> bondedAtoms;

    public Bond(String bond_type, Pair<Atom, Atom> bondedAtoms){
        this.bond_type = bond_type;
        this.bondedAtoms = bondedAtoms;
    }

    public Pair<Atom, Atom> getBondedAtoms() {
        return bondedAtoms;
    }
}
