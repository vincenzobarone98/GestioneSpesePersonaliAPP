package gestioneSpese;

import java.util.Date;

public class Spesa {
	private int idSpesa;
	private String nomeSpesa;
	private Date data;
	private double importo;
	private int idCategoria;
	private String nome;
	private Categoria categoria;


	//Costruttore
	public Spesa() {}
	
	public Spesa(int idSpesa, String nomeSpesa, Date data, double importo, String nome) {
		this.idSpesa = idSpesa;
		this.nomeSpesa = nomeSpesa;
		this.data = data;
		this.importo = importo;
		//this.idCategoria = idCategoria;
		this.nome = nome;
	}
	
	public Spesa(int idSpesa, String nomeSpesa, Date data, double importo, int idCategoria, String nomeCategoria, String descrizioneCategoria) {
		this.idSpesa = idSpesa;
		this.nomeSpesa = nomeSpesa;
		this.data = data;
		this.importo = importo;
		this.idCategoria = idCategoria;
		this.categoria = new Categoria(nomeCategoria, descrizioneCategoria);
		
	}
	
	public Spesa(int idSpesa, String nomeSpesa, Date data, double importo, int idCategoria) {
		this.idSpesa = idSpesa;
		this.nomeSpesa = nomeSpesa;
		this.data = data;
		this.importo = importo;
		this.idCategoria = idCategoria;
	}

	public Spesa(String nomeSpesa, Date data, double importo, int idCategoria) {
		this.nomeSpesa = nomeSpesa;
		this.data = data;
		this.importo = importo;
		this.idCategoria = idCategoria;
	}

	//Metodi Setter
	public int getIdSpesa() {
		return idSpesa;
	}

	public String getNomeSpesa() {
		return nomeSpesa;
	}
	
	public Date getData() {
		return data;
	}

	public double getImporto() {
		return importo;
	}

	public int getIdCategoria() {
		return idCategoria;
	}
	public String getNome() {
		return nome;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	//Metodi Setter
	public void setIdSpesa(int idSpesa) {
		this.idSpesa = idSpesa;
	}

	public void setNomeSpesa(String nomeSpesa) {
		this.nomeSpesa = nomeSpesa;
	}
	public void setImporto(double importo) {
		this.importo = importo;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}



