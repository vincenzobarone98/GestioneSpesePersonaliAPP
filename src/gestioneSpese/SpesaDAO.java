package gestioneSpese;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class SpesaDAO {
	ResultSet rs;
	//METODI CRUD

	//CREATE ->AGGIUNGERE
	public void aggiungiSpesa(Spesa spesa) {
		String query = "INSERT INTO spese(nome, importo, data, id_categoria) VALUES(?, ?, ?, ?)";
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, spesa.getNomeSpesa());
			pstmt.setDouble(2,spesa.getImporto());
			pstmt.setDate(3, new java.sql.Date(spesa.getData().getTime()));
			pstmt.setInt(4, spesa.getIdCategoria());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	//READ -> VISUALIZZARE
	public List<Spesa> visualizzaAllSpese(){
		//System.out.println("sono dentro vis spese");
		List<Spesa> spese = new ArrayList<>();
		String query = "SELECT spese.id_spesa, spese.nome, spese.importo, spese.data, categorie.nome AS nome_categoria"
				+ " FROM spese"
				+ " JOIN categorie ON spese.id_categoria = categorie.id"
				+ " ORDER BY spese.data";
		try(Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)){
			while(rs.next()) {
				Spesa spesa = new Spesa();
				spesa.setIdSpesa(rs.getInt("id_spesa"));
				spesa.setNomeSpesa(rs.getString("nome"));
				spesa.setImporto(rs.getDouble("importo"));
				spesa.setData(rs.getDate("data"));
				spesa.setNome(rs.getString("nome_categoria"));
				

				spese.add(spesa);

			}		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return spese;
	}

	//UPDATE ->MODIFICA
	public void modificaSpesa(Spesa spesa) {
		String query = "UPDATE spese SET nome = ?, importo = ?, data = ?, id_categoria = ? WHERE id_spesa = ?";
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, spesa.getNomeSpesa());
			pstmt.setDouble(2,spesa.getImporto());
			pstmt.setDate(3, new java.sql.Date(spesa.getData().getTime()));
			pstmt.setInt(4, spesa.getIdCategoria());
			pstmt.setInt(5, spesa.getIdSpesa());
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	//DELETE -> ELIMINA
	public void eliminaSpesa(int idSpesa) {
		String query = "DELETE FROM spese WHERE id_spesa = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, idSpesa);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Spesa> cercaSpese(String searchTerm){
		List<Spesa> speseCerca = new ArrayList<>();
		String query = "SELECT spese.id_spesa, spese.nome, spese.importo, spese.data, categorie.id,  categorie.nome AS nome_categoria, categorie.descrizione "
				+ " FROM spese"
				+ " JOIN categorie ON spese.id_categoria = categorie.id"
				+ " WHERE categorie.nome LIKE ? OR spese.data LIKE ? ";
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)){
			String searchPattern = "%" + searchTerm + "%";
			pstmt.setString(1, searchPattern);
			pstmt.setString(2, searchPattern);
		
			rs = pstmt.executeQuery();

			while(rs.next()) {
				
				int idSpesa = rs.getInt("id_spesa");
				String nomeSpesa = rs.getString("nome");
				Date data = rs.getDate("data");
				double importo = rs.getDouble("importo");
				int idCategoria = rs.getInt("id");
				String nomeCategoria = rs.getString("nome_categoria");
				String descrizione = rs.getString("descrizione");
				
				Categoria categoria = new Categoria(nomeCategoria, descrizione);
				Spesa spesaCerca = new Spesa(idSpesa, nomeSpesa, data, importo, idCategoria, nomeCategoria, descrizione);
				spesaCerca.setCategoria(categoria);
				
				speseCerca.add(spesaCerca);
			}		
		}catch(SQLException e) {
			e.printStackTrace();
		}return speseCerca;
	}

}
