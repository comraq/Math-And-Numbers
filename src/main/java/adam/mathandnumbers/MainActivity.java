package adam.mathandnumbers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements CustomDialogFragment.CustomDialogListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show();
      }
    });

    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.main_act_layout_container, new MainFragment());
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

  public void showSettingsPreferenceFragment() {
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.main_act_layout_container, new SettingsPreferenceFragment());
    ft.addToBackStack(null);
    ft.commit();
  }

  public void promptQuit() {
    CustomDialogFragment dialogFrag = CustomDialogFragment.newInstance(this, R.string.dialog_quit_title, R.string.dialog_quit_message, R.string.dialog_button_yes, R.string.dialog_button_cancel);
    dialogFrag.show(getFragmentManager(), "Quit Dialog");
  }

  @Override
  public void doNegClick() { finish(); }

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
}
