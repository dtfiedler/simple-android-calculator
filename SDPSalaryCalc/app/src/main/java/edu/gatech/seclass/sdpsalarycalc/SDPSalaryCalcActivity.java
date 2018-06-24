package edu.gatech.seclass.sdpsalarycalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeMap;

public class SDPSalaryCalcActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //map of cities and their relative index
    Map<String, Integer> cityMap = new TreeMap<String, Integer>();

    private Spinner spinner1, spinner2;
    private String initialCity;
    private String destinationCity;
    private int salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdpsalary_calc);
        this.generateCityList();
        this.setUpButtonClick();
    }

    //for when run is executed
    private void setUpButtonClick(){

        final Button button = findViewById(R.id.runButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                updateResultSalary();
            }
        });
    }

    private void generateCityList() {
        cityMap.put(getString(R.string.city1), 158);
        cityMap.put(getString(R.string.city2), 140);
        cityMap.put(getString(R.string.city3), 227);
        cityMap.put(getString(R.string.city4), 151);
        cityMap.put(getString(R.string.city5), 197);
        cityMap.put(getString(R.string.city6), 243);
        cityMap.put(getString(R.string.city7), 220);
        cityMap.put(getString(R.string.city8), 201);
        cityMap.put(getString(R.string.city9), 143);
        cityMap.put(getString(R.string.city10), 148);

        //same data adapter for both
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, cityMap.keySet().toArray(new String[0]));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set values for intial city
        spinner1=(Spinner)findViewById(R.id.initialCity);
        spinner1.setAdapter(dataAdapter);
        spinner1.setOnItemSelectedListener(this);

        //set values for destination city
        spinner2=(Spinner)findViewById(R.id.destinationCity);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener(this);
    }

    private void updateResultSalary(){
        EditText salaryTextView = (EditText)findViewById(R.id.salary);
        String salaryTemp = salaryTextView.getText().toString().trim();

        //show error message if no salary provided
        if (salaryTemp.equalsIgnoreCase("")){
            salaryTextView.setError(getString(R.string.error_salary));
            return;
        } else {
            salaryTextView.setError(null);
        }

        //set salary to int and clear error
        salary = Integer.parseInt(salaryTemp);
        //if same cities selected
        EditText destinationLabel = (EditText)findViewById(R.id.labelDestinationCity);
        if (initialCity.equalsIgnoreCase(destinationCity)){
            destinationLabel.requestFocus();
            destinationLabel.setError(getString(R.string.error_city_equal));
            return;
        } else {
            destinationLabel.setError(null);
        }

        //otherwise allow computation of value and update result salary label
        TextView resultSalary = (TextView)findViewById(R.id.resultSalary);
        int computedSalary = getRelativeSalary();
        resultSalary.setText("$" + computedSalary + "");
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        //handle parent id and set proper string
        if(parent.getId() == spinner1.getId()){
            initialCity = parent.getItemAtPosition(pos).toString();
        } else {
           destinationCity = parent.getItemAtPosition(pos).toString();
        }
    }

    public int getRelativeSalary(){
        System.out.println(destinationCity + initialCity);
        if(cityMap.get(destinationCity) != null && cityMap.get(initialCity) != null){
            int intialValue = cityMap.get(initialCity);
            int relativeIndex = cityMap.get(destinationCity);
            int computedValue = (salary / intialValue) * relativeIndex;
            return computedValue;
        }
        return -1;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
