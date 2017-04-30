package com.example.kampaani;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by jains on 01-03-2017.
 */

public class WaterPlants extends AppCompatActivity{

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private TextView a;
    private TextView b;
    private Button but;

    public final static String PREF_IP = "PREF_IP_ADDRESS";
    public final static String PREF_PORT = "PREF_PORT_NUMBER";
    // declare buttons and text inputs
    private Button buttonPin11,buttonPin12,buttonPin13;
    //private EditText editTextIPAddress, editTextPortNumber;
    private TextView watertextview1, watertextview2, watertextview3;
    int plant1, plant2, plant3;
    // shared preferences objects used to save the IP address and port so that the user doesn't have to
    // type them next time he/she opens the app.
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_plants);

        sharedPreferences = getSharedPreferences("HTTP_HELPER_PREFS",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        buttonPin11 = (Button)findViewById(R.id.water1);
        buttonPin12 = (Button)findViewById(R.id.water2);
        buttonPin13 = (Button)findViewById(R.id.water3);


        final int[] water_txtviewnameid = {R.id.wat_text1, R.id.wat_text2, R.id.wat_text3, R.id.wat_text4};

        final int[] button_id = {R.id.water1, R.id.water2, R.id.water3, R.id.water4};
        final int[]  water_txtviewnumberid = {R.id.wat_textnumber1, R.id.wat_textnumber2, R.id.wat_textnumber3, R.id.wat_textnumber4};



        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mUserId = mFirebaseUser.getUid();
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int k = 0;
                    for(DataSnapshot d : dataSnapshot.child("Users").child(mUserId).getChildren())
                    {
                        Plant c=d.getValue(Plant.class);
                        a=(TextView) findViewById(water_txtviewnameid[k]);
                        b = (TextView)findViewById(water_txtviewnumberid[k]);
                        but = (Button) findViewById(button_id[k]);
                        a.setText(c.getName());
                        b.setText(c.getNumber());
                        but.setVisibility(View.VISIBLE);
                        k++;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        buttonPin11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the pin number
                String parameterValue = "";
                // get the ip address
                //String ipAddress = editTextIPAddress.getText().toString().trim();
                // get the port number
                //String portNumber = editTextPortNumber.getText().toString().trim();

                String ipAddress = "192.168.43.44";
                String portNumber = "80";


                // save the IP address and port for the next time the app is used
                editor.putString(PREF_IP,ipAddress); // set the ip address value to save
                editor.putString(PREF_PORT,portNumber); // set the port number to save
                editor.commit(); // save the IP and PORT
                parameterValue="0Y";
                // execute HTTP request
                if(ipAddress.length()>0 && portNumber.length()>0) {
                    new HttpRequestAsyncTask(
                            view.getContext(), parameterValue, ipAddress, portNumber, "plant"
                    ).execute();
                }
            }

        });
        buttonPin12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the pin number
                String parameterValue = "";
                // get the ip address
                //String ipAddress = editTextIPAddress.getText().toString().trim();
                // get the port number
                //String portNumber = editTextPortNumber.getText().toString().trim();

                String ipAddress = "192.168.43.44";
                String portNumber = "80";


                // save the IP address and port for the next time the app is used
                editor.putString(PREF_IP,ipAddress); // set the ip address value to save
                editor.putString(PREF_PORT,portNumber); // set the port number to save
                editor.commit(); // save the IP and PORT
                parameterValue="5Y";
                // execute HTTP request
                if(ipAddress.length()>0 && portNumber.length()>0) {
                    new HttpRequestAsyncTask(
                            view.getContext(), parameterValue, ipAddress, portNumber, "plant"
                    ).execute();
                }
            }

        });
        buttonPin13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the pin number
                String parameterValue = "";
                // get the ip address
                //String ipAddress = editTextIPAddress.getText().toString().trim();
                // get the port number
                //String portNumber = editTextPortNumber.getText().toString().trim();

                String ipAddress = "192.168.43.44";
                String portNumber = "80";


                // save the IP address and port for the next time the app is used
                editor.putString(PREF_IP,ipAddress); // set the ip address value to save
                editor.putString(PREF_PORT,portNumber); // set the port number to save
                editor.commit(); // save the IP and PORT
                parameterValue="9Y";
                // execute HTTP request
                if(ipAddress.length()>0 && portNumber.length()>0) {
                    new HttpRequestAsyncTask(
                            view.getContext(), parameterValue, ipAddress, portNumber, "plant"
                    ).execute();
                }
            }

        });

        // get the IP address and port number from the last time the user used the app,
        // put an empty string "" is this is the first time.
        //editTextIPAddress.setText(sharedPreferences.getString(PREF_IP,""));
        //editTextPortNumber.setText(sharedPreferences.getString(PREF_PORT,""));,


        FloatingActionButton fab7 = (FloatingActionButton) findViewById(R.id.waterlog);
        fab7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                loadLogInView();
            }
        });


        FloatingActionButton fab8 = (FloatingActionButton) findViewById(R.id.waterhome);
        fab8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });



    }





    /**
     * Description: Send an HTTP Get request to a specified ip address and port.
     * Also send a parameter "parameterName" with the value of "parameterValue".
     * @param parameterValue the pin number to toggle
     * @param ipAddress the ip address to send the request to
     * @param portNumber the port number of the ip address
     * @param parameterName
     * @return The ip address' reply text, or an ERROR message is it fails to receive one
     */
    public String sendRequest(String parameterValue, String ipAddress, String portNumber, String parameterName) {
        String serverResponse = "ERROR";

        try {

            HttpClient httpclient = new DefaultHttpClient(); // create an HTTP client
            // define the URL e.g. http://myIpaddress:myport/?pin=13 (to toggle pin 13 for example)
            URI website = new URI("http://"+ipAddress+":"+portNumber+"/?"+parameterName+"="+parameterValue);
            HttpGet getRequest = new HttpGet(); // create an HTTP GET object
            getRequest.setURI(website); // set the URL of the GET request
            HttpResponse response = httpclient.execute(getRequest); // execute the request
            // get the ip address server's reply
            InputStream content = null;
            content = response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    content
            ));
            serverResponse = in.readLine();
            // Close the connection
            content.close();

        } catch (ClientProtocolException e) {
            // HTTP error
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            // IO error
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // URL syntax error
            serverResponse = e.getMessage();
            e.printStackTrace();
        }
        // return the server's reply/response text
        return serverResponse;
    }


    /**
     * An AsyncTask is needed to execute HTTP requests in the background so that they do not
     * block the user interface.
     */
    private class HttpRequestAsyncTask extends AsyncTask<Void, Void, Void> {

        // declare variables needed
        private String requestReply,ipAddress, portNumber;
        private Context context;
        private AlertDialog alertDialog;
        private String parameter;
        private String parameterValue;

        /**
         * Description: The asyncTask class constructor. Assigns the values used in its other methods.
         * @param context the application context, needed to create the dialog
         * @param parameterValue the pin number to toggle
         * @param ipAddress the ip address to send the request to
         * @param portNumber the port number of the ip address
         */
        public HttpRequestAsyncTask(Context context, String parameterValue, String ipAddress, String portNumber, String parameter)
        {
            this.context = context;

            alertDialog = new AlertDialog.Builder(this.context)
                    .setTitle("HTTP Response From IP Address:")
                    .setCancelable(true)
                    .create();

            this.ipAddress = ipAddress;
            this.parameterValue = parameterValue;
            this.portNumber = portNumber;
            this.parameter = parameter;
        }

        /**
         * Name: doInBackground
         * Description: Sends the request to the ip address
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {
            alertDialog.setMessage("Data sent, waiting for reply from server...");
            if(!alertDialog.isShowing())
            {
                alertDialog.show();
            }
            requestReply = sendRequest(parameterValue,ipAddress,portNumber, parameter);
            return null;
        }

        /**
         * Name: onPostExecute
         * Description: This function is executed after the HTTP request returns from the ip address.
         * The function sets the dialog's message with the reply text from the server and display the dialog
         * if it's not displayed already (in case it was closed by accident);
         * @param aVoid void parameter
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            alertDialog.setMessage(requestReply);
            if(!alertDialog.isShowing())
            {
                alertDialog.show(); // show dialog
            }
        }

        /**
         * Name: onPreExecute
         * Description: This function is executed before the HTTP request is sent to ip address.
         * The function will set the dialog's message and display the dialog.
         */
        @Override
        protected void onPreExecute() {
            alertDialog.setMessage("Sending data to server, please wait...");
            if(!alertDialog.isShowing())
            {
                alertDialog.show();
            }
        }

    }


    private void loadLogInView() {
        Intent intent = new Intent(this, startup.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}