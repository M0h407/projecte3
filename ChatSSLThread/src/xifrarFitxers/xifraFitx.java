package xifrarFitxers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class xifraFitx {
	public static String dirXifrar = "";

	public static void xifrarXml(String ruta, String nomArxiu, String dni) throws IOException {
		try {
			// Aquí generem la Secret Key i la passo a Base64.
			SecretKey Key;
			Key = Xifra.keygenKeyGeneration(256);
			String Clau = Base64.getEncoder().encodeToString(Key.getEncoded());
			String[] parts = nomArxiu.split("\\.");
			// Creem el nom del directori i tots els arxius que necessitarem.
			generarCarpetaXif(dni);
			String dir = dirXifrar + "\\" + parts[0] + "\\";
			String ter = parts[1];
			String ke = dir + parts[0] + ".key";
			String xi = dir + parts[0] + ".xif";
			String terminacio = dir + parts[0] + ".ter";
			// I aquí creem el directori i tots els arxius que necessitarem.
			File key = new File(ke);
			File xif = new File(xi);
			File file = new File(ruta);
			File directory = new File(dir);
			directory.mkdir();
			// Mirem si l'arxiu existeix.
			if (file.exists()) {
				// Mirem si aquest fixer ja ha sigut xifrat.
				if (!key.exists() && !xif.exists()) {
					// Llegim l'arxiu en byte i el xifrem amb la secret key.
					byte[] fileContent = Files.readAllBytes(file.toPath());
					byte[] textXifrat = Xifra.encryptData(Key, fileContent);
					// Cridem la classe d'escriptura i escrivim en l'arxiu la clau amb Base64.
					Escriure Sclau = new Escriure(ke);
					Sclau.esciure(Clau);
					// Tornem a cridar la classe Escriure i escrivim el text xifrat.
					Escriure arXif = new Escriure(xi);
					String text64 = Base64.getEncoder().encodeToString(textXifrat);
					arXif.esciure(text64);
					// I aquí escric la terminació del fitxer que hem xifrat.
					Escriure termin = new Escriure(terminacio);
					termin.esciure(ter);
				} else {
					System.out.println("Aquest arxius ja esta xifrat");
				}
			} else {
				System.out.println("Aquest fitxer no existeix");
			}
		} catch (Error e) {
			System.out.println("Error " + e);
		}
	}

	public static void generarCarpetaXif(String dni) {
		dirXifrar = "xml\\" + dni + "\\xmlXif";
		File dirXif = new File(dirXifrar);
		if (!dirXif.exists()) {
			dirXif.mkdir();
		}
	}

	public static void desxifrarXml(String nomArx, String dirArx) throws IOException {
		try {
			// Introduïm l'arxiu que volem desencriptar.
			System.out.println("Introdueix la direcció de l'arxiu:");
			// String ruta = teclat.next();
			// Tornem a partir el nom de l'arxiu i creem els noms dels arxius que llegirem.
			String[] parts = nomArx.split("\\.");
			String dir = dirArx + "\\" + parts[0] + "\\";
			String s = dir + parts[0] + ".key";
			String f = dir + parts[0] + ".xif";
			String i = dir + parts[0] + ".ter";
			// Llegim l'arxiu que té la terminació i la creem un string amb el nom de
			// l'arxiu original.
			String term = new String(Files.readAllBytes(Paths.get(i)));
			String d = dir + parts[0] + "." + term;
			// Creem l'arxiu xifrat.
			File file = new File(f);
			// Mirem si aquest arxiu existeix.
			if (file.exists()) {
				// Llegim l'arxiu que te la Secret Key i l'arxiu xifrat.
				String secKey = new String(Files.readAllBytes(Paths.get(s)));
				String xifrt = new String(Files.readAllBytes(Paths.get(f)));
				// Passem a byte els dos arxius que hem llegit.
				byte[] fileContent = Base64.getDecoder().decode(xifrt);
				byte[] encodedKey = Base64.getDecoder().decode(secKey);
				// Amb el byte secretKey la tornem a passar a secretKey.
				SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
				// I aquí posem l'arxiu desxifrat en byte.
				byte[] textDesxifrat = Xifra.decryptData(originalKey, fileContent);
				// Aquí cridem la classe per escriure amb byte en un arxiu, i utilitzem el nom
				// que ja teníem amb la terminació original.
				EscrBin si = new EscrBin(d);
				si.escriu(textDesxifrat);
			}
		} catch (Error e) {
			System.out.println("Error " + e);
		}
	}

	public static void obtenirNomXml(String dni, String numSel) throws IOException {
		String dirCarpeta = "xml\\" + dni + "\\xmlXif";
		File carpeta = new File(dirCarpeta);
		String[] listado = carpeta.list();
		if (listado == null || listado.length == 0) {
			System.out.println("No hay elementos dentro de la carpeta actual");
			return;
		} else {
			for (int i = 0; i < listado.length; i++) {
				String nomArx = listado[i];
				String[] numArx = nomArx.split("-");
				String num = numArx[0];
				if (numSel.equals(num)) {
					desxifrarXml(nomArx, dirCarpeta);
				}
			}
		}
	}
}