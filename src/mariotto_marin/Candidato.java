package mariotto_marin;

import java.io.Serializable;

public class Candidato implements Serializable {

    private String nome;
    private String cognome;
    private String partito;
    private int voti;


    public Candidato(String nome, String cognome, String partito, int voti) {
        this.nome = nome;
        this.cognome = cognome;
        this.partito = partito;
        this.voti = voti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPartito() {
        return partito;
    }

    public void setPartito(String partito) {
        this.partito = partito;
    }
    public void setVoti(int voti) {
		this.voti = voti;
	}
    public int getVoti() {
		return voti;
	}
    public void addVoto() {
		this.voti += 1;
	}
    public boolean equals(Candidato p) {
        if (this.nome == p.nome)
            if (this.cognome == p.cognome)
                if (this.partito == p.partito)
                        return true;
        return false;
    }

    public boolean equalsOrNull(Candidato p) {
        if ((this.nome == p.nome) || (p.nome == null))
            if ((this.cognome == p.cognome) || (p.cognome == null))
                if ((this.partito == p.partito) || (p.partito == null))
                        return true;
        return false;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + ", " + partito +" "+ voti;
    }
    
    public String toStringVotazioni() {
        return nome + " " + cognome + ", " + partito + " " + voti;
    }
    
}
