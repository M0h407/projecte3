package metodes;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class mongoDB {

	public static void main(String[] args) {
		consultaMongo("puta");
	}

	public static void inserirInsult(String fraseCensurada, String nomPersona, String dataHora, String dni) {
		try {
			// Creating a Mongo client
			@SuppressWarnings("resource")
			MongoClient mongo = new MongoClient("192.168.1.207", 27017);

			// Creating Credentials
			@SuppressWarnings("deprecation")
			DB db = mongo.getDB("insults");

			System.out.println("Connexio amb mongoDb correcta.");

			DBCollection collection = db.getCollection("registres");

			BasicDBObject document = new BasicDBObject();
			document.put("database", "insults");
			document.put("table", "registres");

			BasicDBObject documentDetail = new BasicDBObject();
			documentDetail.put("fraseCensurada", fraseCensurada);
			documentDetail.put("nomPersona", nomPersona);
			documentDetail.put("dataHora", dataHora);
			documentDetail.put("dni", dni);
			document.put("detail", documentDetail);

			collection.insert(document);
			System.out.println("El document s'ha inserit correctament");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void inserirParaulaInsult(String paraula, String nomPersona, String dataHora, String dni) {
		try {
			// Creating a Mongo client
			@SuppressWarnings("resource")
			MongoClient mongo = new MongoClient("192.168.1.207", 27017);

			// Creating Credentials
			@SuppressWarnings("deprecation")
			DB db = mongo.getDB("insults");

			System.out.println("Connexio amb mongoDb correcta.");

			DBCollection collection = db.getCollection("paraulaCens");

			BasicDBObject document = new BasicDBObject();
			document.put("database", "insults");
			document.put("table", "fraseCens");

			BasicDBObject documentDetail = new BasicDBObject();
			documentDetail.put("paraula", paraula);
			documentDetail.put("nomPersona", nomPersona);
			documentDetail.put("dataHora", dataHora);
			documentDetail.put("dni", dni);
			document.put("detail", documentDetail);

			collection.insert(document);
			System.out.println("El document s'ha inserit correctament");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void consultaMongo(String consultaInsult) {
		// Creating a Mongo client
		try {
			@SuppressWarnings("resource")

			MongoClient mongo = new MongoClient("192.168.1.207", 27017);

			// Creating Credentials
			DB db = mongo.getDB("insults");

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("paraula", "guarra");
			System.out.println("Connexio amb mongoDb correcta.");

			DBCollection collection = db.getCollection("paraulaCens");
			// DBObject query = new BasicDBObject("paraula", new BasicDBObject("$regex",
			// "guarra"));
			DBCursor cur = collection.find(whereQuery);
			int i = 0;
			while (cur.hasNext()) {
				System.out.println(cur.next().toString());
				i++;
			}
			System.out.println(i);
		} catch (Exception exm) {
			System.out.println("Error de connexio: " + exm);
		}
	}

}
