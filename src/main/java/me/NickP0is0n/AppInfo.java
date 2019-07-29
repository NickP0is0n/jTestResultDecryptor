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
        VERSION = "1.1.0";
        BUILD = "July 29, 2019";
    }

    public static AppInfo getInstance() {
        return new AppInfo();
    }
}
