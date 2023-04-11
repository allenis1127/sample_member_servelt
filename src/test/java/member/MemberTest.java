package member;

import com.example.sample_member_servelt.dto.MemberDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class MemberTest {
    @Test
    public void updateMember() {

        String id = "test"; // 디비에 존재하는 아이디
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
    }
}
