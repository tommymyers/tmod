package cc.tommymyers.tmod.util;

import cc.tommymyers.tmod.Tmod;
import cc.tommymyers.tmod.webapi.endpoints.Versions;
import org.apache.maven.artifact.versioning.ComparableVersion;

import java.io.IOException;

public class Updater {

    public static boolean hasCheckedForUpdate = false;

    private static Versions.Response.Version latestVersion;

    public static Versions.Response.Version getLatestVersion() {
        return latestVersion;
    }

    public static boolean checkForUpdates() {
        try {
            if (latestVersion == null) {
                latestVersion = Versions.getLatest();
            }
            hasCheckedForUpdate = true;
            ComparableVersion currentVersion = new ComparableVersion(Tmod.modVersion);
            ComparableVersion latestVersion = new ComparableVersion(getLatestVersion().getId());
            return currentVersion.compareTo(latestVersion) < 0;
        } catch (Exception exception) {
            Tmod.logger.error("Error occurred when checking for update", exception);
        }
        return false;
    }

}
