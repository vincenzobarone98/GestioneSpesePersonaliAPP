package gestioneSpese;

public class Categoria {
	private int idCategoria;
	private String nomeCategoria;
	private String descrizione;


	//Costruttore
	public Categoria() {}

	public Categoria(String nomeCategoria, String descrizione) {
		this.nomeCategoria = nomeCategoria;
		this.descrizione = descrizione;
	}
	
	public Categoria(int idCategoria, String nomeCategoria, String descrizione) {
		this.idCategoria = idCategoria;
		this.nomeCategoria = nomeCategoria;
		this.descrizione = descrizione;
	}

	//Metodi Getter
	public int getIdCategoria() {
		return idCategoria;
	}
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public String getDescrizione() {
		return descrizione;
	}

	//Metodi Setter
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}	
}
