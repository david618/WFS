/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esri.wfs;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author david
 */
public class WFSTest {

    private void parse() {

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            Document dom = db.parse("/home/david/Documents/geoserver-getCapabilities.application.xml");

            Element docEle = dom.getDocumentElement();

            //NodeList nl = docEle.getElementsByTagName("FunctionName");
            NodeList nl = docEle.getChildNodes();
            if (nl != null && nl.getLength() > 0) {
                for (int i = 0; i < nl.getLength(); i++) {

                    //get the element
                    //

                    Node node = (Node) nl.item(i);
                    if(node.getNodeType() == Node.ELEMENT_NODE){
                        Element el = (Element) nl.item(i);
                    
                        //get the Element object
                        System.out.println(el.getNodeName());
                        
                        if (el.hasChildNodes()) {
                            
                            NodeList nl2 = el.getChildNodes();
                            
                            for (int j = 0; j < nl2.getLength(); j++) {
                                Node n2 = (Node) nl2.item(j);
                                
                                if (n2.getNodeType() == Node.ELEMENT_NODE) {
                                    Element el2 = (Element) n2;
                                    System.out.println(" " + el2.getNodeName());
                                }
                            }
                            
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validate() {
        try {
            // parse an XML document into a DOM tree
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            //Document document = parser.parse(new File("/home/david/schemas/wfs/1.1.0/examples/WFS_Capabilities_Sample.xml"));
            Document document = parser.parse(new File("/home/david/Documents/geoserver-getCapabilities.application.xml"));

            // create a SchemaFactory capable of understanding WXS schemas
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // load a WXS schema, represented by a Schema instance
            Source schemaFile = new StreamSource(new File("/home/david/schemas/wfs/1.1.0/wfs.xsd"));
            Schema schema = factory.newSchema(schemaFile);

            // create a Validator instance, which can be used to validate an instance document
            Validator validator = schema.newValidator();

            // validate the DOM tree
            try {
                validator.validate(new DOMSource(document));
            } catch (SAXException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Start");
        WFSTest a = new WFSTest();
        a.validate();

    }
}
