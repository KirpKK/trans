package Validation; /**
 * Created by Ксения on 07.04.2017.
 */

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
    private static ArrayList<String> queue = new ArrayList<>();
    public static void read(File file) throws IOException {
        try {
            Controller.clear();
            queue.clear();
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse(file);

            // Получаем корневой элемент
            Node root = document.getDocumentElement();

            // Просматриваем все подэлементы корневого
            NodeList umlPackage = root.getChildNodes();
            for (int i = 0; i < umlPackage.getLength(); i++) {
                Node node = umlPackage.item(i);
                if (node.getNodeName().contains("packagedElement")) {
                    //reading of Concepts (Classes and Interfaces)
                    if (node.getAttributes().getNamedItem("xmi:type")
                            .getFirstChild()
                            .getNodeValue().equals("uml:Class")) {
                        Concept con = new Concept(node, "Class");
                        Controller.concepts.add(con);
                    } else if (node.getAttributes().getNamedItem("xmi:type")
                            .getFirstChild()
                            .getNodeValue().equals("uml:AssociationClass")) {
                        Concept con = new Concept(node, "Class");
                        Controller.concepts.add(con);
                        boolean hasMember = false;
                        String memberAss1;
                        String memberAss2;
                        for (int k = 0; k < node.getChildNodes().getLength(); k++) {
                            if (node.getChildNodes() != null) {
                                if (node.getChildNodes().item(k).getNodeName().equals("ownedEnd")) {
                                    if (!hasMember) {
                                        hasMember = true;
                                        memberAss1 = node.getChildNodes().item(k).getAttributes()
                                                .getNamedItem("type").getNodeValue();
                                        queue.add(node.getAttributes().getNamedItem("xmi:id").getNodeValue());
                                        queue.add(memberAss1);
                                    } else if (hasMember) {
                                        memberAss2 = node.getChildNodes().item(k).getAttributes()
                                                .getNamedItem("type").getNodeValue();
                                        queue.add(memberAss2);
                                        hasMember = false;
                                    }
                                }
                            }
                        }
                    } else if (node.getAttributes().getNamedItem("xmi:type")
                            .getFirstChild()
                            .getNodeValue().equals("uml:Interface")) {
                        Concept con = new Concept(node, "Interface");
                        Controller.concepts.add(con);
                    }   //reading of realization
                    else if (node.getAttributes().getNamedItem("xmi:type")
                            .getFirstChild()
                            .getNodeValue().equals("uml:Realization")) {
                        AttrRealization attrRealization = new AttrRealization(node);
                        attrRealization.add();
                    }//reading of dependency
                    else if (node.getAttributes().getNamedItem("xmi:type")
                            .getFirstChild()
                            .getNodeValue().equals("uml:Dependency")) {
                        AttrDependency attrDependency = new AttrDependency(node);
                        attrDependency.add();
                    }   //reading of association
                    else if (node.getAttributes().getNamedItem("xmi:type")
                            .getFirstChild()
                            .getNodeValue().equals("uml:Association")) {
                        String memberAss1 = node.getAttributes()
                                .getNamedItem("memberEnd").getFirstChild()
                                .getNodeValue().split(" ")[0];
                        String memberAss2 = node.getAttributes()
                                .getNamedItem("memberEnd").getFirstChild()
                                .getNodeValue().split(" ")[1];
                        if (Controller.allAssociations.get(memberAss1)
                                .aggregation.equals(AttrAssociation.Aggregation.NONE)) {
                            if (Controller.allAssociations.get(memberAss2)
                                    .aggregation.equals(AttrAssociation.Aggregation.NONE)) {
                                Controller.associations.addParent(Controller
                                                .allAssociations.get(memberAss1).memberClass1,
                                        Controller.allAssociations.get(memberAss1).memberClass2);
                                Controller.associations.addParent(Controller
                                                .allAssociations.get(memberAss2).memberClass1,
                                        Controller.allAssociations.get(memberAss2).memberClass2);
                            } else if (Controller.allAssociations.get(memberAss2)
                                    .aggregation.equals(AttrAssociation.Aggregation.SHARED)) {
                                Controller.aggregations.addParent(Controller
                                                .allAssociations.get(memberAss1).memberClass1,
                                        Controller.allAssociations.get(memberAss1).memberClass2);
                            } else if (Controller.allAssociations.get(memberAss2)
                                    .aggregation.equals(AttrAssociation.Aggregation.COMPOSITE)) {
                                Controller.compositions.addParent(Controller
                                                .allAssociations.get(memberAss1).memberClass1,
                                        Controller.allAssociations.get(memberAss1).memberClass2);
                            }
                        } else if (Controller.allAssociations.get(memberAss1)
                                .aggregation.equals(AttrAssociation.Aggregation.SHARED)) {
                            Controller.aggregations.addParent(Controller
                                            .allAssociations.get(memberAss1).memberClass2,
                                    Controller.allAssociations.get(memberAss1).memberClass1);
                        } else if (Controller.allAssociations.get(memberAss1)
                                .aggregation.equals(AttrAssociation.Aggregation.COMPOSITE)) {
                            Controller.compositions.addParent(Controller
                                            .allAssociations.get(memberAss1).memberClass2,
                                    Controller.allAssociations.get(memberAss1).memberClass1);
                        }
                    }
                }
            }//заполнение ассоциаций из классов-ассоциаций
            for (int s = 0; s < queue.size(); s += 3) {
                Controller.associations.addParent(queue.get(s),queue.get(s+1));
                Controller.associations.addParent(queue.get(s+1),queue.get(s));
                Controller.associations.addParent(queue.get(s),queue.get(s+2));
                Controller.associations.addParent(queue.get(s+2),queue.get(s));
            }
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
