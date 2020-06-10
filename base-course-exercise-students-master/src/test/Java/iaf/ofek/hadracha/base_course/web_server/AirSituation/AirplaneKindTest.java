package iaf.ofek.hadracha.base_course.web_server.AirSituation;

import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class AirplaneKindTest {
    @Test
    public void testLeafKinds_allLeavesHaveNoParent(){
        // Arrange

        AirplaneKind[] airplaneKinds = AirplaneKind.values();
        List<AirplaneKind> leaves = new ArrayList<>(AirplaneKind.LeafKinds()); //clone so can be changed
        leaves.sort(Comparator.comparing(Enum::name)); //sort so result will be predictable

        // Act
        assertEquals(new ArrayList<AirplaneKind>(){{
            add(AirplaneKind.F15);
            add(AirplaneKind.F16);
            add(AirplaneKind.Saraf);
            add(AirplaneKind.Shoval);
            add(AirplaneKind.Yanshoof);
            add(AirplaneKind.Zik);
        }}, leaves);
    }

    @Test
    public void isDescendantOf() {
        assertTrue(AirplaneKind.F15.isDescendantOf(AirplaneKind.Krav));
        assertTrue(AirplaneKind.F16.isDescendantOf(AirplaneKind.Krav));
        assertTrue(AirplaneKind.Saraf.isDescendantOf(AirplaneKind.Maskar));
        assertTrue(AirplaneKind.Yanshoof.isDescendantOf(AirplaneKind.Masaar));
        assertTrue(AirplaneKind.Shoval.isDescendantOf(AirplaneKind.Katmam));
        assertTrue(AirplaneKind.Zik.isDescendantOf(AirplaneKind.Zik));
    }
}
