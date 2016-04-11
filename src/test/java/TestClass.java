
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClass {

    @Test
    public void test() {
        Logger logger = LoggerFactory.getLogger(TestClass.class);
        logger.debug("log");
    }

}
