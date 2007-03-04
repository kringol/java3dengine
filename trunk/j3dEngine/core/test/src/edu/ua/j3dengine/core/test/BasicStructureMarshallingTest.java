package edu.ua.j3dengine.core.test;

import junit.framework.TestCase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;

import edu.ua.j3dengine.core.*;
import edu.ua.j3dengine.core.state.*;

import java.util.List;
import java.util.ArrayList;



public class BasicStructureMarshallingTest extends TestCase {


    public BasicStructureMarshallingTest() {
        super("BasicStructureMarshallingTest");
    }

    public void testBasicWorldSerialization() throws JAXBException {

        GameObject go1 = new StaticGameObject("sObject1");
        go1.addAttribute(new Attribute<String>("object_Attr1", "object_Attr1_value"));
        StaticObjectState state = new StaticObjectState("sObject1_state1");
        state.addAttribute(new Attribute<String>("sObject1_state1_prop1", "val1"));
        state.addAttribute(new Attribute<Double>("sObject1_state1_prop2", 3.66));
        go1.addState(state);
        state = new StaticObjectState("sObject1_state2");
        state.addAttribute(new Attribute<Integer>("sObject1_state2_prop1", 5));
        go1.addState(state);

        GameObject go2 = new StaticGameObject("sObject2");
        go2.addAttribute(new Attribute<String>("sObject2_attr1", "sObject2_attr1_value"));

        List<GameObject> list = new ArrayList<GameObject>();
        list.add(go1);
        list.add(go2);

        World world = World.create("MyWorld", list);


        JAXBContext ctx = JAXBContext.newInstance("edu.ua.j3dengine.core:edu.ua.j3dengine.core.state:edu.ua.j3dengine.core.geometry");

        Marshaller m =  ctx.createMarshaller();

        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(world, System.out);

    }
}
