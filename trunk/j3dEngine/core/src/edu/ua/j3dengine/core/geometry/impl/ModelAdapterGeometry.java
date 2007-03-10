package edu.ua.j3dengine.core.geometry.impl;

import edu.ua.j3dengine.core.geometry.Geometry;
import edu.ua.j3dengine.core.mgmt.ResourceManager;
import edu.ua.j3dengine.core.exception.ModelLoadingException;

import javax.vecmath.Tuple3f;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.xith3d.loaders.models.base.Model;
import org.xith3d.scenegraph.SceneGraphObject;
import org.xith3d.scenegraph.Node;

@XmlRootElement
public class ModelAdapterGeometry extends Geometry implements XithGeometry{

    private GeometryXithImpl adapteeGeometry;

    @XmlElement
    private String modelFilePath;

    @XmlElement
    private String modelObjectName;


    public ModelAdapterGeometry() {
    }

    public ModelAdapterGeometry(String modelFilePath, String modelObjectName) {
        this.modelFilePath = modelFilePath;
        this.modelObjectName = modelObjectName;
    }


    public Node getSceneGraphNode() {
        return getAdapteeGeometry().getSceneGraphNode();
    }

    @Override
    public String getName() {
        return modelObjectName;
    }

    public GeometryXithImpl getAdapteeGeometry() {
        assert modelFilePath != null && modelObjectName != null;

        if (adapteeGeometry == null){
            Model model = ResourceManager.getInstance().getModel(modelFilePath);
            SceneGraphObject object = model.getNamedObject(modelObjectName);
            if (object == null){
                throw new ModelLoadingException("No object with name '"+modelObjectName+"' could be found in model '"+modelFilePath+"'.");
            }

            assert object instanceof Node;

            adapteeGeometry = new GeometryXithImpl(modelObjectName, (Node)object);
        }
        return adapteeGeometry;
    }

    public Tuple3f getLocation() {
        return getAdapteeGeometry().getLocation();
    }
}
