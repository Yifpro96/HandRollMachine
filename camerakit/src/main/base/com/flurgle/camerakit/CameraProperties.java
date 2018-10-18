package com.flurgle.camerakit;

/**
 * Simple pojo containing various camera properties.
 */
public class CameraProperties {
    public final float verticalViewingAngle;
    public final float horizontalViewingAngle;

    private int displayOrientation;

    public CameraProperties(float verticalViewingAngle, float horizontalViewingAngle) {
        this.verticalViewingAngle = verticalViewingAngle;
        this.horizontalViewingAngle = horizontalViewingAngle;
    }

    public void setDisplayOrientation(int displayOrientation) {
        this.displayOrientation = displayOrientation;
    }

    public int getDisplayOrientation() {
        return displayOrientation;
    }
}
