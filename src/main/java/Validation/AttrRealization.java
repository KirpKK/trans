package Validation;

import org.w3c.dom.Node;

/**
 * Created by Ксения on 23.05.2017.
 */

//<packagedElement xmi:type="uml:Realization" xmi:id="_uAOGXj_HEeek6c32LEekNQ"
//        supplier="_uAOGXD_HEeek6c32LEekNQ" client="_uAOGWz_HEeek6c32LEekNQ"/>

public class AttrRealization extends Attribute{
    private final String client;
    private  final String supplier;

    public AttrRealization(Node node) {
        super(node.getAttributes().getNamedItem("xmi:id").getNodeValue(),
                "realization");
        client = node.getAttributes().getNamedItem("client").getNodeValue();
        supplier = node.getAttributes().getNamedItem("supplier").getNodeValue();
    }

    public void add(){
        Controller.findConcept(client).setAttr(this);
        Controller.findConcept(supplier).setAttr(this);
        Controller.realizations.addParent(client, supplier);
    }

    public String getClient() {
        return client;
    }

    public String getSupplier() {
        return supplier;
    }
}
