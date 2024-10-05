package mz.co.macave.mundodoscarros.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import mz.co.macave.mundodoscarros.R

class SharedPrefsManager(context: Context, prefs: SharedPreferences) {

    val prefs = prefs
    val context = context

    fun saveKey(key: String) {
        prefs.edit {
            putString(key, context.getString(R.string.prefs_key_currency))
        }
    }

    fun getCurrencyKey() : String? {
        return prefs.getString(context.getString(R.string.prefs_key_currency), null)
    }
}