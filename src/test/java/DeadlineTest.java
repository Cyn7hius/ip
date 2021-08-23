import duke.task.Deadline;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void testSampleInput(){
        Deadline test = new Deadline("homework", LocalDate.parse("2021-08-24"), LocalTime.parse("12:31"));
        assertEquals("[D][ ] homework (by: 24 AUGUST 2021 12:31)", test.toString());
    }
    @Test
    public void testDoneInput(){
        Deadline test = new Deadline("homework", LocalDate.parse("2021-08-24"), LocalTime.parse("12:31"), true);
        assertEquals("[D][X] homework (by: 24 AUGUST 2021 12:31)", test.toString());
    }

    Deadline test = new Deadline("homework", LocalDate.parse("2021-08-24"), LocalTime.parse("12:31"));

    @Test
    public void testDescription(){
        assertEquals("homework", test.getDescription());
    }

    @Test
    public void testDate(){
        assertEquals(LocalDate.parse("2021-08-24"), test.getDate());
    }

    @Test
    public void testTime(){
        assertEquals(LocalTime.parse("12:31"), test.getTime());
    }

}