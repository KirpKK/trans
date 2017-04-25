/**
 * Created by Ксения on 07.04.2017.
 */


import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

import java.io.IOException;

public class XmlReader {
    private static String UmlPath =
            "C:\\Users\\Ксения\\IBM\\rationalsdp\\workspace\\Translator\\Blank Package.emx";
    private static String anotherPath = "new.xml";
    private static String emxPath = "emxPath.emx";
    public static void main(String[] args) {
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse(emxPath);

            // Получаем корневой элемент
            Node root = document.getDocumentElement();

            System.out.println("uml:Package");
            System.out.println();
            // Просматриваем все подэлементы корневого
            NodeList umlPackage = root.getChildNodes();
            System.out.println("I'm here: " + umlPackage.getLength());
            for (int i = 0; i < umlPackage.getLength(); i++) {
                Node node = umlPackage.item(i);

                if (node.getNodeName().contains("uml:Package")) System.out.println(node.getFirstChild().
                        getAttributes().
                        item(2));
                //if (node.getNodeName().contains("packagedElement")) System.out.println(node.getAttributes().item(2));



            }


           /* for (int i = 0; i < books.getLength(); i++) {
                Node book = books.item(i);
                // Если нода не текст, то это книга - заходим внутрь
                if (book.getNodeType() != Node.TEXT_NODE) {
                    NodeList bookProps = book.getChildNodes();
                    for(int j = 0; j < bookProps.getLength(); j++) {
                        Node bookProp = bookProps.item(j);
                        // Если нода не текст, то это один из параметров книги - печатаем
                        if (bookProp.getNodeType() != Node.TEXT_NODE) {
                            System.out.println(bookProp.getNodeName() + ":" + bookProp.getChildNodes().item(0).getTextContent());
                        }
                    }
                    System.out.println("===========>>>>");
                }
            }*/

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
