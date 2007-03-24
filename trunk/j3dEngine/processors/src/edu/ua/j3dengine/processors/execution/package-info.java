/**
 * Indicates JAXB to use FIELD access (by default) to read/write object attributes.
 * This is neccesary to avoid conflict between property names and getX() methods.
 */
@XmlAccessorType(XmlAccessType.NONE)
package edu.ua.j3dengine.processors.execution;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

