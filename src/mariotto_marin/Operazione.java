package mariotto_marin;

import java.io.Serializable;

public class Operazione implements Serializable {

    public static enum Operazione_t {
        Vota,
        Inserisci,
        Ricerca,
        Op_ACK,
        Op_NACK
    }
	
	/*private Operazione_t op;
	
	public Operazione(Operazione_t op) {
		this.op=op;
	}*/
}
