package edu.ua.j3dengine.core.geometry;

import edu.ua.j3dengine.core.geometry.impl.ModelAdapterGeometry;

import javax.vecmath.Tuple3f;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlAdapter;


@XmlJavaTypeAdapter(Geometry.GeometryAdapter.class)
public interface Geometry {

    Tuple3f getLocation();




    
    public static class GeometryAdapter extends XmlAdapter<BaseGeometry, Geometry>{

        public Geometry unmarshal(BaseGeometry concreteGeometry) throws Exception {
            return concreteGeometry;
        }

        public BaseGeometry marshal(Geometry geometry) throws Exception {
            assert geometry == null || geometry instanceof BaseGeometry : "Geometry should be a subclass of BaseGeometry. We made a bad assumption somewhere!";
            return (BaseGeometry)geometry;
        }
    }

}
