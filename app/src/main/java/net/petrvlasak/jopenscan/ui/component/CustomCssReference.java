package net.petrvlasak.jopenscan.ui.component;

import org.apache.wicket.request.resource.CssResourceReference;

import java.io.Serial;

public class CustomCssReference extends CssResourceReference {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final CustomCssReference INSTANCE = new CustomCssReference();

    /**
     * Instantiates a new custom css resource reference.
     */
    private CustomCssReference() {
        super(CustomCssReference.class, "css/custom.css");
    }

    /**
     * Gets the single instance of CustomCssReference.
     *
     * @return single instance of CustomCssReference
     */
    public static CustomCssReference instance() {
        return INSTANCE;
    }

}
