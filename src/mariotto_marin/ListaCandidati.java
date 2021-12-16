package mariotto_marin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ListaCandidati implements Serializable {
    private ArrayList<Candidato> persone;

    public ListaCandidati() {
        persone = new ArrayList<>();
    }

    public void add(Candidato p) {
        persone.add(p);
    }

    public ListaCandidati ricerca(Candidato p) {
        ListaCandidati r = new ListaCandidati();
        Iterator<Candidato> it = persone.iterator();
        while (it.hasNext()) {
            Candidato persona = it.next();
            if (persona.equalsOrNull(p))
                r.add(persona);
        }
        return r;
    }

    public void modifica(Candidato vecchia, Candidato nuova) {
        for (int i = 0; i < persone.size(); i++) {
            if (persone.get(i).equals(vecchia)) {
                persone.set(i, nuova);
                break;
            }
        }
    }

    public void elimina(Candidato p) {
        for (int i = 0; i < persone.size(); i++) {
            if (persone.get(i).equals(p)) {
                persone.remove(i);
                break;
            }
        }
    }

    public ArrayList<Candidato> getPersone() {
        return persone;
    }
}
