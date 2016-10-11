package tel_ran.persons.entities;

public class Child extends Person {
	private String kindergarten;

	public String getKindergarten() {
		return kindergarten;
	}

	public Child(int id, int birthYear, String name, String kindergarten) {
		super(id, birthYear, name);
		this.kindergarten = kindergarten;
	}
	
}
