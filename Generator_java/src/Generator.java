import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Generator {

	// utworzenie daty
	public static String date() {
		String date = "";
		String year = String.valueOf(new Random().nextInt(100)); // [0...99]

		if (year.length() < 2) {
			year = "0" + year;
		}

		String month = String.valueOf(new Random().nextInt(12) + 1); // [1...12]

		if (month.length() < 2) {
			month = "0" + month;
		}

		String day = String.valueOf(new Random().nextInt(31) + 1); // [1...31]

		if (day.length() < 2)
			day = "0" + day;

		date = year + month + day;
		return date;
	}

	// utworzenie numeru pesel
	public static String peselGenerator() {

		String date = date();
		String fiverandom = String.valueOf(new Random().nextInt(90000) + 10000); // [0...99999]
		String pesel = date + fiverandom;

		return pesel;
	}

	// konwersja z pliku do tablicy
	public static ArrayList<String> toArray(String fileName) throws IOException {
		String str;
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
		ArrayList<String> nazwisko = new ArrayList<String>();
		while ((str = in.readLine()) != null) {

			if (!Character.isLetter(str.charAt(0))) {
				str = str.substring(1);
			}
			// System.out.println(str);

			nazwisko.add(str);
		}
		in.close();
		return nazwisko;
	}

	// losowy dobor z tablicy
	public static String getRandom(ArrayList<String> array) {
		int rnd = new Random().nextInt(array.size());
		return array.get(rnd);
	}

	public static void main(String[] args) throws IOException {

		int id = 0;
		int id2 = 0;
		// otwarcie plikow z danymi
		ArrayList<String> nazwisko = toArray("last.txt");
		ArrayList<String> imiez = toArray("first-f.txt");
		ArrayList<String> imiem = toArray("first-m.txt");
		ArrayList<String> city = toArray("city.txt");
		ArrayList<String> firmy = toArray("firma.txt");

		// otwarcie plikow do zapisu poszczegolnych tabel
		FileWriter file = new FileWriter("D³u¿nik.txt", true);
		BufferedWriter out = new BufferedWriter(file);

		FileWriter file2 = new FileWriter("D³ug.txt", true);
		BufferedWriter out2 = new BufferedWriter(file2);

		FileWriter file3 = new FileWriter("Wierzyciel.txt", true);
		BufferedWriter out3 = new BufferedWriter(file3);

		// tabela 1
		// pesel imie nazwisko miejscowosc wojewodztwo

		for (int i = 0; i < 200; i++) {
			// for (int i = 0; i < 20; i++) {
			String pesel = peselGenerator();
			int gender = new Random().nextInt(2);
			String n = getRandom(nazwisko);
			String im = "";
			if (gender == 0) {
				im = getRandom(imiez);
				n = n.replace("ski", "ska");
				n = n.replace("cki", "cka");
				n = n.replace("dzki", "dzka");
			} else {
				im = getRandom(imiem);
			}
			String ci = getRandom(city);

			// zapis do pliku d³u¿nik
			id2++;

			out.write(id2 + ";" + pesel + ";" + im + ";" + n + ";" + ci);
			out.newLine();

			// tabela2
			// id od 1
			// pesel
			// d³ug od 1 - 100000
			// data randomowa do 10 lat do tylu
			// id wierzyciela losowa od 1 - 30
			int x = new Random().nextInt(20);
			for (int j = 0; j < x; j++) {
				id++;

				int dlug = new Random().nextInt(10000) + 1;
				int id_wierz = new Random().nextInt(30) + 1;
				String date = date();

				// zapis do pliku d³ug

				out2.write(id + ";" + pesel + ";" + dlug + ";" + date + ";" + id_wierz);
				out2.newLine();

			}
		}
		out.close();
		out2.close();

		// tabela3
		// id firma miejsowosc wojewodztwo

		for (int x = 0; x < 30; x++) {

			String firma = firmy.get(x);
			String ci = getRandom(city);

			out3.write(x + 1 + ";" + firma + ";" + ci);
			out3.newLine();
		}
		out3.close();
	}
}