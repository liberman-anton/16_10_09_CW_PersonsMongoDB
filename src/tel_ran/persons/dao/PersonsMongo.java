package tel_ran.persons.dao;

import java.util.*;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import tel_ran.databases.mongo.MongoConnection;
import tel_ran.persons.entities.Person;

public class PersonsMongo {
	private static final String COLLECTION_NAME = "persons";
	private MongoCollection<Document> persons;
	
	public PersonsMongo(String uriStr, String databaseName){	
		MongoConnection mongoConnection = MongoConnection.getMongoConnection(uriStr, databaseName);
		persons = mongoConnection.getDataBase().getCollection(COLLECTION_NAME);
	}
	
	public boolean addPerson(Person person){
		persons.insertOne(getDocument(person));
		return true;
		
	}

	private Document getDocument(Person person) {
		Document res = new Document();
		res.put("_id", person.getId());
		res.put("name", person.getName());
		res.put("birthYear", person.getBirthYear());
		return res;
	}
	public void drop(){
		persons.drop();
	}

	public Person getPerson(int id) {
		Document query = new Document("_id",id);
		FindIterable<Document> resIterable = persons.find(query);
		if(resIterable == null)
			return null;
		Iterator<Document> it = resIterable.iterator();
		Document resDocument = resIterable.first();
		if(resDocument == null)
			return null;
		return getPersonFromDocument(it.next());
	}

	private Person getPersonFromDocument(Document resDocument) {
		return new Person
				(resDocument.getInteger("_id"), 
				resDocument.getInteger("birthYear"), 
				resDocument.getString("name"));
	}

	public Iterable<Person> getPersonByName(String name) {
		Document query = new Document("name",name);
		FindIterable<Document> resIterable = persons.find(query);
		return getPersons(resIterable);
	}

	private Iterable<Person> getPersons(FindIterable<Document> resIterable) {
		ArrayList<Person> res = new ArrayList<>();
		for(Document document : resIterable)
			res.add(getPersonFromDocument(document));
		return res;
	}

	public Iterable<Person> getPersonsByNameAndYearAfter(String name, int year) {
		Document query = new Document("name",name).append("birthYear", new Document("$gte",year));
		FindIterable<Document> resIterable = persons.find(query);
		return getPersons(resIterable);
	}

	public Iterable<Person> getPersonByNames(String[] names) {
		List<Document> queriesList = getQueriesList(names);
		Document query = new Document("$or", queriesList);
		
		FindIterable<Document> resIterable = persons.find(query);
		return getPersons(resIterable);
	}

	private List<Document> getQueriesList(String[] names) {
		List<Document> res = new ArrayList<>();
		for(String name : names){
			res.add(new Document("name",name));
		}
		return res;
	}
}