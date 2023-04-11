package ripple;

import com.example.sample_member_servelt.dao.RippleDAO;
import com.example.sample_member_servelt.dto.RippleDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


@Log4j2
public class RippleTest {
    @Test
    public void insertRippleTest() throws SQLException, ClassNotFoundException {
        RippleDAO rippleDAO = RippleDAO.getInstance();
        RippleDTO rippleDTO = new RippleDTO();

        rippleDTO.setBoardNum(223);  // 디비에 존재하는 번호
        rippleDTO.setName("테스트");
        rippleDTO.setMemberId("test");
        rippleDTO.setContent("ripple test");
        rippleDTO.setIp("1.1.1.1");
        if (rippleDAO.insertRipple(rippleDTO)) {
            log.info("성공");
        }
        else {
            log.info("실패");
        }
        Assertions.assertTrue(rippleDAO.insertRipple(rippleDTO));
    }
}
