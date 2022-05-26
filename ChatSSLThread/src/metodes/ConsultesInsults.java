package metodes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.Connect;

public class ConsultesInsults {
	public static void main(String[] args) {
		conInsult("puta", "12345678Z");
	}
	public static void conInsult(String insult, String dni) {
		Connection conn = Connect.obtenir_connexio_BD();
		String getInsult = "SELECT id FROM insults WHERE nom = '" + insult + "';";

		try {
			Statement stmtDep;
			ResultSet rs;

			stmtDep = conn.createStatement();
			rs = stmtDep.executeQuery(getInsult);
			if (rs.next()) {
				int id = rs.getInt("id");
				registreInsults(id, dni);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String getCategoria(String insult) {
		Connection conn = Connect.obtenir_connexio_BD();
		String categoria = "";
		String getInsult = "SELECT id_categoria FROM insults WHERE nom = '" + insult + "';";

		try {
			Statement stmtDep;
			ResultSet rs;

			stmtDep = conn.createStatement();
			rs = stmtDep.executeQuery(getInsult);
			if (rs.next()) {
				categoria = rs.getString("id_categoria");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoria;
	}
	
	public static boolean conInsultExist(String insult) {
		boolean exist = true;
		Connection conn = Connect.obtenir_connexio_BD();
		String getInsult = "SELECT id FROM insults WHERE nom = '" + insult + "';";

		try {
			Statement stmtDep;
			ResultSet rs;

			stmtDep = conn.createStatement();
			rs = stmtDep.executeQuery(getInsult);
			if (rs.next()) {
				exist = true;
			} else {
				exist = false;
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

	public static void registreInsults(int id, String dni) {
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		
		Connection conn = Connect.obtenir_connexio_BD();
		String insertIns = "INSERT INTO `registre_insults`(`id`, `id_insult`, `Dni_emisor`, `dataInsult`)"
				+ " VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(insertIns);
			preparedStmt.setString(1, null);
			preparedStmt.setInt(2, id);
			preparedStmt.setString(3, dni);
			preparedStmt.setDate(4, date);
			preparedStmt.execute();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}
	
	public static void inserirNouInsults(String nom, String categoria) {	
		Connection conn = Connect.obtenir_connexio_BD();
		String insertIns = "INSERT INTO `insults`(`id`, `nom`, `id_categoria`)"
				+ " VALUES (?, ?, ?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(insertIns);
			preparedStmt.setString(1, null);
			preparedStmt.setString(2, nom);
			preparedStmt.setString(3, categoria);
			preparedStmt.execute();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}
}
