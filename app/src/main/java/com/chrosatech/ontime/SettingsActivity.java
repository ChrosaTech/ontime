package com.chrosatech.ontime;


import android.annotation.TargetApi;
import android.app.AlertDialog;
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
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    AlertDialog.Builder builder;

    static SettingsActivity th;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setAppTheme();

        super.onCreate(savedInstanceState);

        setupActionBar();

        //Get reference for static members
        th = this;


        /*TextView tv = new TextView(this);
        tv.setText(getVersionNumber());
        setListFooter(tv);*/

    }

    /*private String getVersionNumber() {
        String versionNumber = "Thanks for your support";
        // lots of code that gets the actual version number
        return "Crafted with Love ".concat(versionNumber);
    }*/

    private void setAppTheme(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String themeColor = sharedPref.getString("example_theme", "");

        switch (themeColor){

            case "Bubblegum Pink"  :  setTheme(R.style.PinkTheme);
                break;
            case "Hot Orange" : setTheme(R.style.OrangeTheme);
                break;
            case "Rose Red"    : setTheme(R.style.RedTheme);
                break;
            case "Forest Green"  : setTheme(R.style.GreenTheme);
                break;
            default:
        }

    }

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
               /* || DataSyncPreferenceFragment.class.getName().equals(fragmentName)*/
                || NotificationPreferenceFragment.class.getName().equals(fragmentName)
                || CreditFragment.class.getName().equals(fragmentName);
    }

    @Override
    public void onHeaderClick(Header header, int position) {
        if (position == 3)
        {

            /*Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "chrosatech@gmail.com" });
            i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback");
            i.putExtra(android.content.Intent.EXTRA_TEXT, "Add a feature");
            startActivity(Intent.createChooser(i, "Send email"));*/
            Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "chrosatech@gmail.com", null));
            i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback");
            i.putExtra(android.content.Intent.EXTRA_TEXT, "Add a feature");
            startActivity(Intent.createChooser(i, "Send email"));
        }
        else if (position==2)
        {
            builder=new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
            builder.setTitle("About");
            builder.setMessage("/*Custom_message*/");
            builder.setPositiveButton("Ok", null);
            // builder.setNegativeButton("cancel", null);
            builder.show();
            //builder.setIcon(R.drawable.ic_launcher);
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
            bindPreferenceSummaryToValue(findPreference("example_appearance"));
            bindPreferenceSummaryToValue(findPreference("example_theme"));
            findPreference("example_theme").getSharedPreferences().registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        }



        private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("example_theme")) {

                    //Restart the app on changing theme.
                    th.finish();
                    Intent i = new Intent(th.getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    th.getApplicationContext().startActivity(i);
                }
            }
        };
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
            bindPreferenceSummaryToValue(findPreference("notification_before_time"));
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class CreditFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_credits);
        }

    }


    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    /*@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DataSyncPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_data_sync);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("sync_frequency"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }*/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class AboutFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_about);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            /*bindPreferenceSummaryToValue(findPreference("sync_frequency"));*/
        }

    }
}
