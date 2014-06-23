package app.controllers;

import com.jfinal.ext.test.ControllerTestCase;
import com.jfinal.sog.initalizer.AppConfig;
import org.junit.Test;

/**
 * <p>
 * .
 * </p>
 */
public class IndexControllerTest extends ControllerTestCase<AppConfig> {
    @Test
    public void testIndex() throws Exception {
        String url = "/";
        use(url).invoke();
    }
}
