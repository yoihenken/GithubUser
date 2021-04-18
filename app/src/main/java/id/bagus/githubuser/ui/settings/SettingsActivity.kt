package id.bagus.githubuser.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import id.bagus.githubuser.R
import id.bagus.githubuser.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var reminder : Reminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        reminder = Reminder()
        binding.apply {
            appBar.setNavigationOnClickListener { finish() } //When back clicked, finish this Activity
            //Localization
            tvLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            swReminder.apply {
                val isSet = reminder.isAlarmSet(this@SettingsActivity)
                isChecked = isSet

                setOnCheckedChangeListener { _, b ->
                    startNotif(isChecked)
                }
            }
        }
    }

    private fun startNotif(state : Boolean) = when(state){
        true -> reminder.setRepeatingAlarm(this, getString(R.string.reminder_msg))
        else -> reminder.cancelAlarm(this)
    }



}