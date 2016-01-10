package Examples;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import Examples.CarRental;
import Examples.Car;
import Examples.Customer;
import Examples.Catalog;
import Examples.Employee;
import Examples.Insurance;
import Examples.Lending;
import Examples.Location;
import Examples.ObjectFactory;
import Examples.Payment;
import Examples.Reservation;
import Examples.SimpleGUI;

import java.util.Scanner;
import java.util.Calendar;

public class Reasoner {

	// The main Class Object holding the Domain knowledge

	// Generate the classes automatically with: Opening a command console and
	// type:
	// Path to YOUR-PROJECTROOT-IN-WORKSPACE\xjc.bat yourschemaname.xsd -d src
	// -p yourclasspackagename

	public CarRental ourrental; 
	static SimpleGUI Myface;
	// The lists holding the class instances of all domain entities

	public List ourrentalList = new ArrayList(); 									
	public List theCarList = new ArrayList(); 
	public List theEmployeeList = new ArrayList(); 
	public List theCatalogList = new ArrayList(); 
	public List theLendingList = new ArrayList(); 
	public List theRecentThing = new ArrayList();
	public List theCustomerList = new ArrayList();

	// Gazetteers to store synonyms for the domain entities names

	public Vector<String> librarysyn = new Vector<String>(); 
	public Vector<String> Carsyn = new Vector<String>();
	public Vector<String> customersyn = new Vector<String>();
	public Vector<String> catalogsyn = new Vector<String>(); 
	public Vector<String> lendingsyn = new Vector<String>(); 
	public Vector<String> recentobjectsyn = new Vector<String>();

	public String questiontype = ""; //question type selects method to use in a query
	
	public List classtype = new ArrayList(); // class type selects which class list to query
	
	public String attributetype = ""; // attributetype selects the attribute to check for in the query
	
	public Object Currentitemofinterest; // Last Object dealt with
	
	public Integer Currentindex; // Last Index used

	public String tooltipstring = "";
	public String URL = ""; // URL for Wordnet site
	public String URL2 = ""; // URL for Wikipedia entry
	public String URL3 = ""; // URL for Car Comparision website entry

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd-HH-mm";

	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	
	/**public static String modelpicked() {
		String s = Myface.Input.getText();
		String[] parts = s.split("_"); //returns an array with the 2 parts
		String firstPart = parts[0]; //14.015
		
	}**/

	public Reasoner(SimpleGUI myface) {

		Myface = myface; // reference to GUI to update Tooltip-Text basic constructor for the constructors sake :)
		
	}

	public void initknowledge() { // load all the library knowledge from XML

		JAXB_XMLParser xmlhandler = new JAXB_XMLParser(); // we need an instance of our parser
		
		File xmlfiletoload = new File("Library.xml"); // we need a (CURRENT) file (xml) to load

		// Init synonmys and typo forms in gazetteers

		librarysyn.add("library"); 
		librarysyn.add("place"); 
		librarysyn.add("Carstore"); 
		librarysyn.add("Carhouse"); 
		librarysyn.add("libary");
		librarysyn.add("libraby");
		librarysyn.add("librarie"); 

		Carsyn.add("Car"); 

		Carsyn.add("cars");
		Carsyn.add("car");
		Carsyn.add(" bmw");
		Carsyn.add("bmws");
		Carsyn.add(" it");
		Carsyn.add("BMW");
		Carsyn.add("mercedes"); 
		Carsyn.add("audi");
		Carsyn.add("car");
		Carsyn.add("ride");
		Carsyn.add("rides");
		Carsyn.add("rustbuckest");
		Carsyn.add(" bok");
		Carsyn.add(" record");
		Carsyn.add("Carlet");
		Carsyn.add("volume");

		customersyn.add("customer"); 
		customersyn.add("reader");
		customersyn.add("follower");
		customersyn.add("client");
		customersyn.add("member");
		customersyn.add("guy");

		catalogsyn.add("catalog"); 
		catalogsyn.add("Carlist");
		catalogsyn.add("inventor");

		lendingsyn.add(" lending");
		lendingsyn.add(" a week"); 
		lendingsyn.add(" a month"); 
		lendingsyn.add(" a year"); 
		lendingsyn.add(" itworks");
		lendingsyn.add(" 0");

		recentobjectsyn.add(" this"); 
		recentobjectsyn.add(" that");
		recentobjectsyn.add(" him");
		recentobjectsyn.add(" her"); // spaces to prevent collision with "wHERe"
		recentobjectsyn.add(" it");

		try {
			FileInputStream readthatfile = new FileInputStream(xmlfiletoload); // initiate input stream
			ourrental = xmlhandler.loadXML(readthatfile);

			// Fill the Lists with the objects data just generated from the xml

			theCarList = ourrental.getCar();
			theEmployeeList = ourrental.getEmployee(); 
			theCatalogList = ourrental.getCatalog();
			theLendingList = ourrental.getLending();
			theCustomerList = ourrental.getCustomer();
			ourrentalList.add(ourrental); // force it to be a List
			
			System.out.println("List reading");
			System.out.println(ourrental);
			System.out.println(ourrental.car.size());
			System.out.println(ourrental.name);

		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("error in init");
		}
	}

