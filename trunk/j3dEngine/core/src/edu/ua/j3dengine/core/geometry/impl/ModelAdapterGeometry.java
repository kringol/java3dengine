package edu.ua.j3dengine.core.geometry.impl;

import edu.ua.j3dengine.core.geometry.BaseGeometry;
import edu.ua.j3dengine.core.mgmt.ResourceManager;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.exception.ModelLoadingException;

import javax.vecmath.Tuple3f;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

import org.xith3d.loaders.models.base.Model;
import org.xith3d.scenegraph.SceneGraphObject;
import org.xith3d.scenegraph.Node;
import org.xith3d.scenegraph.Transform3D;
import org.xith3d.scenegraph.TransformGroup;

@XmlRootElement()
public class ModelAdapterGeometry extends BaseGeometry implements XithGeometry{

    private XithGeometry adapteeGeometry;


    private boolean separatedModel = true;

    private Transform3D transform;

    @XmlAttribute
    private boolean precomputedModel = false;

    @XmlElement
    private String modelFilePath;

    @XmlElement
    private String modelObjectName;

    @XmlAttribute
    private boolean loadLater = false;


    public ModelAdapterGeometry() {
    }

    public ModelAdapterGeometry(String modelFilePath, String modelObjectName) {
        this(modelFilePath, modelObjectName, false, false);
    }

    public ModelAdapterGeometry(String modelFilePath, String modelObjectName, boolean isPrecomputedModel, boolean loadLater) {
        if (modelFilePath == null){
            setSeparatedModel(false);
        }else{
            this.modelFilePath = modelFilePath;
        }
        if (modelObjectName != null){
            this.setName(modelObjectName);
        }
        this.modelObjectName = modelObjectName;
        this.precomputedModel = isPrecomputedModel;
        this.loadLater = loadLater;
    }


    public void setPrecomputedModel(boolean precomputedModel) {
        this.precomputedModel = precomputedModel;
    }


    public boolean isPrecomputedModel() {
        return precomputedModel;
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


    public boolean loadLater() {
        return this.loadLater;
    }

    protected void setLoadLater(boolean loadLater) {
        this.loadLater = loadLater;
    }

    public Transform3D getPreTransform() {
        return getTransform();
    }

    public Transform3D getTransform() {
        return transform;
    }

    public void setTransform(Transform3D transform) {
        if (this.transform != null){
            throw new IllegalStateException("Transform can only be set once.");
        }
        this.transform = transform;
    }

    @Override
    public String getName() {
        return modelObjectName;
    }

    public XithGeometry getAdapteeGeometry() {
        assert (modelFilePath != null) || (!isSeparatedModel() && modelObjectName != null);

        if (adapteeGeometry == null){
            Node model = null;
            if (isSeparatedModel()){
                if (isPrecomputedModel()){
                    model = ResourceManager.getInstance().getPrecomputedCal3DModel(modelFilePath);
                }else{
                    model = ResourceManager.getInstance().getModel(modelFilePath);
                }
            }else{
                Node node = ((XithGeometry)GameObjectManager.getInstance().getWorld().getGeometry()).getSceneGraphNode();
                if (node instanceof Model){
                    model = node;
                }else{
                    throw new ModelLoadingException("No object with name '"+modelObjectName+"' could be found in loaded world model.");
                }
            }
            SceneGraphObject object = null;
            if (modelObjectName != null){
                assert model instanceof Model;
                //todo (pablius) assuming its not a precomputed model... (refactor this)
                object = ((Model)model).getNamedObject(modelObjectName);

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
