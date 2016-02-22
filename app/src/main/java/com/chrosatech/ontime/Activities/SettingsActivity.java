package com.chrosatech.ontime.Activities;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.widget.Toast;

import com.chrosatech.ontime.BuildConfig;
import com.chrosatech.ontime.Helper.OpenerAndHelper;
import com.chrosatech.ontime.Helper.Values;
import com.chrosatech.ontime.R;
import com.chrosatech.ontime.Receivers.MyBroadcastReciever;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    private AlertDialog.Builder builder;


   // static SettingsActivity th;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        OpenerAndHelper.setContext(this);

        OpenerAndHelper.setAppTheme();



        super.onCreate(savedInstanceState);

        setupActionBar();

        //Get reference for static members
       // th = this;


        /*TextView tv = new TextView(this);
        tv.setText(getVersionNumber());
        setListFooter(tv);*/

    }

    /*private String getVersionNumber() {
        String versionNumber = "Thanks for your support";
        // lots of code that gets the actual version number
        return "Crafted with Love ".concat(versionNumber);
    }*/

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);

    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || NotificationPreferenceFragment.class.getName().equals(fragmentName);
              //  || CreditFragment.class.getName().equals(fragmentName);
    }

    @Override
    public void onHeaderClick(Header header, int position) {

        String title = String.valueOf(header.title);
        switch (title) {
            case "Feedback":

                /*Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "chrosatech@gmail.com" });
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "Add a feature");
                startActivity(Intent.createChooser(i, "Send email"));*/
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "chrosatech@gmail.com", null));
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                String body = "[ " + Build.MANUFACTURER + ", " + Build.BRAND + ", " + Build.MODEL + ", " + Build.PRODUCT + ", " + Build.DEVICE
                        + ", " + Build.VERSION.SDK_INT + ", " + Build.VERSION.RELEASE + ", " + BuildConfig.VERSION_NAME + "]\n\n";
                i.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(i, "Send email"));
                break;
            case "About":
                builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                builder.setTitle("About");
                builder.setMessage("/*Custom_message*/");
                builder.setPositiveButton("Ok", null);
                // builder.setNegativeButton("cancel", null);
                builder.show();
                //builder.setIcon(R.drawable.ic_launcher);
                break;
            case "Change time table":
                MainActivity.sharedPreferences.edit().putBoolean(Values.keyChangeTimeTable, true).apply();
                OpenerAndHelper.restartApp();
                break;
        }

        super.onHeaderClick(header, position);
    }


    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
           /* bindPreferenceSummaryToValue(findPreference("example_text"));
            bindPreferenceSummaryToValue(findPreference("example_list"));*/
            bindPreferenceSummaryToValue(findPreference(Values.keyAppearance));
            bindPreferenceSummaryToValue(findPreference(Values.keyTheme));
            findPreference(Values.keyAppearance).getSharedPreferences().registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
            findPreference(Values.keyTheme).getSharedPreferences().registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            /*bindPreferenceSummaryToValue(findPreference("sync_frequency"));*/
        }
    }

    private static SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener()
    {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                case Values.keyTheme:

                    MainActivity.isThemeChanged = true;
                    //Restart the app on changing theme.
                    //getActivity().finish();
                    OpenerAndHelper.restartApp();

                    break;
                case "notification_before_time":

                    OpenerAndHelper.disableBootReceiver();
                    OpenerAndHelper.enableBootReceiver();

                    break;
                case Values.keyAppearance:
                    for (int i = 0; i < Values.fragNumber; i++) {
                        Values.refreshData[i] = true;
                    }
                    break;
            }
        }
    };

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference(Values.keyNotificationRingtone));
            bindPreferenceSummaryToValue(findPreference(Values.keyNotificationBeforeTime));
            findPreference(Values.keyNotificationBeforeTime).getSharedPreferences().registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);


            //**prefrence for before time**//


            //***switch Prefrence for vibration***
            SwitchPreference alertOption=(SwitchPreference) findPreference(Values.keyNotificationAlert);
            if (alertOption!=null)
            {
                alertOption.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object isAlertEnabled) {
                       boolean isAlertOn= (Boolean) isAlertEnabled;

                        if (isAlertOn)
                        {
                            MyBroadcastReciever.setNextAlarm(OpenerAndHelper.getContext());
                            OpenerAndHelper.enableBootReceiver();
                        }
                        else
                        {
                            Context context = OpenerAndHelper.getContext();
                            Intent intent = new Intent(context, MyBroadcastReciever.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                    context, 234324243, intent, 0);
                            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            if (alarmManager!= null) {
                                alarmManager.cancel(pendingIntent);
                            }
                            OpenerAndHelper.disableBootReceiver();
                        }
                        return true;
                    }
                });
            }

        }

    }

}
