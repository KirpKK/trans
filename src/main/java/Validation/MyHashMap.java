package Validation;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Home on 08.05.2017.
 */
public class MyHashMap{
    private  HashMap<String,ArrayList<String>> storage;

/*    public HashMap<String, ArrayList<String>> getStorage() {
        return storage;
    }*/

    public MyHashMap() {
        storage = new HashMap<String, ArrayList<String>>();
    }

    public MyHashMap(HashMap<String,ArrayList<String>> m) {
        storage = new HashMap<String, ArrayList<String>>(m);
    }

    // child - concept id, parent - general concept id
    //returns true, when new parent is added
    //returns false, when the parent already exists in list
    public boolean addParent(String child, String parent){
        if (storage.get(child) == null) {
            storage.put(child,new ArrayList<String>());
            storage.get(child).add(parent);
            return true;
        } else {
            if (storage.get(child).contains(parent)) return false;
            else {
                storage.get(child).add(parent);
                return true;
            }
        }
    }

    // child - concept id, parent - general concept id
    //return true, when new parent is added
    //return false, when the parent already exists in list
    public void addParentsList(String child, ArrayList<String> m){
        storage.put(child, m);
    }

    public ArrayList<String> get(String key) {
        return storage.get(key);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for (String  key:storage.keySet()) {
            sb.append("\n" + Controller
                    .findConcept(key)
                    .getName() + " : [");
            for (String id:storage.get(key)){
                sb.append(Controller.findConcept(id).getName() + ", ");
            }
            sb.delete(sb.length()-2, sb.length());
            sb.append("]");
        }

/*        for (String  key:storage.keySet()) {
            System.out.println(key);
            System.out.println(Controller
                    .findConcept(key)
                    .getName());

        }*/

        return sb.toString();
    }

    public void clear() {
        storage.clear();
    }
    public int size(){
        return storage.size();
    }

}
