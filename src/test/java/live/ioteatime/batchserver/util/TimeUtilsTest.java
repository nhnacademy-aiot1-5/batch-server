package live.ioteatime.batchserver.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilsTest {

    @Test
    void getDate() {
        LocalDate expected = LocalDate.now().minusDays(1);

        assertEquals(expected, TimeUtils.getDate());
    }

    @Test
    void getDateString() {
        String expected = String.valueOf(LocalDate.now().minusDays(1));

        assertEquals(expected, TimeUtils.getDateString());
    }
}