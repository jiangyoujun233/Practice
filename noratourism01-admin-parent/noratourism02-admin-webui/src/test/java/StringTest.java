import com.nora.website.util.tourismUtil;
import org.junit.Test;

public class StringTest {

    @Test
    public void testMd5(){
        String source ="123456";
        String encoded = tourismUtil.md5(source);
        System.out.println(encoded);
    }
}
