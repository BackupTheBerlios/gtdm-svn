package gtdmanager.core;

import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * <p>Title: JManager class</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Michael Fechter
 * @version 1.0
 */
public class JManager {

    int fontSize, sizeX, sizeY;
    String unit;

    String fileName;
    boolean modified;
    JProject project;

    private boolean projectNodeFound = false;

    private String lastNameNode = "";
    private boolean nameNodeFound = false;


    public JManager() {
        modified = false;
        fontSize = 8;
        sizeX = 8;
        sizeY = 8;
        unit = "mm";

        fileName = "";
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int size) {
        this.fontSize = size;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public void setSizeX(int size) {
        this.sizeX = size;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public void setSizeY(int size) {
        this.sizeY = size;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String strUnit) {
        this.unit = strUnit;
    }

    public JProject getProject() {
        return this.project;
    }

    private void processNode(Node document) {

        short nodeType = document.getNodeType();
        String nodeName = document.getNodeName();

        switch (nodeType) {
        case Node.ELEMENT_NODE:
            //...
            System.out.println("Element found: " + document.getNodeName() + " = " + document.getNodeValue());
            if (nodeName == "project") {
                processProjectNode(document);
                lastNameNode = "project";
            } else if (nodeName == "name") {
                nameNodeFound = true;
            } else if (nodeName == "property") {
                //processPropertyNode(document);
            }
            break;
        case Node.TEXT_NODE:
            //...
            String nodeValue = document.getNodeValue();

            if (nodeValue.charAt(0) != 10) { // no carriage return and tabs
                System.out.println("Text found: " + nodeName + " = " + document.getNodeValue());

                if (nameNodeFound) {
                    if (lastNameNode == "project") {
                        project.setName(document.getNodeValue());
                    } //else if (lastNameNode == "instance") { ...
                    nameNodeFound = false;
                }

                /*for (int i=0; i<nodeValue.length(); i++) {
                    System.out.print(Integer.toHexString(nodeValue.charAt(i)));
                }
                System.out.print("  len: ");
                System.out.println(nodeValue.length());*/
            }

            break;
        case Node.ATTRIBUTE_NODE:
            //...
            System.out.println("Attribute found: " + document.getNodeName() + " = " + document.getNodeValue());
            break;
        default:
            //...
            System.out.println("Other found: " + document.getNodeName() + " = " + document.getNodeValue());
            break;
        }

        if (document.hasAttributes()) {
            NamedNodeMap nodeMap = document.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {
                System.out.println("    Child found: " + nodeMap.item(nodeIdx).getNodeName() + " = " + nodeMap.item(nodeIdx).getNodeValue());
            }
        }

        if (document.hasChildNodes()) {
            NodeList nodeList = document.getChildNodes();
            for (int nodeIdx = 0; nodeIdx < nodeList.getLength(); nodeIdx++) {
                processNode(nodeList.item(nodeIdx));
            }
        }

    } // end of processNode


    // ----------------------------------------------------------------------


    private void processProjectPropertyNode(Node propertyNode) {
        // durchsucht die Attribute eines property-Knoten innerhalb des project-Knotens

        String lastPropName = ""; // hier wird der Property-Name gespeichert, um den folgenden Wert zuordnen zu können

        if (propertyNode.hasAttributes()) { // Attribute einer Project-Property: name, value

            NamedNodeMap nodeMap = propertyNode.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {

                String itemName = nodeMap.item(nodeIdx).getNodeName();
                System.out.println("    propertyChild found: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());

                if (itemName == "name") {
                    // gibt den Namen der Eigenschaft an, deren Wert im nächsten value-Attribut übergeben wird

                    lastPropName = nodeMap.item(nodeIdx).getNodeValue(); // z.B. size-x, size-y, unit, font-size

                } else if (itemName == "value") {
                    // gibt den Wert der Eigenschaft an, deren Name im vorherigen name-Attribut übergeben wurde

                    //boolean b = lastPropName.equalsIgnoreCase("size-x");
                    if (lastPropName.equalsIgnoreCase("font-size")) {
// FEHLER: obwohl lastPropName = "font-size" ist, wird diese anweisung nicht ausgeführt
                        setFontSize(Integer.parseInt(nodeMap.item(nodeIdx).getNodeValue())); // ???
                    } else if (lastPropName.equalsIgnoreCase("size-x")) {
                        setSizeX(Integer.parseInt(nodeMap.item(nodeIdx).getNodeValue()));
                    } else if (lastPropName.equalsIgnoreCase("size-y")) {
                        setSizeY(Integer.parseInt(nodeMap.item(nodeIdx).getNodeValue()));
                    } else if (lastPropName.equalsIgnoreCase("unit")) {
                        setUnit(nodeMap.item(nodeIdx).getNodeValue());
                    } else {

                        if (lastPropName == "") { // keine Zuordnung des value-Werts möglich
                            System.out.println("Kein name-Attribut vor diesem value-Attribut gefunden: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());
                        } else { // unbekannter property-Name
                            System.out.println("Unbekanntes name-Attribut vor diesem value-Attribut gefunden: " + lastPropName + "? : " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());
                        }

                    }
                    lastPropName = ""; // damit kein value-Attribut doppelt vorkommen und damit überschrieben werden kann

                } else {
                    System.out.println("Unbekanntes property-Attribut: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());
                }

            }
        } else { // property besitzt keine Attribute
            System.out.println("Ein property-Knoten innerhalb des project-Knotens besitzt keine Attribute!");
        }
    }

    private void processProjectChildNode(Node childNode) {
        // Kindknoten des project-Knoten: name, property, instance, #text

        short nodeType = childNode.getNodeType();
        String nodeName = childNode.getNodeName();

        switch (nodeType) {
        case Node.ELEMENT_NODE:
            // Elemente des project-Knotens: name, property, instance
            System.out.println("Element found: " + childNode.getNodeName() + " = " + childNode.getNodeValue());

            nameNodeFound = false;
            if (nodeName == "name") {
                nameNodeFound = true; // muss true sein, damit ein übergebener Text als name identifiziert werden kann
            } else if (nodeName == "property") {
                processProjectPropertyNode(childNode);
            } else if (nodeName == "instance") {
                //processInstanceNode(childNode);
                System.out.println("Instanz gefunden");
            } else {
                System.out.println("Unbekanntes Element innerhalb des project-Knotens der XML-Datei: " + nodeName + " = " + childNode.getNodeValue());
            }
            break;

        case Node.TEXT_NODE:
            // Text des project-Knotens: Inhalt von name
            String nodeValue = childNode.getNodeValue();

            // das erste Zeichen des nodeValue-Strings wird auf das Zeichen #10 (Ascii-Code 10) untersucht
            // nicht gerade schön, Verbesserungsvorschläge erwünscht
            if (nodeValue.charAt(0) != 10) { // no carriage return and tabs
                System.out.println("   Text found: " + nodeName + " = " + nodeValue);

                if (nameNodeFound) {
                    project.setName(childNode.getNodeValue());
                    nameNodeFound = false;
                } else { // kein name-Knoten vor diesem Text gefunden
                    System.out.println("Unbekannter Text gefunden: " + nodeName + " = " + nodeValue);
                }
                nameNodeFound = false;
            }
            break;

        /*case Node.ATTRIBUTE_NODE:
            //...
            System.out.println("Attribute found: " + document.getNodeName() + " = " + document.getNodeValue());
            break; */

        default:
            // Unbekannter Knoten im project-Knoten
            System.out.println("Unbekannter Knoten im project-Knoten gefunden: " + nodeName + " = " + childNode.getNodeValue());
            break;
        }

/*        if (childNode.hasAttributes()) {
            NamedNodeMap nodeMap = childNode.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {

                String itemName = nodeMap.item(nodeIdx).getNodeName();
                System.out.println("    projectChild found: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());

                if (itemName == "name") {

                    project.setName(nodeMap.item(nodeIdx).getNodeValue());
                } else if (itemName == "version") {
                    project.setVersion(nodeMap.item(nodeIdx).getNodeValue());
                }

            }
        }  */

    }

    private void processProjectNode(Node projectNode) {
        // wird nur mit dem project-Knoten aufgerufen

        if (projectNode.hasAttributes()) {
            // Attribue des projectNode: vendor-id, version

            NamedNodeMap nodeMap = projectNode.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {

                String itemName = nodeMap.item(nodeIdx).getNodeName();
                System.out.println("    projectChild found: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());

                if (itemName == "vendor-id") {
                    project.setAuthor(nodeMap.item(nodeIdx).getNodeValue());
                } else if (itemName == "version") {
                    project.setVersion(nodeMap.item(nodeIdx).getNodeValue());
                } else {
                    System.out.println("Unbekanntes Attribut des project-Knotens gefunden: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());
                }

            }
        } else {
            System.out.println("Der project-Knoten in der XML-Datei ist leer!");
        }

        if (projectNode.hasChildNodes()) {
            // childNodes des projectNode: name, property, instance

            NodeList nodeList = projectNode.getChildNodes();
            for (int nodeIdx = 0; nodeIdx < nodeList.getLength(); nodeIdx++) {

                processProjectChildNode(nodeList.item(nodeIdx));

            }
        }

    }


    private void processDocChildNode(Node docChild) {
        // wird mit allen Kindknoten des Dokuments aufgerufen (im Normalfall nur project, ansonsten Fehlermeldung)

        short nodeType = docChild.getNodeType();
        String nodeName = docChild.getNodeName();

        if (nodeType == Node.ELEMENT_NODE) {

            if (nodeName == "project") {
                System.out.println("  projectNode found: " + nodeName + " = " + docChild.getNodeValue());

                if (!projectNodeFound) {
                    projectNodeFound = true;
                    lastNameNode = "project";
                    processProjectNode(docChild);
                } else {
                    System.out.println("In der XML-Datei wurden mehrere project-Knoten gefunden!");
                }

            } else {
                System.out.println("Unbekanntes Element in der XML-Datei: " + nodeName + " = " + docChild.getNodeValue());
            }

        } else {
            //case Node.TEXT_NODE:
            //case Node.ATTRIBUTE_NODE:
            //default:
            if (nodeName.equalsIgnoreCase("project")) {
                // !DOCTYPE project ... // gibt verwendetes DTD an (überprüfen, verwenden?)
            } else {
                System.out.println("Unbekannter Knoten in der XML-Datei: " + nodeName + " = " + docChild.getNodeValue());
            }
        }

    } // end of processDocChildNode

    private void processDocument(Node document) {
        //System.out.println("DOC: " + document.getNodeName());
        //processNode(document); // for testing only

        if (document.hasChildNodes()) {

            NodeList nodeList = document.getChildNodes();
            for (int nodeIdx = 0; nodeIdx < nodeList.getLength(); nodeIdx++) {
                processDocChildNode(nodeList.item(nodeIdx));
            }

        } else {
            System.out.println("Es befinden sich keine Elemente in der XML-Datei!");
        }

    } // end of processDocument

    public void loadProject(String fileName) {
        // loads project from xml-file

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder domBuilder = dbf.newDocumentBuilder();
            File file = new File(fileName);
            Document document = domBuilder.parse(file);
            processDocument(document);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void saveProject(String fileName) {
        // saves project in xml-file

        try {

            File fileOut = new File(fileName);
            FileWriter fWriter = new FileWriter(fileOut);
            PrintWriter pWriter = new PrintWriter(new BufferedWriter(fWriter));
            //pWriter.println("Protokoll von heute");
            //pWriter.println("\tTest");
            pWriter.close();

        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }

    }

    public void newProject() {
        this.project = new JProject();
    }

    public void newProject(String strName, String strVersion) {
        this.project = new JProject();
        this.project.setName(strName);
        this.project.setVersion(strVersion);
    }

    public void generateSampleProject() {

        newProject("Testprojekt", "1.0.0.0");

	JInstance i;
	JActivity a1, a2, a3, a4, a5, a6, a7, a8, a9;

	i	= getProject().getInstance(getProject().newInstance(
		"Instanz1",
		new GregorianCalendar(2005, 5, 1),
		new GregorianCalendar(2005, 5, 1),
		new GregorianCalendar(2005, 6, 30), true));

        a1	= i.getActivity(i.newActivity(i.activities,
		"Aktivi1", "Akt1",
		new GregorianCalendar(2005, 5, 4),
		new GregorianCalendar(2005, 5, 12), 0));

        a2	= i.getActivity(i.newActivity(i.activities,
		"Aktivi2", "Akt2",
		new GregorianCalendar(2005, 5, 14),
		new GregorianCalendar(2005, 5, 20), 0));

        a3	= i.getActivity(i.newActivity(i.activities,
		"Aktivi3", "Akt3",
		new GregorianCalendar(2005, 5, 14),
		new GregorianCalendar(2005, 5, 19), 0));

        a1.newDependency(a2.getId(), JDependency.ENDBEGIN);

	i	= getProject().getInstance(getProject().newInstance(
		"Instanz2",
		new GregorianCalendar(2005, 5, 12),
		new GregorianCalendar(2005, 5, 1),
		new GregorianCalendar(2005, 6, 30), true));

     //i.getActivity(a2.getId()).setStartDate(new GregorianCalendar(2005, 5, 17));
     //i.getActivity(a2.getId()).setEndDate(new GregorianCalendar(2005, 5, 21));
    }

    private void jbInit() throws Exception {
    }

}
