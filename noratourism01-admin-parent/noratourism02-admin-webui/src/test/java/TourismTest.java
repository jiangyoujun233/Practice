import com.nora.website.entity.Admin;
import com.nora.website.mapper.AdminMapper;
import com.nora.website.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class TourismTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void test(){
        for(int i=1; i<100; i++){
            Admin admin = new Admin(null, "testloginAcct"+i, "123456", "testuserName"+i, "jack@gmall.com", null);
            adminMapper.insert(admin);
        }
    }
    @Test
    public void testTx(){
        Admin admin = new Admin(null, "tom", "123456", "汤姆", "tom@gmall.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testlog(){
        //1.获取Logger对象
        Logger logger = LoggerFactory.getLogger(TourismTest.class);

        //2.打印不同级别的log
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

    @Test
    public void testInsertAdmin(){
        Admin admin = new Admin(null, "jack", "123456", "jiangyoujun", "jack@gmall.com", null);
        adminMapper.insert(admin);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }


}
