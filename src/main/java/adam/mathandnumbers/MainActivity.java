package adam.mathandnumbers;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainFragment.MainCommunicator, SettingsPreferenceFragment.SettingsCommunicator, CustomDialogFragment.CustomDialogListener {

  public static String DEFAULT_PREFERENCES;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DEFAULT_PREFERENCES = getPackageName() + "_preferences";

    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.main_act_fragment_container, new MainFragment());
    ft.commit();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    switch (id) {
      case R.id.action_start:
        showQuestionActivity();
        break;
      case R.id.action_settings:
        showSettingsPreferenceFragment();
        break;
      case R.id.action_exit:
        promptQuit();
        break;
      default:
        //Nothing
    }
    return super.onOptionsItemSelected(item);
  }

  private void showQuestionActivity() {
    SharedPreferences pref = getSharedPreferences(DEFAULT_PREFERENCES, MODE_PRIVATE);
    File f = new File("/data/data/" + getPackageName() + "/shared_prefs/" + MainActivity.DEFAULT_PREFERENCES + ".xml");
    if (f.exists()) {
      for (QuestionBank.QuestionType type : QuestionBank.QUESTION_TYPES) {
        if (pref.getBoolean(type.toString(), false)) {
          Intent i = new Intent(this, QuestionActivity.class);
          startActivity(i);
          return;
        }
      }
      showInvalidSettingsDialog();
      return;
    }
    Intent i = new Intent(this, QuestionActivity.class);
    startActivity(i);
  }

  private void showInvalidSettingsDialog() {
    Map<CustomDialogFragment.DialogKeys, String> mapBundle = new HashMap<>();
    mapBundle.put(CustomDialogFragment.DialogKeys.TITLE, getString(R.string.dialog_invalid_settings_title));
    mapBundle.put(CustomDialogFragment.DialogKeys.MESSAGE, getString(R.string.dialog_invalid_settings_message));
    mapBundle.put(CustomDialogFragment.DialogKeys.NEG_BUTTON, getString(R.string.dialog_button_settings));
    mapBundle.put(CustomDialogFragment.DialogKeys.POS_BUTTON, getString(R.string.dialog_button_cancel));

    CustomDialogFragment dialogFrag = CustomDialogFragment.newInstance(mapBundle);
    dialogFrag.show(getFragmentManager(), "Invalid Settings Dialog");
  }

  private void showSettingsPreferenceFragment() {
    if (!(getFragmentManager().findFragmentById(R.id.main_act_fragment_container) instanceof PreferenceFragment)) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();
      ft.replace(R.id.main_act_fragment_container, new SettingsPreferenceFragment());
      ft.addToBackStack(null);
      ft.commit();
    }
  }

  private void promptQuit() {
    Map<CustomDialogFragment.DialogKeys, String> mapBundle = new HashMap<>();
    mapBundle.put(CustomDialogFragment.DialogKeys.TITLE, getString(R.string.dialog_quit_title));
    mapBundle.put(CustomDialogFragment.DialogKeys.MESSAGE, getString(R.string.dialog_quit_message));
    mapBundle.put(CustomDialogFragment.DialogKeys.NEG_BUTTON, getString(R.string.dialog_button_yes));
    mapBundle.put(CustomDialogFragment.DialogKeys.POS_BUTTON, getString(R.string.dialog_button_cancel));

    CustomDialogFragment dialogFrag = CustomDialogFragment.newInstance(mapBundle);
    dialogFrag.show(getFragmentManager(), "Quit Dialog");
  }

  @Override
  public void doNegClick(String negButton) {
    if (negButton.equals(getString(R.string.dialog_button_yes)))
      finish();
    else if (negButton.equals(getString(R.string.dialog_button_settings)))
      showSettingsPreferenceFragment();
  }

  /**
   * Overrides back button navigation with fragments
   */
  @Override
  public void onBackPressed() {
    if (getFragmentManager().getBackStackEntryCount() > 0) {
      getFragmentManager().popBackStack();
    } else {
      promptQuit();
    }
  }

  @Override
  public void startButtonClicked() {
    showQuestionActivity();
  }

  @Override
  public void settingsButtonClicked() {
    showSettingsPreferenceFragment();
  }

  @Override
  public void exitButtonClick() {
    promptQuit();
  }

  @Override
  public void addSubClicked() {
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.main_act_fragment_container, new AddSubPreferenceFragment());
    ft.addToBackStack(null);
    ft.commit();
  }

  @Override
  public void mulDivClicked() {
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.main_act_fragment_container, new MulDivPreferenceFragment());
    ft.addToBackStack(null);
    ft.commit();
  }
}
