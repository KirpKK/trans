import org.w3c.dom.Node;

/**
 * Created by Ксения on 20.12.2017.
 */

public class AttrDependency extends Attribute{
    private final String client;
    private  final String supplier;

    public AttrDependency(Node node) {
        super(node.getAttributes().getNamedItem("xmi:id").getNodeValue(),
                "dependency");
        client = node.getAttributes().getNamedItem("client").getNodeValue();
        supplier = node.getAttributes().getNamedItem("supplier").getNodeValue();
    }

    public void add(){
        Controller.findConcept(client).setAttr(this);
        Controller.findConcept(supplier).setAttr(this);
        Controller.dependencies.addParent(client, supplier);
    }

    public String getClient() {
        return client;
    }

    public String getSupplier() {
        return supplier;
    }
}