	public Vector<String> generateAnswer(String input) { // Generate an answer (String Vector)
		
		String customername1 = Myface.Input.getText();
		System.out.println(customername1);
		
		Vector<String> out = new Vector<String>();
		out.clear(); // just to make sure this is a new and clean vector
		 
		questiontype = "none";

		Integer Answered = 0; // check if answer was generated

		Integer subjectcounter = 0; // Counter to keep track of # of identified subjects (classes)

		// Answer Generation Idea: content = Questiontype-method(classtype class) (+optional attribute)

		// ___________________________ IMPORTANT _____________________________

		input = input.toLowerCase(); // all in lower case because thats easier to analyse

		// ___________________________________________________________________

		String answer = ""; // the answer we return
		
		String customername = "Bakar";
		
		

		// ----- Check for the kind of question (number, location, etc)------------------------------

		if (input.contains("how many")) { questiontype = "amount"; input = input.replace("how many", "<b>how many</b>"); }
		if (input.contains("number of")) { questiontype = "amount";input = input.replace("number of", "<b>number of</b>");}
		if (input.contains("amount of")) { questiontype = "amount";input = input.replace("amount of", "<b>amount of</b>"); }
		if (input.contains("count")) { questiontype = "amount";input = input.replace("count", "<b>count</b>"); }
		if (input.contains("what car")) { questiontype = "list";input = input.replace("what car", "<b>what Car</b>"); }
		if (input.contains("diplay all")) { questiontype = "list";input = input.replace("diplay all", "<b>diplay all</b>"); }

		if (input.contains("is there a")) { questiontype = "checkfor";input = input.replace("is there a", "<b>is there a</b>"); }
		if (input.contains("i am searching")) { questiontype = "checkfor";input = input.replace("i am searching", "<b>i am searching</b>"); }
		if (input.contains("i am looking for")) { questiontype = "checkfor";input = input.replace("i am looking for", "<b>i am looking for</b>"); }
		if (input.contains("do you have") && !input.contains("how many")) { questiontype = "checkfor";input = input.replace("do you have", "<b>do you have</b>"); }
		if (input.contains("i look for")) { questiontype = "checkfor"; input = input.replace("i look for", "<b>i look for</b>"); }
		if (input.contains("is there")) { questiontype = "checkfor";input = input.replace("is there", "<b>is there</b>"); }
		
		if (input.contains("list all") 
				|| input.contains("view all") 
				|| input.contains("show all")) 
		{ 
			questiontype = "list";
			input = input.replace("what car", "<b>what car</b>");
		}

		if (input.contains("where") 
				|| input.contains("can't find") 
				|| input.contains("can i find")
				|| input.contains("way to"))
		{
			questiontype = "location";
			System.out.println("Find Location");
		}

		if (input.contains("can i lend") 
				|| input.contains("can i borrow") 
				|| input.contains("can i get the Car")
				|| input.contains("am i able to") 
				|| input.contains("could i lend") 
				|| input.contains("i want to lend")
				|| input.contains("can i rent") 
				|| input.contains("can i Rent") 
				|| input.contains("i want to borrow"))
		{
			questiontype = "intent";
			System.out.println("Find CarAvailability");
		}

		if (input.contains("thank you") 
				|| input.contains("bye") 
				|| input.contains("thanks")
				|| input.contains("cool thank"))
		{
			questiontype = "farewell";
			System.out.println("farewell");
		}

		if (input.contains("hello") 
				|| input.contains("hi") 
				|| input.contains("hanji")
				|| input.contains("Hi") 
				|| input.contains("Hey")
				|| input.contains("yo") 
				|| input.contains("Hello")
				|| input.contains("hey"))

		{
			questiontype = "introduction";
			System.out.println("Introduction");
		}

		if (input.contains("a car") 
				|| input.contains("A car") 
				|| input.contains("im looking for a car")
				|| input.contains("I am looking for a car"))

		{
			questiontype = "statingvehicle";
			System.out.println("stating type of vehicle");
		}

		if (input.contains("no i dont") 
				|| input.contains("no, i dont") 
				|| input.contains("No i dont")
				|| input.contains("no i dont know") 
				|| input.contains("i dont know") 
				|| input.contains("no i don't")
				|| input.contains("no"))

		{
			questiontype = "dontknowcar";
			System.out.println("view all cars or type random brand");
		}
		
		if (input.contains("who has it") 
				|| input.contains("by who") 
				|| input.contains("who"))

		{
			questiontype = "location";
		}

		//#################################################################################
		if (input.contains("yoo") 
				|| input.contains("lol"))
			
		
		{
			questiontype = "intro";
//			String customername1 = Myface.Input.getText();
//			System.out.println(customername1);
			System.out.println("Find Location");
		}
		
		//#################################################################################
		
		if (input.contains("my name is") 
				|| input.contains("my names"))
		
		{
			questiontype = "name";
			
			System.out.println("Find Location");
		}
		
		//#################################################################################
		
		if (input.contains("i want the") 
				|| input.contains("i want the"))
		
		{
			questiontype = "pickmodel";
			
			System.out.println("picking model");
		}


		// ------- Checking the Subject of the Question
		// --------------------------------------
		classtype = theCarList;
		for (int x = 0; x < Carsyn.size(); x++) { 
			if (input.contains(Carsyn.get(x))) { 
				classtype = theCarList;
				input = input.replace(Carsyn.get(x), "<b>" + Carsyn.get(x) + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Car recognised.");
			}
		}
		
		for (int x = 0; x < customersyn.size(); x++) { 
			if (input.contains(customersyn.get(x))) { 
				classtype = theCustomerList; 
				input = input.replace(customersyn.get(x), "<b>" + customersyn.get(x) + "</b>");
				subjectcounter = 1;
				System.out.println("Class type customer recognised.");
			}
		}
		
		for (int x = 0; x < catalogsyn.size(); x++) { 
			if (input.contains(catalogsyn.get(x))) { 
				classtype = theCatalogList; 
				input = input.replace(catalogsyn.get(x), "<b>" + catalogsyn.get(x) + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Catalog recognised.");
			}
		}
		
		for (int x = 0; x < lendingsyn.size(); x++) {
			if (input.contains(lendingsyn.get(x))) { 
				classtype = theLendingList; 
				input = input.replace(lendingsyn.get(x), "<b>" + lendingsyn.get(x) + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Lending recognised.");
			}
		}

		if (subjectcounter == 0) {
			for (int x = 0; x < recentobjectsyn.size(); x++) {
				if (input.contains(recentobjectsyn.get(x))) {
					classtype = theRecentThing;
					input = input.replace(recentobjectsyn.get(x), "<b>" + recentobjectsyn.get(x) + "</b>");
					subjectcounter = 1;
					System.out.println("Class type recognised as" + recentobjectsyn.get(x));
				}
			}
		}

		// More than one subject in question + Library ...
		// "Does the Library has .. Subject 2 ?"

		System.out.println("subjectcounter = " + subjectcounter);
		for (int x = 0; x < librarysyn.size(); x++) { 
			if (input.contains(librarysyn.get(x))) { 
				// Problem: "How many Cars does the Library have ?" -> classtype = Library Solution:
				if (subjectcounter == 0) { // Library is the first subject in the question
					input = input.replace(librarysyn.get(x), "<b>" + librarysyn.get(x) + "</b>");
					classtype = ourrentalList; 
					System.out.println("class type Library recognised");
				}
			}
		}

		// Compose Method call and generate answerVector

		if (questiontype == "amount") { // Number of Subject
			Integer numberof = Count(classtype);
			answer = ("The number of " + classtype.get(0).getClass().getSimpleName() + "s is " + numberof + ".");
			Answered = 1; // An answer was given
		}

		if (questiontype == "list") { // List all Subjects of a kind
			answer = ("All the Available " + classtype.get(0).getClass().getSimpleName() + "s. <br>"
					+ classtype.get(0).getClass().getSimpleName() + "s:" + ListAll(classtype));
			Answered = 1; // An answer was given
		}
		
		if (questiontype == "checkfor") { // test for a certain Subject instance

			Vector<String> check = CheckFor(classtype, input);
			answer = (check.get(0));
			Answered = 1; // An answer was given
			if (check.size() > 1) {
				Currentitemofinterest = classtype.get(Integer.valueOf(check.get(1)));
				System.out.println("Classtype List = " + classtype.getClass().getSimpleName());
				System.out.println("Index in Liste = " + Integer.valueOf(check.get(1)));
				Currentindex = Integer.valueOf(check.get(1));
				theRecentThing.clear(); // Clear it before adding (changing) the
				// now recent thing
				theRecentThing.add(classtype.get(Currentindex));
			}
		}

		// Location Question in Pronomial form "Where can i find it"
		if (questiontype == "location") { // We always expect a pronomial question to refer to the last object questioned for
			answer = ("You can find the " + classtype.get(0).getClass().getSimpleName() + " " + "at "
					+ Location(classtype, input));
			Answered = 1; // An answer was given
		}
		
		//Car curCar = new Car();

		if ((questiontype == "intent" && classtype == theCarList)
				|| (questiontype == "intent" && classtype == theRecentThing)) {
			// Can I lend the Car or not (Can I lend "it" or not)
			answer = ("Which one would you like?" +  ListMake(classtype) );
			//answer = ("" + CarAvailable(classtype, input));
			Answered = 1; // An answer was given
		}

		if (questiontype == "farewell") { // Reply to a farewell
			answer = ("You are welcome.");
			Answered = 1; // An answer was given
		}

		if (questiontype == "introduction") { // Reply to a farewell
			answer = ("Hi, to start off do you know what type of vehicle you are after?");
			Answered = 1; // An answer was given
		}
		
		if (questiontype == "name") { // Reply to a farewell
			answer = ("Hi " + customername + ", to start off do you know what type of vehicle you are after?");
			Answered = 1; // An answer was given
		}

		if (questiontype == "dontknowcar") { // Reply to a farewell
			answer = ("Would you like to <b>view all cars</b>, alternatively simply type the make of a car (i.e BMW)");
			Answered = 1; // An answer was given
		}

		if (Answered == 0) { // No answer was given
			answer = ("Sorry I didn't understand that.Would you like try again");
		}
		
		if (questiontype == "intro") { // Reply to a farewell
			answer = ("Please enter your name: ");
			//String customername = input;
			//System.out.println(customername);
			
			Answered = 1; // An answer was given
		}
		
		if (questiontype == "pickmodel") { // Reply to a farewell
			answer = ("Please enter your name: ");
			//String customername = input;
			//System.out.println(customername);
			
			Answered = 1; // An answer was given
		}
		
		

		out.add(input);
		out.add(answer);

		return out;
	}

	// Methods to generate answers for the different kinds of Questions

	// Answer a question of the "Is a Car or "it (meaning a Car) available ?"
	// kind

	public String CarAvailable(List thelist, String input) {
		boolean available = true;
		String rentduration = "0";
		String customerName = "";
		String answer = "";
		Car curCar = new Car();
		Lending curLend = new Lending();
		String Carmake = "";

		if (thelist == theCarList) { 
			int counter = 0;
			// Identify which Car is asked for
			for (int i = 0; i < thelist.size(); i++) {
				curCar = (Car) thelist.get(i); 
				if (input.contains(curCar.getMake().toLowerCase()) 
						|| input.contains(curCar.getMake().toLowerCase())) { 
					counter = i;
					Currentindex = counter;
					theRecentThing.clear(); 
					classtype = theCarList; 
					theRecentThing.add(classtype.get(Currentindex));
					Carmake = curCar.getMake();

					if (input.contains(curCar.getMake().toLowerCase())) {
						input = input.replace(curCar.getMake().toLowerCase(),
								"<b>" + curCar.getMake().toLowerCase() + "</b>");
					}

					i = thelist.size() + 1; // force break
				}
			}
		}

		// maybe other way round or double
		if (thelist == theRecentThing && theRecentThing.get(0) != null) {
			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("Car")) { 
				curCar = (Car) theRecentThing.get(0); 
				Carmake = curCar.getMake();
			}
		}

		// check all lendings if they contain the Cars ID
		for (int i = 0; i < theLendingList.size(); i++) {
			Lending curlend = (Lending) theLendingList.get(i); 
			// If there is a lending with the Cars CarID, the Car is not available
			if (curCar.getCarID().toLowerCase().equals(curlend.getCarID().toLowerCase())) { 
				input = input.replace(curlend.getCarID().toLowerCase(),
						"<b>" + curlend.getCarID().toLowerCase() + "</b>");
				rentduration = curlend.getPeriodOfRent();
				available = false;
				System.out.println(rentduration);
				i = thelist.size() + 1; // force break
			}
		}
		
		//###########################################
		for (int i = 0; i < theCustomerList.size(); i++) {
			Customer curCust = (Customer) theCustomerList.get(i); 
			// If there is a lending with the Cars CarID, the Car is not available
			if (curCar.getCarID().toLowerCase().equals(curCust.getCarID().toLowerCase())) { 
				input = input.replace(curCust.getCarID().toLowerCase(),
						"<b>" + curCust.getCarID().toLowerCase() + "</b>");
				customerName = curCust.getFName();
				available = false;
				System.out.println(customerName);
				i = thelist.size() + 1; // force break
			}
		}
		//###########################################

		if (available) {
			answer = "Yes you can rent one of the following " +" Make: " + curCar.getMake()  +
					 curCar.getModel()+ "Please let us know for how many days you need the car for ";
			
		} else {
			

			answer = "Sorry, " + curCar.getMake() + " is not Available. It has been lent by " + customerName 
					+ " for "+ rentduration
					+ "\n" ; //TODO ADD RECOMMENDATION BY TYPE OF VEHICLE*******************

		}

		URL3 = "https://soc.uwl.ac.uk/~21226018/" + curCar.getMake().toLowerCase() +".html";
		System.out.println("URL = " + URL3);
		tooltipstring = readwebsite(URL3);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL3);

		return (answer);
	}
	
	// Answer a question of the "How many ...." kind

	public Integer Count(List thelist) { // List "thelist": List of Class Instances (e.g. theCarList)

		System.out.println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL3);

		return thelist.size();
	}

