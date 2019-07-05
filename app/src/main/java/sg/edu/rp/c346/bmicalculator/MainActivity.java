package sg.edu.rp.c346.bmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
  EditText etWeight, etHeight;
  TextView tvDate, tvBMI, tvResult;
  Button btnCalc, btnReset;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    etWeight = findViewById(R.id.editTextWeight);
    etHeight = findViewById(R.id.editTextHeight);
    btnCalc  = findViewById(R.id.buttonCalculate);
    btnReset = findViewById(R.id.buttonReset);
    tvBMI    = findViewById(R.id.tvLastBMI);
    tvDate   = findViewById(R.id.tvLastDate);
    tvResult = findViewById(R.id.tvOutcome);

    btnCalc.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        //Step 1a: Get the user input from the EditTet and store it in a variable
        float height = Float.parseFloat(etHeight.getText().toString());
        float weight= Float.parseFloat(etWeight.getText().toString());

        //Step 1b: Obtain an instance of the SharedPreference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        //Step 1c: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor preEdit = prefs.edit();

        //Step 1d: Add the key-value pair
        preEdit.putFloat("Height", height);
        preEdit.putFloat("Weight", weight);

        //Step 1e: Call commit() to save the changes into SharedPreference
        preEdit.commit();

        float bmi = weight/(height*height);

        //Step 2c: Update the UI element witht the value
        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        String stringBMI = String.format("%.3f",bmi);
        tvDate.setText("Last Calculated Date: "+ datetime);
        tvBMI.setText("Last Calculated BMI: "+ stringBMI);


        if (bmi<18.5){
          tvResult.setText("You are underweight");

        }else if(bmi<24.9){
          tvResult.setText("Your BMI is normal");

        }else if(bmi<29.9){
          tvResult.setText("You are overweight");
        }else{
          tvResult.setText("You are obese");
        }
      }
    });

    btnReset.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        tvResult.setText("");
        etHeight.setText("");
        etWeight.setText("");
        tvDate.setText("Last Calculated Date: ");
        tvBMI.setText("Last Calculated BMI: ");
      }
    });
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    //Step 1a: Get the user input from the EditTet and store it in a variable
    float height = Float.parseFloat(etHeight.getText().toString());
    float weight = Float.parseFloat(etWeight.getText().toString());

    //Step 1b: Obtain an instance of the SharedPreference
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

    //Step 1c: Obtain an instance of the SharedPreference Editor for update later
    SharedPreferences.Editor preEdit = prefs.edit();

    //Step 1d: Add the key-value pair
    preEdit.putFloat("Height", height);
    preEdit.putFloat("Weight", weight);

    //Step 1e: Call commit() to save the changes into SharedPreference
    preEdit.commit();
  }

  @Override
  protected void onResume()
  {
    super.onResume();

    //Step 2a: Obtain an instance of the SHaredPreference
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

    //Step 2b: Retrieve the saved data with the key "greeting" from the SharedPreference object
    float height = prefs.getFloat("Height",0);
    float weight = prefs.getFloat("Weight",0);

    float bmi = weight/(height*height);

    //Step 2c: Update the UI element with the value
    Calendar currentDT = Calendar.getInstance();  //Create a Calendar object with current date and time
    String datetime = currentDT.get(Calendar.DAY_OF_MONTH) + "/" +
            (currentDT.get(Calendar.MONTH)+1) + "/" +
            currentDT.get(Calendar.YEAR) + " " +
            currentDT.get(Calendar.HOUR_OF_DAY) + ":" +
            currentDT.get(Calendar.MINUTE);

    String stringBMI = String.format("%.3f",bmi);
    tvDate.setText("Last Calculated Date: "+ datetime);
    tvBMI.setText("Last Calculated BMI: "+stringBMI);
  }
}
