package me.NickP0is0n;

public class AppInfo {

    private final String VERSION;
    private final String BUILD;

    public String getVersion() {
        return VERSION;
    }

    public String getBuild() {
        return BUILD;
    }

    private AppInfo() {
        VERSION = "1.1 Beta 2 (Developer Branch)";
        BUILD = "July 27, 2019";
    }

    public static AppInfo getInstance() {
        AppInfo info = new AppInfo();
        return info;
    }
}
