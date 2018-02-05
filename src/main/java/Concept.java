import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by Ксения on 26.04.2017.
 */

    /*<packagedElement xmi:type="uml:Class" xmi:id="_yFyD-yqGEeenXOl9Fz5tlQ" name="Class1">
    <ownedAttribute xmi:type="uml:Property" xmi:id="_yFyD_CqGEeenXOl9Fz5tlQ" name="Attr1" visibility="private"/>
    <ownedAttribute xmi:type="uml:Property" xmi:id="_yFyD_SqGEeenXOl9Fz5tlQ" name="Attr2" visibility="private"/>
    <ownedAttribute xmi:type="uml:Property" xmi:id="_yFyD_iqGEeenXOl9Fz5tlQ" name="class2" visibility="private" type="_yFyEAiqGEeenXOl9Fz5tlQ" association="_yFyECyqGEeenXOl9Fz5tlQ">
    <upperValue xmi:type="uml:LiteralUnlimitedNatural" xmi:id="_yFyD_yqGEeenXOl9Fz5tlQ" value="2"/>
    <lowerValue xmi:type="uml:LiteralInteger" xmi:id="_yFyEACqGEeenXOl9Fz5tlQ" value="2"/>
    </ownedAttribute>
    <ownedOperation xmi:type="uml:Operation" xmi:id="_yFyEASqGEeenXOl9Fz5tlQ" name="Method1"/>
    </packagedElement>*/
public class Concept {
    private String type;
    private String id;
    private String name;
    private ArrayList<Attribute> attr = new ArrayList<Attribute>();
    //private ArrayList<Operation> operations;

    public Concept(Node classNode, String type) {
        this.type = type;
        this.id = classNode.getAttributes().getNamedItem("xmi:id").getNodeValue();
        this.name = classNode.getAttributes().getNamedItem("name").getNodeValue();
        for (int i = 0; i< classNode.getChildNodes().getLength();i++) {
            Node childNode = classNode.getChildNodes().item(i);
            //<generalization xmi:type="uml:Generalization" xmi:id="_yFyECiqGEeenXOl9Fz5tlQ"
            // general="_yFyEAiqGEeenXOl9Fz5tlQ"/>
            //reading of generalization
            if (childNode.getNodeName().equals("generalization")) {
                    AttrGeneralization generalization = new AttrGeneralization(childNode);
                    this.setAttr(generalization);
                    if (type.equals("Class")) Controller.generalizationsClass.addParent(this.getId(),generalization.getGeneral());
                    else if (type.equals("Interface")) Controller.generalizationsInterface.addParent(this.getId(),generalization.getGeneral());
                    //System.out.println(generalization.getGeneral()+" is general to "+this.getId());
            } else
            //reading of association (including aggregation and composition)
            if (childNode.getNodeName().equals("ownedAttribute")) {
                if (childNode.getAttributes()
                        .getNamedItem("association") != null) {
                    AttrAssociation association = new AttrAssociation(childNode, this.id);
                    this.setAttr(association);
                    //System.out.println("Here is association between " + association.memberClass1 + " and " + association.memberClass2 + " and type is " + association.aggregation);
                }
            }

            /*if (childNode.getNodeName().equals("ownedAttribute")) {
                Attribute attr = new Attribute(childNode);
                this.setAttr(attr);
            }
            if (childNode.getNodeName().equals("ownedOperation")) {
                Operation oper = new Operation(childNode);
                this.setOperation(oper);
            }*/
        }

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Attribute> getAttr() {
        return attr;
    }

    public void setAttr(ArrayList<Attribute> attr) {
        this.attr = attr;
    }

    public void setAttr(Attribute attr) {
        this.attr.add(attr);
    }

}
