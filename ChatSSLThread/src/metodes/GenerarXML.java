package metodes;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.Connect;

public class GenerarXML {
	public static String xmlFilePath = "";
	public static String nomXml = "";
	public static String hora = "";
	public static int nuArx = 0;

	public static String dataHora() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String dataPath = formatter.format(date);
		String[] split = dataPath.split(" ");
		hora = split[1];
		hora = hora.replace(":", "_");

		long millis = System.currentTimeMillis();
		java.sql.Date date1 = new java.sql.Date(millis);
		String dataFin = date1.toString();

		return dataFin;
	}

	public static void dirXML(String dni) {
		nuArx = 0;
		String direct = "xml\\" + dni;
		File carpeta = new File(direct);
		if (!carpeta.exists()) {
			carpeta.mkdir();
		}

		String dirGenXml = direct + "\\xmlO";
		File carpetaXML = new File(dirGenXml);
		if (!carpetaXML.exists()) {
			nuArx = 1;
			carpetaXML.mkdir();
		} else {
			File[] lista = carpetaXML.listFiles();
			nuArx = lista.length + 1;
		}
		String dataHora = dataHora();

		nomXml = nuArx + "-" + dataHora + "-" + hora + ".xml";
		xmlFilePath = dirGenXml + "\\" + nuArx + "-" + dataHora + "-" + hora + ".xml";
	}

	public static void generarXML(String dniX, String paraulaX, String emisorX, String noCens) throws IOException {
		dirXML(dniX);
		String tipusParaX = metodes.ConsultesInsults.getCategoria(noCens);

		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		String dataFin = date.toString();

		try {
			DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = df.newDocumentBuilder();
			Document document = db.newDocument();

			Element arrel = document.createElement("insults");
			document.appendChild(arrel);

			Element pare = document.createElement("insult");
			arrel.appendChild(pare);

			Attr dni = document.createAttribute("dni");
			dni.setValue(dniX);
			pare.setAttributeNode(dni);

			// Dni del Receptor
			Element paraula = document.createElement("paraula");
			paraula.appendChild(document.createTextNode(paraulaX));
			pare.appendChild(paraula);

			// Nom del receptor
			Element tipusParaula = document.createElement("tipusParaula");
			tipusParaula.appendChild(document.createTextNode(tipusParaX));
			pare.appendChild(tipusParaula);

			// direccio del receptor
			Element dataHora = document.createElement("dataHora");
			dataHora.appendChild(document.createTextNode(dataFin));
			pare.appendChild(dataHora);

			// nom del producte
			Element emisor = document.createElement("emisor");
			emisor.appendChild(document.createTextNode(emisorX));
			pare.appendChild(emisor);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource ds = new DOMSource(document);
			StreamResult sr = new StreamResult(new File(xmlFilePath));

			t.transform(ds, sr);

			xifrarFitxers.xifraFitx.xifrarXml(xmlFilePath, nomXml, dniX);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		}
	}

	public static void xifrarXML(String numInserit) {
		File carpeta = new File("");
		String[] listado = carpeta.list();
		if (listado == null || listado.length == 0) {
			System.out.println("No hay elementos dentro de la carpeta actual");
			return;
		} else {
			for (int i = 0; i < listado.length; i++) {
				System.out.println(listado[i]);
				String nomArx = listado[i];
				String[] numArx = nomArx.split("-");
				String num = numArx[0];
				if (numInserit == num) {
					// nomArx
				}
			}
		}
	}

	public static void desxifrarXML(String numInserit, String dni) {
		String dirCarpeta = "xml\\" + dni;
		File carpeta = new File(dirCarpeta);
		String[] listado = carpeta.list();
		if (listado == null || listado.length == 0) {
			System.out.println("No hay elementos dentro de la carpeta actual");
			return;
		} else {
			for (int i = 0; i < listado.length; i++) {
				System.out.println(listado[i]);
				String nomArx = listado[i];
				String[] numArx = nomArx.split("-");
				String num = numArx[0];
				if (numInserit == num) {
					// nomArx
				}
			}
		}
	}

}