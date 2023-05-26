package com.cbnu_voice.cbnu_imy

import android.content.Context
import android.widget.EditText
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreModule(private val context : Context){
    private val Context.datastore by preferencesDataStore(name = "datastore")

    private val KEY_SPEAKER = stringPreferencesKey("key_speaker")
    private val KEY_SELECT_NUM = intPreferencesKey("key_select_num")

    val text : Flow<String> = context.datastore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        } .map { preferances->
            preferances[KEY_SPEAKER] ?: "vyuna"
        }

    val Num : Flow<Int> = context.datastore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        } .map { preferances->
            preferances[KEY_SELECT_NUM] ?: 2
        }

    suspend fun setText(text: String, Num: Int){
        context.datastore.edit { preferences ->
            preferences[KEY_SPEAKER] = text
            preferences[KEY_SELECT_NUM] = Num
        }
    }
}