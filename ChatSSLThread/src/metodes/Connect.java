package metodes;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
		public static Connection obtenir_connexio_BD() {
	    	Connect con = null;
	        String servidor = "jdbc:postgresql://192.168.1.99:5432/vmdel";
	        String user = "postgres";
	        String password = "1234";
		    	try {
		    		try {
		    			Class.forName("org.postgresql.Driver").newInstance();
		    		} catch(ClassNotFoundException ex) {
		    			System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
		    		}
		    		con = DriverManager.getConnection(servidor, user, password);
		    	} catch (Exception e) {
		    		e.printStackTrace();
		    	}
		    return con;
	    }
}
