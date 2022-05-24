package metodes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.Connect;

public class Login {
	public static boolean login(String dni, String psw) {
		boolean estat = false;
		Connection conn = Connect.obtenir_connexio_BD();
		try {
			String getLogin = "SELECT * FROM persones WHERE dni ='" + dni + "' AND contrasenya ='" + psw + "';";

			Statement stmtDep;
			ResultSet rs;

			stmtDep = conn.createStatement();
			rs = stmtDep.executeQuery(getLogin);
			if (rs.next()) {
				System.out.println("Correcte");
				estat = true;
			} else {
				System.out.println("Usuari o contrasenya mal.");
				estat = false;
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return estat;
	}
}
