package org.test.xith3d.loaders.adapters;

/**
 * Created by IntelliJ IDEA.
 * User: Pablo
 * Date: Jan 27, 2007
 * Time: 1:35:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelLoadingException extends RuntimeException {

    public ModelLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelLoadingException(Throwable cause) {
        super(cause);
    }
}
