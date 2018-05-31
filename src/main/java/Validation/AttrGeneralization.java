package Validation;

import org.w3c.dom.Node;

/**
 * Created by Ксения on 02.05.2017.
 */


//<generalization xmi:type="uml:Generalization" xmi:id="_yFyECiqGEeenXOl9Fz5tlQ"
// general="_yFyEAiqGEeenXOl9Fz5tlQ"/>

public class AttrGeneralization extends Attribute{
    String general;
    public AttrGeneralization(Node node){
        super(node.getAttributes().getNamedItem("xmi:id").getNodeValue(),
                "generalization");
        general = node.getAttributes().getNamedItem("general").getNodeValue();
    }

    public String getGeneral() {
        return general;
    }
}
