package cc.tommymyers.tmod.util;

import cc.tommymyers.tmod.Tmod;
import cc.tommymyers.tmod.webapi.endpoints.Versions;
import com.google.gson.JsonSyntaxException;

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
            return Tmod.modVersion.compareTo(latestVersion.getId()) < 0;
        } catch (IOException ioException) {
            Tmod.logger.error("I/O Exception when checking for update", ioException);
        } catch (JsonSyntaxException jsonSyntaxException) {
            Tmod.logger.error("Invalid/Unexpected JSON in version server's response", jsonSyntaxException);
        }
        return false;
    }

}
