package tel_ran.persons.controller;

import tel_ran.persons.dao.PersonsMongo;
import tel_ran.persons.entities.Person;
//import static tel_ran.persons.controller.interfaces.PersonsRandomJdbcConstants.*;

public class UsegePersonsJdbc {

	public static void main(String[] args) throws Exception {
		PersonsMongo personsMongo = new PersonsMongo
				("mongodb://root:12345@ds053166.mlab.com:53166/","bsh_persons");
		System.out.println(personsMongo.getPerson(5081011));
		
//		displayPersons(personsMongo.getPersonByName(("name3")));
//		displayPersons(personsMongo.getPersonsByNameAndYearAfter("name3",1960));
		displayPersons(personsMongo.getPersonByNames(new String[]{"name5", "name18"}));
		
//		Iterable<Person> personsAge = personsJdbc.getPersonsByAge(20, 30);
//		displayPersons(personsAge);
//		System.out.println();
//		System.out.println(personsJdbc.getPerson(1554907));
//		System.out.println();
//		displayPersons(personsJdbc.getPersonsByName("name7"));
	}

	

	private static void displayPersons(Iterable<Person> persons) {
		for(Person person : persons){
			System.out.println(person);
		}
		
	}

}
