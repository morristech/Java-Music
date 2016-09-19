package collections;

import org.junit.Test;
import pitches.PitchClass;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Nick on 2016-09-18.
 */
public class DiatonicCollectionsTest {
    @Test
    public void noDiatonicCollectionMembersAreNull() throws Exception {
        for (Map<PitchClass, DiatonicCollection> map : DiatonicCollections.MAP.values()) {
            for (DiatonicCollection dc : map.values()) {
                for (int i = 0; i < dc.getCollectionSize(); i++) {
                    assertNotNull(dc.getScaleDegree(i));
                }
            }
        }
    }
}