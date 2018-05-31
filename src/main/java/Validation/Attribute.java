package Validation;

/**
 * Created by Ксения on 26.04.2017.
 */

    /*<packagedElement xmi:type="uml:Class" xmi:id="_yFyD-yqGEeenXOl9Fz5tlQ" name="Class1">
    <ownedAttribute xmi:type="uml:Property" xmi:id="_yFyD_CqGEeenXOl9Fz5tlQ" name="Attr1" visibility="private"/>
    <ownedAttribute xmi:type="uml:Property" xmi:id="_yFyD_SqGEeenXOl9Fz5tlQ" name="Attr2" visibility="private"/>
    <ownedAttribute xmi:type="uml:Property" xmi:id="_yFyD_iqGEeenXOl9Fz5tlQ" name="class2" visibility="private"
            type="_yFyEAiqGEeenXOl9Fz5tlQ" association="_yFyECyqGEeenXOl9Fz5tlQ">
    <upperValue xmi:type="uml:LiteralUnlimitedNatural" xmi:id="_yFyD_yqGEeenXOl9Fz5tlQ" value="2"/>
    <lowerValue xmi:type="uml:LiteralInteger" xmi:id="_yFyEACqGEeenXOl9Fz5tlQ" value="2"/>
    </ownedAttribute>
    <ownedOperation xmi:type="uml:Validation.Operation" xmi:id="_yFyEASqGEeenXOl9Fz5tlQ" name="Method1"/>
    </packagedElement>*/

public abstract class Attribute {

    protected String id;
    protected String type;

    Attribute(String id, String type){
        this.type = type;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
