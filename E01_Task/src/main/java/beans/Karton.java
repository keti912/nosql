package beans;

public class Karton {

	private Lekar lekar;
	private Pacijent pacijent;
	private Dijagnoza[] dijagnoze;

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Dijagnoza[] getDijagnoze() {
		return dijagnoze;
	}

	public void setDijagnoze(Dijagnoza[] dijagnoze) {
		this.dijagnoze = dijagnoze;
	}
}
