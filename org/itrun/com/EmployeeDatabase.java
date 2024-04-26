package org.itrun.com;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDatabase {


    private Map<String, Person> internalEmployees;
    private Map<String, Person> externalEmployees;

    private static final String INTERNAL_XML_FILE = "/Users/serglapidus/Documents/ITRun-Test-Task2/src/org/itrun/com/internal.xml";
    private static final String EXTERNAL_XML_FILE = "/Users/serglapidus/Documents/ITRun-Test-Task2/src/org/itrun/com/external.xml";

    public EmployeeDatabase() {
        internalEmployees = loadEmployeesFromFile(INTERNAL_XML_FILE);
        externalEmployees = loadEmployeesFromFile(EXTERNAL_XML_FILE);
    }

    public Person find(Type type, String firstName, String lastName, String mobile, String pesel) {
        Map<String, Person> employees = (type == Type.INTERNAL) ? internalEmployees : externalEmployees;
        for (Person employee : employees.values()) {
            if ((firstName == null || firstName.equals(employee.getFirstName()))
                    && (lastName == null || lastName.equals(employee.getLastName()))
                    && (mobile == null || mobile.equals(employee.getMobile()))
                    && (pesel == null || pesel.equals(employee.getPesel()))) {
                return employee;
            }
        }
        return null;
    }

    public void create(Person person, Type type) {
        Map<String, Person> employees = (type == Type.INTERNAL) ? internalEmployees : externalEmployees;
        employees.put(person.getPersonId(), person);
        saveEmployeesToFile(type);
    }

    public boolean remove(String personId, Type type) {
        Map<String, Person> employees = (type == Type.INTERNAL) ? internalEmployees : externalEmployees;
        if (employees.containsKey(personId)) {
            employees.remove(personId);
            saveEmployeesToFile(type);
            return true;
        }
        return false;
    }

    public void modify(Person person, Type type) {
        Map<String, Person> employees = (type == Type.INTERNAL) ? internalEmployees : externalEmployees;
        if (employees.containsKey(person.getPersonId())) {

            Person existingPerson = employees.get(person.getPersonId());

            existingPerson.setFirstName(person.getFirstName());
            existingPerson.setLastName(person.getLastName());
            existingPerson.setMobile(person.getMobile());
            existingPerson.setEmail(person.getEmail());
            existingPerson.setPesel(person.getPesel());

            saveEmployeesToFile(type);
            System.out.println("Person with ID " + person.getPersonId() + " modified successfully.");
        } else {
            System.out.println("Person with ID " + person.getPersonId() + " not found.");
        }
    }

    private Map<String, Person> loadEmployeesFromFile(String filename) {
        Map<String, Person> employees = new HashMap<>();
        try {
            File file = new File(filename);
            if (!file.exists()) {
                return employees;
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String personId = element.getElementsByTagName("personId").item(0).getTextContent();
                    String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                    String mobile = element.getElementsByTagName("mobile").item(0).getTextContent();
                    String email = element.getElementsByTagName("email").item(0).getTextContent();
                    String pesel = element.getElementsByTagName("pesel").item(0).getTextContent();
                    employees.put(personId, new Person(personId, firstName, lastName, mobile, email, pesel));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    private void saveEmployeesToFile(Type type) {
        Map<String, Person> employees = (type == Type.INTERNAL) ? internalEmployees : externalEmployees;
        String filename = (type == Type.INTERNAL) ? INTERNAL_XML_FILE : EXTERNAL_XML_FILE;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element rootElement = doc.createElement("Employees");
            doc.appendChild(rootElement);

            if (!employees.isEmpty()) {
                for (Person employee : employees.values()) {
                    Element personElement = doc.createElement("Person");

                    Element personId = doc.createElement("personId");
                    personId.appendChild(doc.createTextNode(employee.getPersonId()));
                    personElement.appendChild(personId);

                    Element firstName = doc.createElement("firstName");
                    firstName.appendChild(doc.createTextNode(employee.getFirstName()));
                    personElement.appendChild(firstName);

                    Element lastName = doc.createElement("lastName");
                    lastName.appendChild(doc.createTextNode(employee.getLastName()));
                    personElement.appendChild(lastName);

                    Element mobile = doc.createElement("mobile");
                    mobile.appendChild(doc.createTextNode(employee.getMobile()));
                    personElement.appendChild(mobile);

                    Element email = doc.createElement("email");
                    email.appendChild(doc.createTextNode(employee.getEmail()));
                    personElement.appendChild(email);

                    Element pesel = doc.createElement("pesel");
                    pesel.appendChild(doc.createTextNode(employee.getPesel()));
                    personElement.appendChild(pesel);

                    rootElement.appendChild(personElement);
                }
            }

            System.out.println("File path to save: " + filename);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);


            File tempFile = new File(filename + ".temp");


            StreamResult streamResult = new StreamResult(tempFile);
            transformer.transform(source, streamResult);


            File originalFile = new File(filename);
            if (originalFile.exists()) {
                originalFile.delete();
            }
            tempFile.renameTo(originalFile);

            System.out.println("Employees saved successfully to " + filename);
        } catch (Exception e) {
            e.printStackTrace();
            // Log the error message
            System.err.println("Error occurred while saving employees to file: " + e.getMessage());
        }
    }

    public enum Type {
        INTERNAL, EXTERNAL
    }
}