package edu.ua.j3dengine.core.test;

import junit.framework.TestCase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import edu.ua.j3dengine.core.*;
import edu.ua.j3dengine.core.behavior.InertBehavior;
import edu.ua.j3dengine.core.state.*;

import java.util.List;
import java.util.ArrayList;
import java.io.StringWriter;
import java.io.StringReader;


public class BasicStructureMarshallingTest extends TestCase {


    public BasicStructureMarshallingTest() {
        super("BasicStructureMarshallingTest");
    }



    public void testBasicWorldSerialization() throws JAXBException {

        World world = createStructure();


        JAXBContext ctx = JAXBContext.newInstance("edu.ua.j3dengine.core:" +
                "edu.ua.j3dengine.core.state:" +
                "edu.ua.j3dengine.core.geometry:" +
                "edu.ua.j3dengine.core.behavior");

        Marshaller m =  ctx.createMarshaller();

        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();

        m.marshal(world, writer);

        System.out.println("Result:\n" + writer.toString());

        StringReader reader = new StringReader(writer.toString());

        Unmarshaller um = ctx.createUnmarshaller();
        Object o = um.unmarshal(reader);

        assertEquals(o, world);

        String name = "dObject1";
        DynamicGameObject dgo = (DynamicGameObject)world.getGameObject(name);
        assertNotNull("There should be a DynamicObject named after '"+name+"'", dgo);

        String behaviorName = "dObject1_behavior1";
        assertEquals("DynamicObject should have a behavior named '"+ behaviorName +"'",dgo.getBehavior().getName(), behaviorName);

        assertTrue("Behavior should be of type '"+InertBehavior.class+"'",dgo.getBehavior().getClass() == InertBehavior.class);



    }

    private World createStructure() {
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

        DynamicGameObject do1 = new DynamicGameObject("dObject1");
        do1.addAttribute(new Attribute<Long>("dObject1_attr1", 2000000L));
        DynamicObjectState state2 = new DynamicObjectState("dObject1_state1", null, null, new InertBehavior("dObject1_behavior1"));
        do1.addState(state2);
        do1.setInitialState(state2.getName());


        World world = World.create("MyWorld", go1, go2, do1);
        return world;
    }
}
