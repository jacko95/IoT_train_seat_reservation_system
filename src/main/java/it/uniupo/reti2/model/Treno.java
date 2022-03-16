package it.uniupo.reti2.model;

public class Treno {
	
	//compaiono tutti in localhost:4567...
	
	public int id; 
	
	public String stazionePartenza;
	
	public String orarioPartenza;
    
	public String dataPartenza;
	
	public String stazioneDestinazione;
	
	public String orarioArrivo;
	
	public String dataArrivo;
	
	public String durata;
	
	public String tariffa;
		
	public int posti;
	
	public int postiBici;
	
	public int occupati;
	
	public int occupatiBici;
	
	public int pieno;//1 se pieno completamente
	
	public int pienoBici;//1 se pieno solo per le bici
		
    public Treno(int id, String stazionePartenza, String orarioPartenza, String dataPartenza, String stazioneDestinazione, String orarioArrivo, String dataArrivo, String durata, String tariffa, int pieno, int pienoBici, int posti, int postiBici) {
		this.id = id;
    	this.stazionePartenza = stazionePartenza;
		this.orarioPartenza = orarioPartenza;
		this.dataPartenza = dataPartenza;
		this.stazioneDestinazione = stazioneDestinazione;
		this.orarioArrivo = orarioArrivo;
		this.dataArrivo = dataArrivo;
		this.durata = durata;
		this.tariffa = tariffa;
		this.pieno = pieno;
		this.pienoBici = pienoBici;
		this.posti = posti;
		this.postiBici = postiBici;
	}

	public String toString() {
		System.out.println("Treno delle " + orarioPartenza + " del " + dataPartenza + ":");
    	System.out.println("Stazione di destinazione: " + stazioneDestinazione);
    	System.out.println("Orario di arrivo: " + orarioArrivo);
    	System.out.println("Data di arrivo: " + dataArrivo);
    	System.out.println("Durata: " + durata);
    	System.out.println("Tariffa: " + tariffa);
        System.out.println("\n");
		return "Treno delle " + orarioPartenza + ":\n" + dataPartenza + stazioneDestinazione + orarioArrivo + orarioArrivo + durata + tariffa;
	}
}