	// Answer a question of the "What kind of..." kind
	public String ListAll(List thelist) {
		String listemall = "<ul>";
		if (thelist == theCarList) { 
			for (int i = 0; i < thelist.size(); i++) {
				Car curCar = (Car) thelist.get(i); 
				listemall = listemall + "<li>" + (curCar.getMake() + "</li>"); 
			}
		}

		if (thelist == theEmployeeList) { 
			for (int i = 0; i < thelist.size(); i++) {
				Customer curmem = (Customer) thelist.get(i);
				listemall = listemall + "<li>" 
						+ (curmem.getFName() + " " + curmem.getSName() + "</li>");
			}
		}

		if (thelist == theCatalogList) { 
			for (int i = 0; i < thelist.size(); i++) {
				Catalog curcat = (Catalog) thelist.get(i); 
				listemall = listemall + "<li>" + (curcat.getCatalogID() + "</li>");
			}
		}

		if (thelist == theLendingList) {
			for (int i = 0; i < thelist.size(); i++) {
				Lending curlend = (Lending) thelist.get(i); 
				listemall = listemall + "<li>" + (curlend.getCarID() + "</li>"); 
			}
		}

		listemall += "</ul>";
		System.out.println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return listemall;
	}
	
	public String ListMake(List thelist) {
		String listmake = "<ul>";
		if (thelist == theCarList) { 
			for (int i = 0; i < thelist.size(); i++) {
				Car curCar = (Car) thelist.get(i); 
				if(curCar.getMake().equals("BMW")){ //THIS IS THE LINE YOU ARE MISSING
					listmake = listmake + "<li>" + (curCar.getModel() + "</li>"); 
				}
			}
		}

		

		listmake += "</ul>";
		System.out.println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return listmake;
	}


