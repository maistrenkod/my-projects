package ru.skillbench.tasks.javax.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.util.ArrayList;

public class XPathCallerImpl implements XPathCaller {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        FileInputStream fis = new FileInputStream("emp-hier.xml");
        Document doc = db.parse(fis);
        XPathCaller a = new XPathCallerImpl();
        Element [] el = a.getCoworkers(doc,"7521","emp-hier");
        System.out.println(el.length);
        System.out.println(a.getHighestPayed(doc,"30", "emp"));
        //System.out.println(a.getHighestPayed(doc,"30", "emp-hier"));
    }

    public Element[] getEmployees(Document src, String deptno, String docType) {
        String expression = "//employee[@deptno =" + deptno +  "]";
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = null;
        try {
            xPathExpression = xpath.compile(expression);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        NodeList reasult = null;
        try {
            reasult = (NodeList)xPathExpression.evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        ArrayList<Element> ar = new ArrayList<>();
        for (int i = 0; i < reasult.getLength(); i++) {
            Node node = reasult.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element elem = (Element) node;
                ar.add(elem);
            }
        }
        Element [] elements = new Element[ar.size()];
        ar.toArray(elements);
        return elements;
    }

    public String getHighestPayed(Document src, String docType) {
        String expression = null;
        if (docType.matches("emp")) {
            expression = "//employee[not(sal < //employee/sal)]/ename";
        }
        if (docType.matches("emp-hier")){
            expression ="";
        }
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = null;
        try {
            xPathExpression = xpath.compile(expression);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        NodeList reasult = null;
        try {
            reasult = (NodeList)xPathExpression.evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return reasult.item(0).getTextContent();
    }

    public static String nodeToString(Node node) throws TransformerException {
        StringWriter sw = new StringWriter();
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();
    }

    public String getHighestPayed(Document src, String deptno, String docType) {
        String expression = null;
        if (docType.matches("emp")) {
            expression = "//employee[not(sal < //employee[@deptno = " + deptno + "]/sal)and @deptno = " + deptno + "]/ename";
        }
        if (docType.matches("emp-hier")){
            expression = "//employee[not(sal < //employee[@deptno = " + deptno + "]/sal)and @deptno = " + deptno + "]";
        }
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = null;
        try {
            xPathExpression = xpath.compile(expression);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        NodeList reasult = null;
        try {
            reasult = (NodeList)xPathExpression.evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return reasult.item(0).getTextContent();
    }

    public Element[] getTopManagement(Document src, String docType) {
        String expression = null;
        if (docType.matches("emp")) {
            expression = "//employee[not(@mgr)]";
        }
        if (docType.matches("emp-hier")){
            expression = "/employee[1]";
        }
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = null;
        try {
            xPathExpression = xpath.compile(expression);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        NodeList reasult = null;
        try {
            reasult = (NodeList)xPathExpression.evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        ArrayList<Element> ar = new ArrayList<>();
        for (int i = 0; i < reasult.getLength(); i++) {
            Node node = reasult.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element elem = (Element) node;
                ar.add(elem);
            }
        }
        Element [] elements = new Element[ar.size()];
        ar.toArray(elements);
        return elements;
    }

    public Element[] getOrdinaryEmployees(Document src, String docType) {
        String expression = "//employee[not(@empno = ../employee/@mgr)]";
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = null;
        try {
            xPathExpression = xpath.compile(expression);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        NodeList reasult = null;
        try {
            reasult = (NodeList)xPathExpression.evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        ArrayList<Element> ar = new ArrayList<>();
        for (int i = 0; i < reasult.getLength(); i++) {
            Node node = reasult.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element elem = (Element) node;
                ar.add(elem);
            }
        }
        Element [] elements = new Element[ar.size()];
        ar.toArray(elements);
        return elements;
    }

    public Element[] getCoworkers(Document src, String empno, String docType) throws TransformerException {
        String expression = null;
        if (docType.matches("emp")) {
            expression = "//employee[@mgr = ../employee[@empno = " + empno + "]/@mgr and @empno != " + empno + "]";
        }
        if (docType.matches("emp-hier")){
            expression = "ancestor::/employee[@empno = " + empno + "]/@mgr]";
        }
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = null;
        try {
            xPathExpression = xpath.compile(expression);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        NodeList reasult = null;
        try {
            reasult = (NodeList)xPathExpression.evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        ArrayList<Element> ar = new ArrayList<>();
        for (int i = 0; i < reasult.getLength(); i++) {
            System.out.println(nodeToString(reasult.item(i)));
            Node node = reasult.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element elem = (Element) node;
                ar.add(elem);
            }
        }
        Element [] elements = new Element[ar.size()];
        ar.toArray(elements);
        return elements;
    }
}
