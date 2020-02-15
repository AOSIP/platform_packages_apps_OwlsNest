/*
 *  Copyright (C) 2015-2020 Android Open Source Illusion Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aosip.owlsnest.notification;

import android.content.Context;
import android.os.Bundle;
import android.os.ServiceManager;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.search.SearchIndexable;

import com.aosip.support.preference.SystemSettingSwitchPreference;

import java.util.ArrayList;
import java.util.List;

@SearchIndexable
public class NotificationHolder extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener, Indexable {

    private static final String PULSE_AMBIANT_LIGHT_PREF = "pulse_ambient_light";

    private SystemSettingSwitchPreference mPulseEdgeLights;

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.OWLSNEST;
    }

    private Preference mBatteryLightPref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.notification);

        mPulseEdgeLights = (SystemSettingSwitchPreference) findPreference(PULSE_AMBIANT_LIGHT_PREF);
        boolean mPulseNotificationEnabled = Settings.Secure.getInt(getContentResolver(),
                Settings.Secure.DOZE_ENABLED, 0) != 0;
        mPulseEdgeLights.setEnabled(mPulseNotificationEnabled);

        mBatteryLightPref = (Preference) findPreference("charging_light");
        PreferenceScreen prefSet = getPreferenceScreen();
        if (!getResources().getBoolean(
                com.android.internal.R.bool.config_deviceHasLED)) {
            prefSet.removePreference(mBatteryLightPref);
        }
      }

    @Override
    public void onResume() {
        super.onResume();
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
       return false;
    }

    /**
     * For Search.
     */
    public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider() {
            @Override
            public List<SearchIndexableResource> getXmlResourcesToIndex(Context context, boolean enabled) {
                ArrayList<SearchIndexableResource> result =
                    new ArrayList<SearchIndexableResource>();
                    SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.notification;
                    result.add(sir);
                    return result;
            }

            @Override
            public List<String> getNonIndexableKeys(Context context) {
                List<String> keys = super.getNonIndexableKeys(context);
                return keys;
            }
        };
}

