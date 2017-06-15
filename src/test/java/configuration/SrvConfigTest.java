package configuration;

import configuration.SrvConfig;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by sbt-gimaletdinov1-rr on 14.06.2017.
 */
public class SrvConfigTest {

    @Test
    public void testGetInstance() throws IOException{
        Object srvConfigInstance = SrvConfig.getInstance();
        assertEquals(srvConfigInstance.getClass(), SrvConfig.class );
        assertEquals(srvConfigInstance, SrvConfig.getInstance());
    }

    @Test
    public void testGetProperty() throws IOException{
        String value = SrvConfig.getInstance().getProperty("port");
        assertEquals("8080", value);

    }

    @Test (expected = RuntimeException.class)
    public void testGetPropertyUncheckedException() throws Exception {
        String value = SrvConfig.getInstance().getProperty("Some_sring");

    }

    @Test
    public void testGetHostPort(){
        String value = SrvConfig.getInstance().getHostPort();
        assertEquals("localhost:8080", value);

    }

    @Test
    public void testGetHttpHostPort(){
        String value = SrvConfig.getInstance().getHttpHostPort();
        assertEquals("http://localhost:8080",value);
    }
}