	// Answer a question of the "Do you have..." kind
	public Vector<String> CheckFor(List thelist, String input) {
		Vector<String> yesorno = new Vector<String>();
		if (classtype.isEmpty()) {
			yesorno.add("Class not recognised. Please specify if you are searching for a Car, catalog, member, or lending?");
		} else {
			yesorno.add("No we don't have such a " + classtype.get(0).getClass().getSimpleName());
		}

		Integer counter = 0;

		if (thelist == theCarList) { 
			for (int i = 0; i < thelist.size(); i++) {
				Car curCar = (Car) thelist.get(i); 
				if (input.contains(curCar.getMake().toLowerCase()) 
						|| input.contains(curCar.getCarID().toLowerCase()) 
						|| input.contains(curCar.getMake().toLowerCase())) { 
					counter = i;
					yesorno.set(0, "Yes we have " + curCar.getMake());
					yesorno.add(counter.toString());
					i = thelist.size() + 1; // force break
				}
			}
		}

		if (thelist == theEmployeeList) { 
			for (int i = 0; i < thelist.size(); i++) {
				Employee curemp = (Employee) thelist.get(i); 
				if (input.contains(curemp.getEmployeeID().toLowerCase()) 
						|| input.contains(curemp.getFName().toLowerCase()) 
						|| input.contains(curemp.getSName().toLowerCase()) 
						|| input.contains(curemp.getAddress().toLowerCase())
						|| input.contains(curemp.getEmail().toLowerCase())
						|| input.contains(curemp.getPostcode().toLowerCase())) { 
					counter = i;
					yesorno.set(0, "Yes we have such a Employee"); 
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}

		if (thelist == theCatalogList) { 
			for (int i = 0; i < thelist.size(); i++) {
				Catalog curcat = (Catalog) thelist.get(i); 
				if (input.contains(curcat.getCatalogID().toLowerCase()) 
						|| input.contains(curcat.getModel().toLowerCase())) { 
					counter = i;
					yesorno.set(0, "Yes we have such a Catalog"); 
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}

		if (thelist == theLendingList) {
			for (int i = 0; i < thelist.size(); i++) {
				Lending curlend = (Lending) thelist.get(i); 
				if (input.contains(curlend.getCarID().toLowerCase()) 
						|| input.contains(curlend.getCustomerID().toLowerCase())) {

					counter = i;
					yesorno.set(0, "Yes we have such a Lending"); 
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}

		if (classtype.isEmpty()) {
			System.out.println("Not class type given.");
		} else {
			System.out.println("URL = " + URL);
			tooltipstring = readwebsite(URL);
			String html = "<html>" + tooltipstring + "</html>";
			Myface.setmytooltip(html);
			Myface.setmyinfobox(URL2);
		}

		return yesorno;
	}

	// Method to retrieve the location information from the object (Where is...) kind
	public String Location(List classtypelist, String input) {
		List thelist = classtypelist;
		String location = "";
		// if a pronomial was used "it", "them" etc: Reference to the recent thing
		if (thelist == theRecentThing && theRecentThing.get(0) != null) {
			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("Car")) { 
				Car curCar = (Car) theRecentThing.get(0);
			}

			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("member")) { 

				Customer curmem = (Customer) theRecentThing.get(0); 
				location = (curmem.getAddress() + " " + curmem.getMobileNo() + " " + curmem 
						.getEmail()); 
			}

			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("catalog")) { 

				Catalog curcat = (Catalog) theRecentThing.get(0); 
				location = (curcat.getLocation() + " "); 
			}

			if (theRecentThing.get(0).getClass().getSimpleName().toLowerCase().equals("library")) {

				location = (ourrental.getCity() + " " + ourrental.getAddress() + ourrental 
						.getPostcode()); 
			}

		}

		// if a direct noun was used (Car, member, etc)
		else {

			if (thelist == theCarList) { 

				int counter = 0;

				for (int i = 0; i < thelist.size(); i++) {

					Car curCar = (Car) thelist.get(i); 

					if (input.contains(curCar.getMake().toLowerCase())
							|| input.contains(curCar.getCarID().toLowerCase()) 
							|| input.contains(curCar.getMake().toLowerCase())) { 
						counter = i;
						
						Currentindex = counter;
						theRecentThing.clear(); // Clear it before adding (changing) theRecentThing
						classtype = theCarList; 
						theRecentThing.add(classtype.get(Currentindex));
						i = thelist.size() + 1; // force break
					}
				}
			}

			if (thelist == theEmployeeList) { 
				int counter = 0;
				for (int i = 0; i < thelist.size(); i++) {
					Customer curmember = (Customer) thelist.get(i);

					if (input.contains(curmember.getFName().toLowerCase()) 
							|| input.contains(curmember.getSName().toLowerCase()) 
							|| input.contains(curmember.getCustomerID().toLowerCase())) {
						counter = i;
						location = (curmember.getAddress() + " ");
						Currentindex = counter;
						theRecentThing.clear(); 
						classtype = theEmployeeList; 
						theRecentThing.add(classtype.get(Currentindex));
						i = thelist.size() + 1; // force break
					}
				}
			}

			if (thelist == theCatalogList) { 
				int counter = 0;
				for (int i = 0; i < thelist.size(); i++) {
					Catalog curcatalog = (Catalog) thelist.get(i);
					if (input.contains(curcatalog.getLocation().toLowerCase()) 
							|| input.contains(curcatalog.getMake().toLowerCase())) { 
						counter = i;
						location = (curcatalog.getLocation() + " ");
						Currentindex = counter;
						theRecentThing.clear(); 
						classtype = theCatalogList; 
						theRecentThing.add(classtype.get(Currentindex));
						i = thelist.size() + 1; // force break
					}
				}
			}

			if (thelist == ourrentalList) {

				location = (ourrental.getCity() + " " + ourrental.getAddress() + ourrental 
						.getPostcode());
			}
		}

		System.out.println("URL = " + URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return location;
	}

	public String testit() { // test the loaded knowledge by querying for Cars written by dostoyjewski
		String answer = "";
		System.out.println("Car List = " + theCarList.size()); 

		for (int i = 0; i < theCarList.size(); i++) { // check each Car in the List, //This is a candidate for a name change

			Car curCar = (Car) theCarList.get(i); // cast list element to Car Class
			System.out.println("Car: " + curCar.getMake());

			if (curCar.getMake().equalsIgnoreCase("dostoyjewski")) { // check for the author
				answer = "A Car written by " + curCar.getMake() + "\n" 
						+ " is for example the classic " + curCar.getMake() 
						+ ".";
			}
		}
		return answer;
	}

	public String readwebsite(String url) {
		String webtext = "";
		try {
			BufferedReader readit = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			String lineread = readit.readLine();
			System.out.println("Reader okay");
			while (lineread != null) {
				webtext = webtext + lineread;
				lineread = readit.readLine();
			}

			// Hard coded cut out from "wordnet website source text": Check if website still has this structure 

			webtext = webtext.substring(webtext.indexOf("<ul>"), webtext.indexOf("</ul>")); 

			webtext = "<table width=\"700\"><tr><td>" + webtext + "</ul></td></tr></table>";

		} catch (Exception e) {
			webtext = "Not yet";
			System.out.println("Error connecting to wordnet");
			
		}
		return webtext;
	}
}
