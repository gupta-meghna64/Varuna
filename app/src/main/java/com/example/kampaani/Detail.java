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
import android.widget.ImageButton;
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




public class Detail extends AppCompatActivity {

    public final static String PREF_IP = "PREF_IP_ADDRESS";
    public final static String PREF_PORT = "PREF_PORT_NUMBER";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private TextView aa, bb, cc, dd;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public static String messageResponse;
    public ImageButton refreshBut;
    private ImageButton plant1But, plant2But, plant3But;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        sharedPreferences = getSharedPreferences("HTTP_HELPER_PREFS", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final int[] nametxtviewid = {R.id.plant1Name, R.id.plant2Name, R.id.plant3Name, R.id.plant4Name};
        final int[] numbertxtviewid = {R.id.plant1Number, R.id.plant2Number, R.id.plant3Number, R.id.plant4Number};
        final int[] moisturetxtviewid = {R.id.plant1Moisture, R.id.plant2Moisture, R.id.plant3Moisture, R.id.plant4Moisture};
        final int[] temperaturetxtviewid = {R.id.plant1Temperature, R.id.plant2Temperature, R.id.plant3Temperature, R.id.plant4Temperature};
        final int[] refreshButid = {R.id.plant1Refresh, R.id.plant2Refresh, R.id.plant3Refresh, R.id.plant4Refresh};
        plant1But = (ImageButton) findViewById(refreshButid[0]);
        plant2But = (ImageButton) findViewById(refreshButid[1]);
        plant3But = (ImageButton) findViewById(refreshButid[2]);

        FloatingActionButton fab5 = (FloatingActionButton) findViewById(R.id.detailog);
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                loadLogInView();
            }
        });


        FloatingActionButton fab6 = (FloatingActionButton) findViewById(R.id.detailhome);
        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });


        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mUserId = mFirebaseUser.getUid();
            //a = (TextView) findViewById(R.id.val);
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int k = 0;
                    for (DataSnapshot d : dataSnapshot.child("Users").child(mUserId).getChildren()) {
                        if (k <= 3) {
                            Plant c = d.getValue(Plant.class);
                            aa = (TextView) findViewById(nametxtviewid[k]);
                            bb = (TextView) findViewById(numbertxtviewid[k]);
                            refreshBut = (ImageButton) findViewById(refreshButid[k]);
                            //a.setText(a.getText().toString().concat("\n").concat(c.getName()).concat("\n").concat(c.getNumber()));
                            String s = "Plant Name: ";
                            String s1 = c.getName();
                            String s2 = s.concat(s1);
                            String t = "Plant Number: ";
                            String t1 = c.getNumber();
                            String t2 = t.concat(t1);
                            aa.setText(s2);
                            bb.setText(t2);
                            refreshBut.setVisibility(View.VISIBLE);
                            k++;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


        plant1But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the pin number
                String parameterValue = "";
                // get the ip address
                //String ipAddress = editTextIPAddress.getText().toString().trim();
                // get the port number
                //String portNumber = editTextPortNumber.getText().toString().trim();

                String ipAddress = "192.168.43.44";
                String portNumber = "80";


                // save the IP address and port for the next time the app is used
                editor.putString(PREF_IP, ipAddress); // set the ip address value to save
                editor.putString(PREF_PORT, portNumber); // set the port number to save
                editor.commit(); // save the IP and PORT
                parameterValue = "0N";
                // execute HTTP request
                if (ipAddress.length() > 0 && portNumber.length() > 0) {
                    new Detail.HttpRequestAsyncTask(
                            v.getContext(), parameterValue, ipAddress, portNumber, "plant"
                    ).execute();
                }
            }
        });

        plant2But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                parameterValue="5N";
                // execute HTTP request
                if(ipAddress.length()>0 && portNumber.length()>0) {
                    new Detail.HttpRequestAsyncTask(
                            v.getContext(), parameterValue, ipAddress, portNumber, "plant"
                    ).execute();
                }
            }
        });


        plant3But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                parameterValue="9N";
                // execute HTTP request
                if(ipAddress.length()>0 && portNumber.length()>0) {
                    new Detail.HttpRequestAsyncTask(
                            v.getContext(), parameterValue, ipAddress, portNumber, "plant"
                    ).execute();
                }
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
            messageResponse=requestReply;
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