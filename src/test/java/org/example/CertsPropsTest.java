package org.example;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CertsPropsTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        final String fingerprint = "Letv/letv_gae_cvt/letv_gae_cvt:9/V3603RCN02C000423D10281D/20230221:userdebug/test-keys";
        CertsProps res = CertsProps.parseFingerprint(fingerprint);
        System.out.println("parsed CertsProps: " + res);

        // These properties should be get from ro.build.type and ro.build.tags in real devices.
        Map<CertsProps, Boolean> propsMap = new HashMap<>();
        // Development userdebug build
        propsMap.put(new CertsProps("userdebug", "test-keys"), true);
        // Development user build
        propsMap.put(new CertsProps("user", "test-keys"), false);
        // Release user build
        propsMap.put(new CertsProps("user", "release-keys"), false);
        for (CertsProps props : propsMap.keySet()) {
            System.out.println("" + props);
            assertEquals(propsMap.get(props), props.equals(res));
        }
    }
}