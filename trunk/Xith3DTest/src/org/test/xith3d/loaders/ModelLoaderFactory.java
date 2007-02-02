package org.test.xith3d.loaders;

import org.test.xith3d.loaders.adapters.DaeLoaderAdapter;
import org.test.xith3d.loaders.adapters.OBJLoaderAdapter;
import org.test.xith3d.loaders.adapters.TDSLoaderAdapter;
import org.test.xith3d.loaders.adapters.NewdawnTDSLoaderAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: Pablo
 * Date: Jan 27, 2007
 * Time: 1:27:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelLoaderFactory {

    public enum ModelLoaderType{
        DAE_LOADER{
            public ModelLoader createLoader() {
                return new DaeLoaderAdapter();
            }},
        OBJ_LOADER{

            public ModelLoader createLoader() {
                return new OBJLoaderAdapter();
            }},
        TDS_LOADER {

            public ModelLoader createLoader() {
                return new TDSLoaderAdapter();
            }},
        NEWDAWN_TDS_LOADER {
            public ModelLoader createLoader() {
                return new NewdawnTDSLoaderAdapter();
            }};

        public abstract ModelLoader createLoader();
    }

    public static ModelLoader createModelLoader(ModelLoaderType loaderType){
        return loaderType.createLoader();
    }

}
