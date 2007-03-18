package edu.ua.j3dengine.core.geometry.impl;

import edu.ua.j3dengine.core.geometry.BaseGeometry;
import edu.ua.j3dengine.core.mgmt.ResourceManager;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.exception.ModelLoadingException;

import javax.vecmath.Tuple3f;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.xith3d.loaders.models.base.Model;
import org.xith3d.scenegraph.SceneGraphObject;
import org.xith3d.scenegraph.Node;

@XmlRootElement
public class ModelAdapterGeometry extends BaseGeometry implements XithGeometry{

    private XithGeometry adapteeGeometry;


    private boolean separatedModel = true;

    @XmlElement
    private String modelFilePath;

    @XmlElement
    private String modelObjectName;


    public ModelAdapterGeometry() {
    }

    public ModelAdapterGeometry(String modelFilePath, String modelObjectName) {
        if (modelFilePath == null){
            setSeparatedModel(false);
        }else{
            this.modelFilePath = modelFilePath;
        }
        this.modelObjectName = modelObjectName;
    }


    public Node getSceneGraphNode() {
        return getAdapteeGeometry().getSceneGraphNode();
    }


    public boolean isSeparatedModel() {
        return separatedModel;
    }

    public void setSeparatedModel(boolean separatedModel) {
        this.separatedModel = separatedModel;
    }

    @Override
    public String getName() {
        return modelObjectName;
    }

    public XithGeometry getAdapteeGeometry() {
        assert (modelFilePath != null) || (!isSeparatedModel() && modelObjectName != null);

        if (adapteeGeometry == null){
            Model model = null;
            if (isSeparatedModel()){
                model = ResourceManager.getInstance().getModel(modelFilePath);
            }else{
                Node node = ((XithGeometry)GameObjectManager.getInstance().getWorld().getGeometry()).getSceneGraphNode();
                if (node instanceof Model){
                    model = (Model)node;
                }else{
                    throw new ModelLoadingException("No object with name '"+modelObjectName+"' could be found in loaded world model.");
                }
            }
            SceneGraphObject object = null;
            if (modelObjectName != null){
                object = model.getNamedObject(modelObjectName);

                if (object == null){
                    throw new ModelLoadingException("No object with name '"+modelObjectName+"' could be found in model '"+modelFilePath+"'.");
                }
            }else{
                object = model;
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
