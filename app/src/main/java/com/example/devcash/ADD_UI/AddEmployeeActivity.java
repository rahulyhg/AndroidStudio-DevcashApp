package com.example.devcash.ADD_UI;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devcash.Database.DatabaseHelper;
import com.example.devcash.Object.Employee;
import com.example.devcash.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddEmployeeActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference dbreference;
    private FirebaseDatabase firebasedb;
    private String EmployeeId;
    TextInputEditText empLname, empFname, empEmail, empPhone, empDOB;
    Uri empimageUri;
    ImageView empphoto;
    TextView takephoto, choosephoto;
    RadioGroup grpTask;
    RadioButton selectedTask;
    DatePickerDialog bdatePickerDia;

    private static final int PICK_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //
        empphoto = (ImageView) findViewById(R.id.emp_photo) ;
        takephoto = (TextView) findViewById(R.id.txt_emptakephoto);
        choosephoto = (TextView) findViewById(R.id.txt_empchoosephoto);
        empLname = (TextInputEditText) findViewById(R.id.text_input_emp_lname);
        empFname = (TextInputEditText) findViewById(R.id.text_input_emp_fname);
        empEmail = (TextInputEditText) findViewById(R.id.text_input_emp_email_address);
        empPhone = (TextInputEditText) findViewById(R.id.text_input_emp_pnumber);
        empDOB = (TextInputEditText) findViewById(R.id.text_input_dob);

        grpTask = (RadioGroup) findViewById(R.id.radio_group_emp_task);

        //

        //add listeners to the textviews
        empDOB.setOnClickListener(this);
        takephoto.setOnClickListener(this);
        choosephoto.setOnClickListener(this);

        dbreference = firebasedb.getReference("/datadevcash");
        EmployeeId = dbreference.push().getKey();


    }

    public void addEmployee(Uri empImageUri, String empLname, String empFname, String empTask, String empDOB, String empGender, String empBdate, int empCtcnum){
        Employee employee = new Employee(empImageUri, empLname, empFname, empTask, empDOB, empGender, empBdate, empCtcnum);
        dbreference.child("/employees").child(String.valueOf(EmployeeId)).setValue(employee);
    }

    public void insertEmployee(){
//        addEmployee(imageUri, txtEmpLname, );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate SAVE menu
        getMenuInflater().inflate(R.menu.savemenu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Unsaved changes");
        builder.setMessage("Are you sure you want to leave without saving changes?");
        builder.setPositiveButton("LEAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //menu item click handling
        //if back button is clicked
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }else if(id == R.id.action_save){ //if SAVE is clicked
//            //collect the inputted data
//            String emplname = this.txtEmpLname.getText().toString();
//            String empfname = this.txtEmpFname.getText().toString();
//            String empemail = this.txtEmpEmail.getText().toString();
//            String empphone = this.txtEmpPhone.getText().toString();


        }
        return super.onOptionsItemSelected(item);

    }


    // handles camera clicks
    @Override
    public void onClick(View v) {
        int sid = v.getId();

        switch (sid){

            case R.id.txt_empchoosephoto:
                Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
                break;
            case R.id.txt_emptakephoto:
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 0);
                break;
            case R.id.text_input_dob:
                //calendar class's instance and get current date, month and year from calendar
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); //current year
                int mMonth = c.get(Calendar.MONTH); //current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); //current date of the month

                //date picker dialog
                bdatePickerDia = new DatePickerDialog(AddEmployeeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //set day month, month and year value in the textinputedittext
                                empDOB.setText(dayOfMonth + "/"
                                                + (month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                            bdatePickerDia.show();
                break;
        }
    }

    //handles opening the camera and gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            empimageUri = data.getData();
            empphoto.setImageURI(empimageUri);
        }else{
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            empphoto.setImageBitmap(bitmap);
        }

    }





}
