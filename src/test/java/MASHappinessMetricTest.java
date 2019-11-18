import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MASHappinessMetricTest {

    @Test
    public void testLectureExamples() {
        assertEquals(0.5d, MASHappinessMetric.calculateHappiness(new int[]{0, 1, 2, 3}, new int[]{1, 0, 2, 3}));

        assertEquals(0.25d, MASHappinessMetric.calculateHappiness(new int[]{0, 1, 2, 3}, new int[]{2, 0, 1, 3}));
    }

}
