package adam.mathandnumbers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by adam on 2016-01-08.
 */
public class QuestionActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);

    SharedPreferences pref = getSharedPreferences("test_pref", MODE_PRIVATE);
    String result = "" + pref.getBoolean(getString(R.string.sw_pref_add_key), false);
    result += " " + pref.getBoolean(getString(R.string.sw_pref_sub_key), false);
    result += " " + pref.getBoolean(getString(R.string.sw_pref_mul_key), false);
    result += " " + pref.getBoolean(getString(R.string.sw_pref_div_key), false);
    Log.i("PREF", getString(R.string.sw_pref_add_key) + " " + pref.contains(getString(R.string.sw_pref_add_key)));

    ((TextView) findViewById(R.id.show_pref_text_view)).setText(result);
  }
}
