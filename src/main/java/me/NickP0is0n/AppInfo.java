package me.NickP0is0n;

public class AppInfo {

    private final String version;
    private final String build;

    public String getVersion() {
        return version;
    }

    public String getBuild() {
        return build;
    }

    private AppInfo() {
        version = "Version 1.1 Beta 2";
        build = "";
    }

    public static AppInfo getInstance() {
        AppInfo info = new AppInfo();
        return info;
    }
}
