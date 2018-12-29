package es.jescuderv.unex.facetrackernearbytfg.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import es.jescuderv.unex.facetrackernearbytfg.App;

public class SessionPreferences {

    private final static String PREFERENCES_NAME = "MY_PREFERENCES";

    private final static String SESSION = "SESSION";
    private final static String VISIBILITY = "VISIBILITY";


    private static SharedPreferences getPreferences() {
        return App.getInstance().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    //session: 0 - no session | 1 - advertiser | 2 - discoverer
    public static int getSession() {
        return getPreferences().getInt(SESSION, 0);
    }

    public static void setSession(int session) {
        getPreferences().edit().putInt(SESSION, session).apply();
    }

    // 0 - invisible | 1 - visible
    public static void setVisibility(int visibility) {
        getPreferences().edit().putInt(VISIBILITY, visibility).apply();
    }

    public static int getVisibility() {
        return getPreferences().getInt(VISIBILITY, 0);
    }
}
