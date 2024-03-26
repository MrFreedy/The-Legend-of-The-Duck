package org.azal.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * The XMLReader class represents a utility class for reading XML files.
 */
public class XMLReader {
    /** The Document instance representing the XML file. */
    private Document doc;

    /**
     * Constructs a new XMLReader instance for reading the specified XML file.
     *
     * @param filePath The path to the XML file to be read.
     */
    public XMLReader(final String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the value of the specified tag name from the XML file.
     *
     * @param tagName The name of the tag to retrieve the value from.
     * @return The value of the specified tag name.
     */
    public String getValue(final String tagName) {
        NodeList nList = doc.getElementsByTagName(tagName);
        Node node = nList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return element.getTextContent();
        }
        return null;
    }

    /**
     * Retrieves the value of the specified child tag name from the specified parent tag name in the XML file.
     *
     * @param parentTagName The name of the parent tag to retrieve the value from.
     * @param childTagName The name of the child tag to retrieve the value from.
     * @return The value of the specified child tag name from the specified parent tag name.
     */
    public String getValue(final String parentTagName, final String childTagName) {
        NodeList nList = doc.getElementsByTagName(parentTagName);
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                NodeList childList = element.getElementsByTagName(childTagName);
                if (childList.getLength() > 0) {
                    return childList.item(0).getTextContent();
                }
            }
        }
        return null;
    }
}
