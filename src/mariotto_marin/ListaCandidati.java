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
			// if (persona.equalsOrNull(p))
			r.add(persona);
		}
		return r;
	}

	public Candidato ricercaPerPosizione(int p) {
		return persone.get(p);
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

	public String vittoria() {
		Iterator<Candidato> it = persone.iterator();
		int voti = 0;
		String nome = null;
		while (it.hasNext()) {
			Candidato persona = it.next();
			if (persona.getVoti() > voti) {
				voti = persona.getVoti();
				nome = "Ha vinto le elezioni: " + persona.getNome() + " " + persona.getCognome() + " del "
						+ persona.getPartito() + " con " + voti + " voti";
			}
		}
		return nome;

	}

	public ArrayList<Candidato> getPersone() {
		return persone;
	}

	public String toString() {
		Iterator<Candidato> it = persone.iterator();
		String elenco = new String();
		while (it.hasNext()) {
			Candidato persona = it.next();
			// if (persona.equalsOrNull(p))
			elenco = elenco + " " + persona.toString();
		}
		return elenco;

	}
}
