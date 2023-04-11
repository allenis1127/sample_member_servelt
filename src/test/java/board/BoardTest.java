package board;

import com.example.sample_member_servelt.dao.BoardDAO;
import com.example.sample_member_servelt.dto.BoardDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

@Log4j2
public class BoardTest {

    @Test
    public void insertBoard() throws SQLException, ClassNotFoundException {
        BoardDAO boardDAO = BoardDAO.getInstance();

        for(int i = 0; i < 100; i++) {
            BoardDTO boardDTO = new BoardDTO();

            boardDTO.setId("test");
            boardDTO.setName("name-" + i);
            boardDTO.setSubject("test" + i);
            boardDTO.setContent("contents" + i);
            java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd(HH:mm:ss)");
            String registDay = formatter.format(new java.util.Date());
            boardDTO.setRegistDay(registDay);
            boardDTO.setHit(0);
            boardDAO.insertPost(boardDTO);
        }

    }

    @Test
    public void selectOneTest() throws SQLException, ClassNotFoundException {
        BoardDAO boardDAO = BoardDAO.getInstance();
        BoardDTO boardDTO = boardDAO.selectOneByNum(100); // 존재하는 번호. 디비에서 확인
        log.info(boardDTO);
    }
}
