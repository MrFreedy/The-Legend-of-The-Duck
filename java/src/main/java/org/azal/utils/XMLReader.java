package org.azal.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XMLReader {
    private Document doc;

    public XMLReader(String filePath) {
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

    public String getValue(String tagName) {
        NodeList nList = doc.getElementsByTagName(tagName);
        Node node = nList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            return element.getTextContent();
        }
        return null;
    }

    public String getValue(String parentTagName, String childTagName) {
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
