package edu.ua.j3dengine.core;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Created by IntelliJ IDEA.
 * User: Pablo
 * Date: Mar 4, 2007
 * Time: 2:42:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
        try {
            JAXBContext ctx = JAXBContext.newInstance("edu.ua.j3dengine.core:edu.ua.j3dengine.core.state");
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
