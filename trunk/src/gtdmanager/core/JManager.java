package gtdmanager.core;

import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
//import java.awt.Color;
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

    //private String projectLastNameNode = "";
    private boolean projectNameNodeFound = false;

    //private String instLastNameNode = "";
    private boolean instNameNodeFound = false;

    private String actLastNameNode = "";
    private boolean actNameNodeFound = false;


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


    // --------------------------------------------------------------------------------------


    private void processDependencyPropertyNode(Node propNode, JDependency dep) {

        String lastPropName = "";
        String nodeName = propNode.getNodeName();

        if (nodeName.equalsIgnoreCase("property")) {
            if (propNode.hasAttributes()) {
                NamedNodeMap nodeMap = propNode.getAttributes();
                for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {

                    String itemName = nodeMap.item(nodeIdx).getNodeName();
                    String itemValue = nodeMap.item(nodeIdx).getNodeValue();

                    if (itemName.equalsIgnoreCase("name")) {
                        lastPropName = itemValue;
                    } else if (itemName.equalsIgnoreCase("value")) {
                        if (lastPropName.equalsIgnoreCase("color")) {
                            dep.setColor(getColorInt(itemValue));
                        } else if (lastPropName.equalsIgnoreCase("line")) {
                            dep.setLine(itemValue);
                        }
                    }

                }
            }
        }
    }

    private void processDependencyNode(Node depNode, JActivity act) {

        JDependency dep = new JDependency();

        if (depNode.hasAttributes()) {

            NamedNodeMap nodeMap = depNode.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {

                String itemName = nodeMap.item(nodeIdx).getNodeName();
                String itemValue = nodeMap.item(nodeIdx).getNodeValue();

                if (itemName.equalsIgnoreCase("type")) {

                    if (itemValue.equalsIgnoreCase("begin-begin")) {
                        dep.setDependencyType(dep.BEGINBEGIN);
                    } else if (itemValue.equalsIgnoreCase("begin-end")) {
                        dep.setDependencyType(dep.BEGINEND);
                    } else if (itemValue.equalsIgnoreCase("end-begin")) {
                        dep.setDependencyType(dep.ENDBEGIN);
                    } else if (itemValue.equalsIgnoreCase("end-end")) {
                        dep.setDependencyType(dep.ENDEND);
                    }

                } else if (itemName.equalsIgnoreCase("activity-id")) {

                    dep.setToActivityId(Integer.parseInt(itemValue.substring(1)));

                }
            }
        }

        if (depNode.hasChildNodes()) {

            NodeList nodeList = depNode.getChildNodes();
            for (int nodeIdx = 0; nodeIdx < nodeList.getLength(); nodeIdx++) {
                processDependencyPropertyNode(nodeList.item(nodeIdx), dep);
            }
        }

        act.dependencies.add(dep);
    }

    private void processActDateNode(Node dateNode, JActivity act) {

        int dateInt = -1;
        int monthInt = -1;
        int yearInt = -1;

        String nodeName = dateNode.getNodeName();
        //System.out.println("       Date-Node found '" + nodeName + "'");

        if (dateNode.hasAttributes()) {

            NamedNodeMap nodeMap = dateNode.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {
                String itemName = nodeMap.item(nodeIdx).getNodeName();
                String itemValue = nodeMap.item(nodeIdx).getNodeValue();
                //System.out.println("    DateAttrib found: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());
                if (itemName.equalsIgnoreCase("date")) {
                    dateInt = Integer.parseInt(itemValue);
                } else if (itemName.equalsIgnoreCase("month")) {
                    monthInt = Integer.parseInt(itemValue);
                } else if (itemName.equalsIgnoreCase("year")) {
                    yearInt = Integer.parseInt(itemValue);
                }
            }

            if ((dateInt >= 0) && (monthInt >= 0) && (yearInt >= 0)) {

                Calendar c = Calendar.getInstance();
                c.set(yearInt, monthInt, dateInt);

                if (nodeName.equalsIgnoreCase("start-date")) {
                    act.setStartDate(c);
                } else if (nodeName.equalsIgnoreCase("end-date")) {
                    act.setEndDate(c);
                } else {
                    System.out.println("Unbekannter Datumsknoten in activity: " + nodeName);
                }

            } else { // eines oder mehrere der attribute date, month und year wurden nicht gefunden
                System.out.println("Der Datumsknoten '" + nodeName + "' besitzt nicht genügend Attribute für ein korrektes Datum!");
            }
        } else {
            System.out.println("Der Datumsknoten '" + nodeName + "' besitzt keine Attribute!");
        }
    }

    private int getColorInt(String colorStr) {

        int retColor = 0;

        if (colorStr.startsWith("0x")) { // example: given as 0xFFFF0000
            retColor = Integer.parseInt(colorStr.substring(2), 16);
        } else if (colorStr.startsWith("#")) { // example: given as #FF0000
            retColor = Integer.parseInt(colorStr.substring(1), 16);
        } else if (colorStr.startsWith("0") && (colorStr.length() > 1)) {
            retColor = Integer.parseInt(colorStr.substring(1), 8);
        } else if (colorStr.equalsIgnoreCase("white")) {
            retColor = Integer.parseInt("FFFFFF", 16);
        } else if (colorStr.equalsIgnoreCase("black")) {
            retColor = Integer.parseInt("000000", 16);
        } else if (colorStr.equalsIgnoreCase("red")) {
            retColor = Integer.parseInt("FF0000", 16);
        } else if (colorStr.equalsIgnoreCase("lime")) {
            retColor = Integer.parseInt("00FF00", 16);
        } else if (colorStr.equalsIgnoreCase("blue")) {
            retColor = Integer.parseInt("0000FF", 16);
        } else if (colorStr.equalsIgnoreCase("yellow")) {
            retColor = Integer.parseInt("FFFF00", 16);
        } else if (colorStr.equalsIgnoreCase("fuchsia")) {
            retColor = Integer.parseInt("FF00FF", 16);
        } else if (colorStr.equalsIgnoreCase("silver")) {
            retColor = Integer.parseInt("C0C0C0", 16);
        } else if (colorStr.equalsIgnoreCase("maroon")) {
            retColor = Integer.parseInt("800000", 16);
        } else if (colorStr.equalsIgnoreCase("purple")) {
            retColor = Integer.parseInt("800080", 16);
        } else if (colorStr.equalsIgnoreCase("green")) {
            retColor = Integer.parseInt("008000", 16);
        } else if (colorStr.equalsIgnoreCase("olive")) {
            retColor = Integer.parseInt("808000", 16);
        } else if (colorStr.equalsIgnoreCase("navy")) {
            retColor = Integer.parseInt("000080", 16);
        } else if (colorStr.equalsIgnoreCase("teal")) {
            retColor = Integer.parseInt("008080", 16);
        } else if (colorStr.equalsIgnoreCase("aqua")) {
            retColor = Integer.parseInt("00FFFF", 16);
        } else {
            retColor = Integer.parseInt(colorStr);
            //System.out.println("Unbekannte Farbe gefunden: " + colorStr);
        }
        return retColor;
    }

    private String getColorStr(String hexStr) {

        if (hexStr.equalsIgnoreCase("#FFFFFF")) {
            return "white";
        } else if (hexStr.equalsIgnoreCase("#000000")) {
            return "black";
        } else if (hexStr.equalsIgnoreCase("#FF0000")) {
            return "red";
        } else if (hexStr.equalsIgnoreCase("#00FF00")) {
            return "lime";
        } else if (hexStr.equalsIgnoreCase("#0000FF")) {
            return "blue";
        } else if (hexStr.equalsIgnoreCase("#FFFF00")) {
            return "yellow";
        } else if (hexStr.equalsIgnoreCase("#FF00FF")) {
            return "fuchsia";
        } else if (hexStr.equalsIgnoreCase("#C0C0C0")) {
            return "silver";
        } else if (hexStr.equalsIgnoreCase("#800000")) {
            return "maroon";
        } else if (hexStr.equalsIgnoreCase("#800080")) {
            return "purple";
        } else if (hexStr.equalsIgnoreCase("#008000")) {
            return "green";
        } else if (hexStr.equalsIgnoreCase("#808000")) {
            return "olive";
        } else if (hexStr.equalsIgnoreCase("#000080")) {
            return "navy";
        } else if (hexStr.equalsIgnoreCase("#008080")) {
            return "teal";
        } else if (hexStr.equalsIgnoreCase("#00FFFF")) {
            return "aqua";
        }
        return hexStr;
    }

    private void processActivityPropertyNode(Node childNode, JActivity act) {
        // Bsp: <property name="color" value="red" />
        if (childNode.getAttributes().getLength() >= 2) {
            if ((childNode.getAttributes().item(0).getNodeName().equalsIgnoreCase("name")) &&
                (childNode.getAttributes().item(1).getNodeName().equalsIgnoreCase("value"))) {

                String nameStr = childNode.getAttributes().item(0).getNodeValue();
                String valueStr = childNode.getAttributes().item(1).getNodeValue();

                if (nameStr.equalsIgnoreCase("color")) {
                    act.setColor(getColorInt(valueStr)); // z.b. color "red" in int-zahl umwandeln
                }
            }
        }
    }

    private void processActivityChildNode(Node childNode, JActivity act) {
        // Kindknoten eines activity-Knotens: name, short-name, start-date, end-date, property, #text, activity*, dependency*

        short nodeType = childNode.getNodeType();
        String nodeName = childNode.getNodeName();

        switch (nodeType) {
        case Node.ELEMENT_NODE:
            // Elemente des instance-Knotens: name, short-name, start-date, end-date, activity*
            //System.out.println("ActivityElement found: " + childNode.getNodeName() + " = " + childNode.getNodeValue());

            actNameNodeFound = false;
            if ((nodeName.equalsIgnoreCase("name")) || (nodeName.equalsIgnoreCase("short-name"))) {
                actNameNodeFound = true; // muss true sein, damit ein übergebener Text als name identifiziert werden kann
                actLastNameNode = nodeName;
                if (childNode.hasChildNodes()) {
                    processActivityChildNode(childNode.getFirstChild(), act);
                }
            } else if (nodeName.endsWith("-date")) {
                processActDateNode(childNode, act);
            } else if (nodeName.equalsIgnoreCase("property")) {
                processActivityPropertyNode(childNode, act);
            } else if (nodeName.equalsIgnoreCase("dependency")) {
                processDependencyNode(childNode, act);
            } else if (nodeName.equalsIgnoreCase("activity")) {
                processActivityNode(childNode, act.activities);
                //System.out.println("Instanz gefunden");
            } else {
                System.out.println("Unbekanntes Element innerhalb eines activity-Knotens der XML-Datei: " + nodeName + " = " + childNode.getNodeValue());
            }
            break;

        case Node.TEXT_NODE:
            // Text des instance-Knotens: Inhalt von name
            String nodeValue = childNode.getNodeValue();

            // das erste Zeichen des nodeValue-Strings wird auf das Zeichen #10 (Ascii-Code 10) untersucht
            // nicht gerade schön, Verbesserungsvorschläge erwünscht
            if ((nodeValue.charAt(0) != 10) && (nodeValue.charAt(0) != 13)) { // no carriage return and tabs
                //System.out.println("   Text found: " + nodeName + " = " + nodeValue);

                if (actNameNodeFound) {

                    if (actLastNameNode.equalsIgnoreCase("name")) {
                        act.setName(childNode.getNodeValue());
                    } else if (actLastNameNode.equalsIgnoreCase("short-name")) {
                        act.setShortName(childNode.getNodeValue());
                    }
                    actNameNodeFound = false;
                } else { // kein name-Knoten vor diesem Text gefunden
                    System.out.println("Unbekannter Text gefunden: " + nodeName + " = " + nodeValue);
                }
                actNameNodeFound = false;
            }
            break;

        default:
            // Unbekannter Knoten im instance-Knoten
            System.out.println("Unbekannter Knoten in instance-Knoten gefunden: " + nodeName + " = " + childNode.getNodeValue());
            break;
        }

    }

    private void processActivityNode(Node actNode, ArrayList activities) {
        // Parameter: actNode:    der activity-Knoten des XML-Dokuments
        //            activities: die ArrayList, in welche diese activity eingefügt werden soll

        JActivity act = new JActivity();

        if (actNode.hasAttributes()) {
            if (actNode.getAttributes().item(0).getNodeName().equalsIgnoreCase("id")) {
                String idStr = actNode.getAttributes().item(0).getNodeValue();
                act.id = Integer.parseInt(idStr.substring(1));
                // das erste Zeichen des id-Strings wird ignoriert (id="A3" => id=3)
            }
        }

        if (actNode.hasChildNodes()) {
        // childNodes des actNode: name, short-name, start-date, end-date, #text, activity*

            NodeList nodeList = actNode.getChildNodes();
            for (int nodeIdx = 0; nodeIdx < nodeList.getLength(); nodeIdx++) {

                processActivityChildNode(nodeList.item(nodeIdx), act);

            }
        }

        activities.add(act);
    }

    private void processInstDateNode(Node dateNode, JInstance destInst) {

        int dateInt = -1;
        int monthInt = -1;
        int yearInt = -1;

        String nodeName = dateNode.getNodeName();
        //System.out.println("       Date-Node found '" + nodeName + "'");

        if (dateNode.hasAttributes()) {

            NamedNodeMap nodeMap = dateNode.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {
                String itemName = nodeMap.item(nodeIdx).getNodeName();
                String itemValue = nodeMap.item(nodeIdx).getNodeValue();
                //System.out.println("    DateAttrib found: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());
                if (itemName.equalsIgnoreCase("date")) {
                    dateInt = Integer.parseInt(itemValue);
                } else if (itemName.equalsIgnoreCase("month")) {
                    monthInt = Integer.parseInt(itemValue);
                } else if (itemName.equalsIgnoreCase("year")) {
                    yearInt = Integer.parseInt(itemValue);
                }
            }

            if ((dateInt >= 0) && (monthInt >= 0) && (yearInt >= 0)) {

                Calendar c = Calendar.getInstance();
                c.set(yearInt, monthInt, dateInt);

                if (nodeName.equalsIgnoreCase("creation-date")) {
                    destInst.setCreationDate(c);
                } else if (nodeName.equalsIgnoreCase("start-date")) {
                    destInst.setStartDate(c);
                } else if (nodeName.equalsIgnoreCase("end-date")) {
                    destInst.setEndDate(c);
                } else {
                    System.out.println("Unbekannter Datumsknoten in instance: " + nodeName);
                }

            } else { // eines oder mehrere der attribute date, month und year wurden nicht gefunden
                System.out.println("Der Datumsknoten '" + nodeName + "' besitzt nicht genügend Attribute für ein korrektes Datum!");
            }
        } else {
            System.out.println("Der Datumsknoten '" + nodeName + "' besitzt keine Attribute!");
        }
    }

    private void processInstanceChildNode(Node childNode, JInstance destInst) {
        // Kindknoten des instance-Knoten: name, creation-date, start-date, end-date, #text, activity*

        short nodeType = childNode.getNodeType();
        String nodeName = childNode.getNodeName();
        //System.out.println("instChild: " + nodeName + " = " + childNode.getNodeValue());

        switch (nodeType) {
        case Node.ELEMENT_NODE:
            // Elemente des instance-Knotens: name, creation-date, start-date, end-date, activity*
            //System.out.println("InstanceElement found: " + childNode.getNodeName() + " = " + childNode.getNodeValue());

            instNameNodeFound = false;
            if (nodeName.equalsIgnoreCase("name")) {
                instNameNodeFound = true; // muss true sein, damit ein übergebener Text als name identifiziert werden kann
                if (childNode.hasChildNodes()) {
                    processInstanceChildNode(childNode.getFirstChild(), destInst);
                }
            } else if (nodeName.endsWith("-date")) {
                processInstDateNode(childNode, destInst);
            } else if (nodeName.equalsIgnoreCase("activity")) {
                processActivityNode(childNode, destInst.activities);
                //System.out.println("Instanz gefunden");
            } else {
                System.out.println("Unbekanntes Element innerhalb eines instance-Knotens der XML-Datei: " + nodeName + " = " + childNode.getNodeValue());
            }
            break;

        case Node.TEXT_NODE:
            // Text des instance-Knotens: Inhalt von name
            String nodeValue = childNode.getNodeValue();

            // das erste Zeichen des nodeValue-Strings wird auf das Zeichen #10 (Ascii-Code 10) untersucht
            // nicht gerade schön, Verbesserungsvorschläge erwünscht
            if ((nodeValue.charAt(0) != 10) && (nodeValue.charAt(0) != 13)) { // no carriage return and tabs
                //System.out.println("   Text found: " + nodeName + " = " + nodeValue);

                if (instNameNodeFound) {
                    destInst.setName(nodeValue);
                    instNameNodeFound = false;
                } else { // kein name-Knoten vor diesem Text gefunden
                    System.out.println("Unbekannter Text gefunden: " + nodeName + " = " + nodeValue);
                }
                instNameNodeFound = false;
            }
            break;

        default:
            // Unbekannter Knoten im instance-Knoten
            System.out.println("Unbekannter Knoten in instance-Knoten gefunden: " + nodeName + " = " + childNode.getNodeValue());
            break;
        }
    }

    private void processInstanceNode(Node instNode) {

        JInstance inst = project.newEmptyInstance();

        if (instNode.hasChildNodes()) {
            // childNodes des instNode: name, creation-date, start-date, end-date, #text, activity*

            NodeList nodeList = instNode.getChildNodes();
            for (int nodeIdx = 0; nodeIdx < nodeList.getLength(); nodeIdx++) {

                processInstanceChildNode(nodeList.item(nodeIdx), inst);
//                System.out.println("Instanz gefunden: " + instNode.getNodeName());

            }
        }

    }

    private void processProjectPropertyNode(Node propertyNode) {
        // durchsucht die Attribute eines property-Knoten innerhalb des project-Knotens

        String lastPropName = ""; // hier wird der Property-Name gespeichert, um den folgenden Wert zuordnen zu können

        if (propertyNode.hasAttributes()) { // Attribute einer Project-Property: name, value

            NamedNodeMap nodeMap = propertyNode.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {

                String itemName = nodeMap.item(nodeIdx).getNodeName();
                //System.out.println("    propertyChild found: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());

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
        //System.out.println("ProjChild: " + nodeName + " = " + childNode.getNodeValue());

        switch (nodeType) {
        case Node.ELEMENT_NODE:
            // Elemente des project-Knotens: name, property, instance
            //System.out.println("Element found: " + childNode.getNodeName() + " = " + childNode.getNodeValue());

            projectNameNodeFound = false;
            if (nodeName == "name") {
                projectNameNodeFound = true; // muss true sein, damit ein übergebener Text als name identifiziert werden kann
                if (childNode.hasChildNodes()) {
                    processProjectChildNode(childNode.getFirstChild());
                }
            } else if (nodeName == "property") {
                processProjectPropertyNode(childNode);
            } else if (nodeName == "instance") {
                processInstanceNode(childNode);
                //System.out.println("Instanz gefunden");
            } else {
                System.out.println("Unbekanntes Element innerhalb des project-Knotens der XML-Datei: " + nodeName + " = " + childNode.getNodeValue());
            }
            break;

        case Node.TEXT_NODE:
            // Text des project-Knotens: Inhalt von name
            String nodeValue = childNode.getNodeValue();

            // das erste Zeichen des nodeValue-Strings wird auf das Zeichen #10 (Ascii-Code 10) untersucht
            // nicht gerade schön, Verbesserungsvorschläge erwünscht
            if ((nodeValue.charAt(0) != 10) && (nodeValue.charAt(0) != 13)) { // no carriage return and tabs
                //System.out.println("   Text found: " + nodeName + " = " + nodeValue);

                if (projectNameNodeFound) {
                    project.setName(nodeValue);
                    projectNameNodeFound = false;
                } else { // kein name-Knoten vor diesem Text gefunden
                    System.out.println("Unbekannter Text gefunden: " + nodeName + " = " + nodeValue);
                }
                projectNameNodeFound = false;
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

    }

    private void processProjectNode(Node projectNode) {
        // wird nur mit dem project-Knoten aufgerufen

        if (projectNode.hasAttributes()) {
            // Attribue des projectNode: vendor-id, version

            NamedNodeMap nodeMap = projectNode.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {

                String itemName = nodeMap.item(nodeIdx).getNodeName();
                //System.out.println("    projectChild found: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());

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
                //System.out.println("  projectNode found: " + nodeName + " = " + docChild.getNodeValue());

                if (!projectNodeFound) {
                    projectNodeFound = true;
                    //lastNameNode = "project";
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

    private String getColorInXML(int color) {
        String s = Integer.toHexString(color);
        while (s.length() < 6) {
            s = "0" + s;
        }
        return getColorStr("#"+ s);
    }

    private void writeDependency(PrintWriter pWriter, JDependency dep, String tabs) {
        pWriter.println(tabs + "<dependency type=\"" + dep.getDependencyTypeStr() + "\" activity-id=\"A" + dep.getToActivityId() + "\">");
        pWriter.println(tabs + "\t<property name=\"color\" value=\"" + getColorInXML(dep.getColor()) + "\" />");
        pWriter.println(tabs + "\t<property name=\"line\" value=\"" + dep.getLine() + "\" />");
        pWriter.println(tabs + "</dependency>");
    }

    private void writeActivity(PrintWriter pWriter, JActivity act, String tabs) {
        pWriter.println(tabs + "<activity id=\"A" + act.getId() + "\">");
        pWriter.println(tabs + "\t<name>" + act.getName() + "</name>");
        pWriter.println(tabs + "\t<short-name>" + act.getShortName() + "</short-name>");
        pWriter.println(tabs + "\t<start-date date=\"" + act.getStartDate().get(Calendar.DATE) + "\" month=\"" + act.getStartDate().get(Calendar.MONTH) + "\" year=\"" + act.getStartDate().get(Calendar.YEAR) + "\" />");
        pWriter.println(tabs + "\t<end-date date=\"" + act.getEndDate().get(Calendar.DATE) + "\" month=\"" + act.getEndDate().get(Calendar.MONTH) + "\" year=\"" + act.getEndDate().get(Calendar.YEAR) + "\" />");
        pWriter.println(tabs + "\t<property name=\"color\" value=\"" + getColorInXML(act.getColor()) + "\" />");

        for (int i = 0; i < act.dependencies.size(); i++) {
            writeDependency(pWriter, (JDependency)act.dependencies.get(i), tabs + "\t");
        }

        for (int i = 0; i < act.activities.size(); i++) {
            writeActivity(pWriter, (JActivity)act.activities.get(i), tabs + "\t");
        }

        pWriter.println(tabs + "</activity>");
    }

    private void writeInstance(PrintWriter pWriter, JInstance inst) {
        pWriter.println("\t<instance>");
        pWriter.println("\t\t<name>" + inst.getName() + "</name>");
        pWriter.println("\t\t<creation-date date=\"" + inst.getCreationDate().get(Calendar.DATE) + "\" month=\"" + inst.getCreationDate().get(Calendar.MONTH) + "\" year=\"" + inst.getCreationDate().get(Calendar.YEAR) + "\" />");
        pWriter.println("\t\t<start-date date=\"" + inst.getStartDate().get(Calendar.DATE) + "\" month=\"" + inst.getStartDate().get(Calendar.MONTH) + "\" year=\"" + inst.getStartDate().get(Calendar.YEAR) + "\" />");
        pWriter.println("\t\t<end-date date=\"" + inst.getEndDate().get(Calendar.DATE) + "\" month=\"" + inst.getEndDate().get(Calendar.MONTH) + "\" year=\"" + inst.getEndDate().get(Calendar.YEAR) + "\" />");

        for (int i = 0; i < inst.activities.size(); i++) {
            writeActivity(pWriter, (JActivity)inst.activities.get(i), "\t\t");
        }

        pWriter.println("\t</instance>");
    }

    public void saveProject(String fileName) {
        // saves project in xml-file

        try {

            File fileOut = new File(fileName);
            FileWriter fWriter = new FileWriter(fileOut);
            PrintWriter pWriter = new PrintWriter(new BufferedWriter(fWriter));

            pWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pWriter.println("<!DOCTYPE project SYSTEM \"gtdmanager.dtd\">");
            pWriter.println("<project vendor-id=\"" + project.getAuthor() + "\" version=\"" + project.getVersion() + "\">");

            pWriter.println("\t<name>" + project.getName() + "</name>");
            pWriter.println("");
            pWriter.println("\t<property name=\"size-x\" value=\"" + getSizeX() + "\" />");
            pWriter.println("\t<property name=\"size-y\" value=\"" + getSizeY() + "\" />");
            pWriter.println("\t<property name=\"unit\" value=\"" + getUnit() + "\" />");
            pWriter.println("\t<property name=\"font-size\" value=\"" + getFontSize() + "\" />");
            pWriter.println("");

            for (int i = 0; i < project.instances.size(); i++) {
                writeInstance(pWriter, (JInstance)project.instances.get(i));
            }

            pWriter.println("</project>");

            pWriter.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
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

        /*newProject("Testprojekt", "1.0.0.0");
	

	JInstance i;
	JActivity a1, a2, a3, a4, a5, a6, a7, a8, a9;

	i	= getProject().getInstance(getProject().newInstance(
		"Instanz1",
		new GregorianCalendar(2005, 6, 1),		// create
		new GregorianCalendar(2005, 6, 1),		// start
		new GregorianCalendar(2005, 6, 30), true));	// end

        a1	= i.getActivity(i.newActivity(i.activities,
		"Aktivi1", "Akt1",
		new GregorianCalendar(2005, 6, 2),
		new GregorianCalendar(2005, 6, 6), 0));

        a2	= i.getActivity(i.newActivity(i.activities,
		"Aktivi2", "Akt2",
		new GregorianCalendar(2005, 6, 7),
		new GregorianCalendar(2005, 6, 17), 0));

        a3	= i.getActivity(i.newActivity(i.activities,
		"Aktivi3", "Akt3",
		new GregorianCalendar(2005, 6, 14),
		new GregorianCalendar(2005, 6, 29), 0));

	a1.setColor(0xff0000);
	a2.setColor(0x00ff00);
	a3.setColor(0x0000ff);*/

	// tv: *** WENN DASS ENABLED IST, ALLES ARSCH ***
	/*i	= getProject().getInstance(getProject().newInstance(
		"Instanz2",
		new GregorianCalendar(2005, 6, 6),
		new GregorianCalendar(2005, 6, 2),
		new GregorianCalendar(2005, 6, 29), true));

       	i.getActivity(a1.getId()).setEndDate(
		new GregorianCalendar(2005, 6, 11));

	i	= getProject().getInstance(getProject().newInstance(
		"Instanz3",
		new GregorianCalendar(2005, 6, 8),
		new GregorianCalendar(2005, 6, 2),
		new GregorianCalendar(2005, 6, 34), true));*/

       /*i.getActivity(a1.getId()).setStartDate(new GregorianCalendar(2005,6,9));
       	i.getActivity(a1.getId()).setEndDate(new GregorianCalendar(2005,6,18));
       i.getActivity(a2.getId()).setStartDate(new GregorianCalendar(2005,6,12));
       	i.getActivity(a2.getId()).setEndDate(new GregorianCalendar(2005,6,24));
*/
	// for DiagramView testing
/*	a9	= i.getActivity(i.newActivity(i.activities, "ENDEND1", "EE1",
	  	new GregorianCalendar(2005, 6, 3),
	  	new GregorianCalendar(2005, 6, 7), 0));
	a1.newDependency(a9.getId(), JDependency.BEGINBEGIN);
	a1.newDependency(a9.getId(), JDependency.ENDEND);
	a9	= i.getActivity(i.newActivity(i.activities, "ENDEND2", "EE2",
	  	new GregorianCalendar(2005, 6, 15),
	  	new GregorianCalendar(2005, 6, 19), 0));
	a1.newDependency(a9.getId(), JDependency.BEGINBEGIN);
	a1.newDependency(a9.getId(), JDependency.ENDEND);
	a9	= i.getActivity(i.newActivity(i.activities, "ENDEND3", "EE3",
	  	new GregorianCalendar(2005, 6, 27),
	  	new GregorianCalendar(2005, 6, 31), 0));
	a1.newDependency(a9.getId(), JDependency.BEGINBEGIN);
	a1.newDependency(a9.getId(), JDependency.ENDEND);
	a9	= i.getActivity(i.newActivity(i.activities, "BEGINEND1", "BE1",
	  	new GregorianCalendar(2005, 6, 4),
	  	new GregorianCalendar(2005, 6, 8), 0));
	a2.newDependency(a9.getId(), JDependency.BEGINEND);
	a2.newDependency(a9.getId(), JDependency.ENDBEGIN);
	a9	= i.getActivity(i.newActivity(i.activities, "BEGINEND2", "BE2",
	  	new GregorianCalendar(2005, 6, 16),
	  	new GregorianCalendar(2005, 6, 20), 0));
	a2.newDependency(a9.getId(), JDependency.BEGINEND);
	a2.newDependency(a9.getId(), JDependency.ENDBEGIN);
	a9	= i.getActivity(i.newActivity(i.activities, "BEGINEND2", "BE2",
	  	new GregorianCalendar(2005, 6, 28),
	  	new GregorianCalendar(2005, 6, 32), 0));
	a2.newDependency(a9.getId(), JDependency.BEGINEND);
	a2.newDependency(a9.getId(), JDependency.ENDBEGIN);
*/



	//i.newActivity(i.activities,
	//	"Aktivi1", "Akt1",
	//	new GregorianCalendar(2005, 6, 2),
	//	new GregorianCalendar(2005, 6, 6), 0));

     //i.getActivity(a2.getId()).setStartDate(new GregorianCalendar(2005, 5, 17));
     //i.getActivity(a2.getId()).setEndDate(new GregorianCalendar(2005, 5, 21));

	//newProject();
	//loadProject("test.xml");
    }

    private void jbInit() throws Exception {
    }

}
