import org.w3c.dom.Node;

/**
 * Created by Ксения on 18.11.2017.
 */
public class AttrAssociation extends Attribute {
    String memberClass1;
    String memberClass2; //second class in Association "type=..."
    Aggregation aggregation; //type of association
    enum Aggregation {NONE, SHARED, COMPOSITE};
    AttrAssociation(Node node, String parentClassId) {
        super(node.getAttributes().getNamedItem("xmi:id").getNodeValue(),
                "association");
        memberClass1 = parentClassId;
        if (node.getAttributes()
                .getNamedItem("aggregation") == null) {
            aggregation = Aggregation.NONE;
        } else {
            if (node.getAttributes().getNamedItem("aggregation").getNodeValue().equals("shared")) {
                aggregation = Aggregation.SHARED;
            } else aggregation = Aggregation.COMPOSITE;
        }
        memberClass2 = node.getAttributes().getNamedItem("type").getNodeValue();
        Controller.allAssociations.put(id, this);
    }
}
