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

    int fontSize;
    String fileName;
    boolean modified;
    JProject project;

    private String lastNameNode = "";
    private boolean nameNodeFound = false;
    private String lastPropName = "";



    public JManager() {
        modified = false;
        fontSize = 10;
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

    public JProject getProject() {
        return this.project;
    }

    private void processProjectChildNode(Node childNode) {
/*
        if (childNode.hasAttributes()) {
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
        }
*/
    }

    private void processProjectNode(Node projectNode) {

        if (projectNode.hasAttributes()) {
            NamedNodeMap nodeMap = projectNode.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {

                String itemName = nodeMap.item(nodeIdx).getNodeName();
                System.out.println("    projectChild found: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());

                if (itemName == "vendor-id") {
                    //project.setAuthor(nodeMap.item(nodeIdx).getNodeValue());
                    // noch kein Autor in project
                } else if (itemName == "version") {
                    project.setVersion(nodeMap.item(nodeIdx).getNodeValue());
                }

            }
        }
/*
        if (projectNode.hasChildNodes()) {
            NodeList nodeList = projectNode.getChildNodes();
            for (int nodeIdx = 0; nodeIdx < nodeList.getLength(); nodeIdx++) {

                processProjectChildNode(nodeList.item(nodeIdx));

            }
        }
*/
    }

    private void processPropertyNode(Node propertyNode) {

        if (propertyNode.hasAttributes()) {
            NamedNodeMap nodeMap = propertyNode.getAttributes();
            for (int nodeIdx = 0; nodeIdx < nodeMap.getLength(); nodeIdx++) {

                String itemName = nodeMap.item(nodeIdx).getNodeName();
                System.out.println("    propertyChild found: " + itemName + " = " + nodeMap.item(nodeIdx).getNodeValue());

                if (lastNameNode == "project") {

                    if (itemName == "name") {
                        lastPropName = nodeMap.item(nodeIdx).getNodeValue();
                    } else if (itemName == "value") {
                        //System.out.println(lastPropName);
                        if (lastPropName == "font-size") {
// FEHLER: obwohl lastPropName = "font-size" ist, wird diese anweisung nicht ausgeführt
                            setFontSize(Integer.parseInt(nodeMap.item(nodeIdx).getNodeValue()));
                        } // else if (propName == "size-x") { ...
                    }

                } // else if (lastNameNode == "...") { ...

            }
        }
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
                processPropertyNode(document);
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

    public void loadProject(String fileName) {
        // loads project from xml-file

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder domBuilder = dbf.newDocumentBuilder();
            File file = new File(fileName);
            Document document = domBuilder.parse(file);
            processNode(document);

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

//	i	= getProject().getInstance(getProject().newInstance(
//		"Instanz2",
//		new GregorianCalendar(2005, 5, 12),
//		new GregorianCalendar(2005, 5, 1),
//		new GregorianCalendar(2005, 6, 30), true));

     //i.getActivity(a2.getId()).setStartDate(new GregorianCalendar(2005, 5, 17));
     //i.getActivity(a2.getId()).setEndDate(new GregorianCalendar(2005, 5, 21));
    }

    private void jbInit() throws Exception {
    }

}
