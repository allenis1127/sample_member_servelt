package member;

import com.example.sample_member_servelt.dao.DBConnection;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class DBConnTest {

    @Test
    public void ConnDBTest() {
        DBConnection connection = new DBConnection();
        if (connection != null) {
            log.info(connection);
        }
        else {

            log.info("fail");
        }
    }
}
