import com.h3dg3wytch.CustomerList;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;

public class PropertiesLoad {

    @Test
    public void TestThatPropertiesLoad(){

        CustomerList list = new CustomerList();

        list.setUp();

        Properties properties = CustomerList.getProperties();

        assert properties.getProperty("secret_key").equals("a1913003d89647008fd742d232e9fcd1");
        assert properties.getProperty("tenant_id").equals("23839");
        assert properties.getProperty("site_id").equals("35388");
    }

}
