package org.example;

class CertsProps {
    String buildType = "";
    String buildTags = "";

    @Override
    public String toString() {
        return super.toString() + "{"
                + "ro.build.type: '" + buildType + "'"
                + ", ro.build.tags: '" + buildTags + "'"
                + "}";
    }

    protected CertsProps(String variant, String tags) {
        buildType = variant;
        buildTags = tags;
    }

    /**
     * @param fingerprint To track and report issues tied to a particular Android build,
     *                    it is important to understand the build fingerprint.
     *                    The build fingerprint is a unique,
     *                    human-readable string containing manufacturer information issued to each build.
     *                    Compile-Time:
     *                       file: $ROOT/build/make/core/Makefile
     *                       BUILD_FINGERPRINT := \
     *                       1: $(PRODUCT_BRAND)\
     *                       2: /$(TARGET_PRODUCT)\
     *                       3: /$(TARGET_DEVICE):$(PLATFORM_VERSION)\
     *                       4: /$(BUILD_ID)\
     *                       5: /$(BF_BUILD_NUMBER):$(TARGET_BUILD_VARIANT)\
     *                       6: /$(BUILD_VERSION_TAGS)
     *                    Runtime-Time:
     *                      System property: [ro.build.fingerprint]
     *                    https://source.android.google.cn/docs/setup/build/building#understanding-build-fingerprints
     * @return CertsProps object when parsing is successful. null when parsing is failed.
     */
    static CertsProps parseFingerprint(String fingerprint) {
        String[] buildVars = fingerprint.split("/");
        if (buildVars.length == 6) {
            String[] vars = buildVars[4].split(":");
            final String buildType = vars[1];
            final String buildTags = buildVars[5];
            return new CertsProps(buildType, buildTags);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CertsProps) {
            CertsProps meta = (CertsProps)o;
            if (buildType.equals(meta.buildType) && buildTags.equals(meta.buildTags))
                return true;
        }
        return super.equals(o);
    }
}
