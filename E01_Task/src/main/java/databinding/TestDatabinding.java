package databinding;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Dijagnoza;
import beans.Karton;
import beans.Lekar;
import beans.Pacijent;

public class TestDatabinding {

	public void readKarton() {
		ObjectMapper om = new ObjectMapper();
		try {
			Karton k = om.readValue(new File("Karton.json"), Karton.class);

			System.out.println("Karton broj: " + k.getPacijent().getBrojKartona());
			System.out.println("\tPacijent: " + k.getPacijent().getIme() + " " + k.getPacijent().getPrezime());
			System.out.println("\tDijagnoze: ");
			Dijagnoza[] dijagnoze = k.getDijagnoze();
			for (Dijagnoza d : dijagnoze) {
				System.out.println("\t\t" + d.getSifra() + ", " + d.getNaziv());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void writeKarton() {
		ObjectMapper om = new ObjectMapper();

		Pacijent p = new Pacijent();
		p.setIme("Zika");
		p.setPrezime("Zivkovic");
		p.setBrojKartona("15/15");

		Lekar l = new Lekar();
		l.setIme("Marko");
		l.setPrezime("Markovic");

		Dijagnoza d1 = new Dijagnoza();
		d1.setNaziv("upala grla");
		d1.setSifra("ug1");

		Dijagnoza d2 = new Dijagnoza();
		d2.setNaziv("alergijska reakcija");
		d2.setSifra("ar2");

		Dijagnoza[] d = { d1, d2 };

		Karton k = new Karton();
		k.setPacijent(p);
		k.setLekar(l);
		k.setDijagnoze(d);
		try {
			om.writeValue(new File("Karton2.json"), k);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		TestDatabinding tdb = new TestDatabinding();
		tdb.readKarton();
		tdb.writeKarton();
	}
}
