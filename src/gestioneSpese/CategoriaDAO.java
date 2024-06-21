package gestioneSpese;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
	
	public List<Categoria> getAllCategorie() {
	    List<Categoria> categorie = new ArrayList<>();
	    try (Connection conn = DatabaseConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT * FROM categorie")) {

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String nome = rs.getString("nome");
	            String descrizione = rs.getString("descrizione");
	            categorie.add(new Categoria(id, nome, descrizione));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return categorie;
	}

	//CREATE ->AGGIUNGI
	public void aggiungiCategoria(Categoria categoria) {
		String query = "INSERT INTO categorie(nome, descrizione) VALUES (?, ?)";
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, categoria.getNomeCategoria());
			stmt.setString(2, categoria.getDescrizione());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//READ ->AGGIORNA
	public List<Categoria> visualizzaAllCategorie() {
		List<Categoria> categorie = new ArrayList<>();
		String query = "SELECT * FROM categorie";
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Categoria categoria = new Categoria();
				categoria.setIdCategoria(rs.getInt("id"));
				categoria.setNomeCategoria(rs.getString("nome"));
				categoria.setDescrizione(rs.getString("descrizione"));
				categorie.add(categoria);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorie;
	}

	//UPDATE ->MODIFICA
	public void modificaCategoria(Categoria categoria) {
		String query = "UPDATE categorie SET nome = ?, descrizione = ? WHERE id = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, categoria.getNomeCategoria());
			stmt.setString(2, categoria.getDescrizione());
			stmt.setInt(3, categoria.getIdCategoria());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//DELETE ->ELIMINA
	public void eliminaCategoria(int idCategoria) {
		String query = "DELETE FROM categorie WHERE id = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, idCategoria);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean speseAssociate(int idCategoria) {
        boolean risultato = false;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM Spese WHERE id_categoria = ?")) {
            statement.setInt(1, idCategoria);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                risultato = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return risultato;
    }
}
