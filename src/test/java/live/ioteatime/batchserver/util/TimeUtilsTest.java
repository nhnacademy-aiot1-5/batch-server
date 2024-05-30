package live.ioteatime.batchserver.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilsTest {

    @Test
    void constructorTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<TimeUtils> constructor = TimeUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        TimeUtils timeUtils = constructor.newInstance();

        assertNotNull(timeUtils);
    }

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
