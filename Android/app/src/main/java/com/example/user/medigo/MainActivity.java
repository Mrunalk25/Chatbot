package com.example.user.medigo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.ResponseMessage;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener{
    AIService aiService;
    TextView t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t=findViewById(R.id.Text1);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
        final AIConfiguration config = new AIConfiguration("b4e7e055a45f445389bf59c26e359fdc",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);


      aiService = AIService.getService(this, config);
        aiService.setListener(this);
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                101);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {


                } else {

                }
                return;
            }
        }
    }
public void buttonClick(View view){
    aiService.startListening();

}
    @SuppressLint("SetTextI18n")
    @Override
    public void onResult(final AIResponse response) {
                Result result = response.getResult();

        // Get parameters


        final String speech = result.getFulfillment().getSpeech();
        Log.i("munu", "Speech: " + speech);


        // Show results in TextView.
        t.setText(  "\nQuery:" + result.getResolvedQuery()+

                    "\nChatbot: " +speech);




    }

    @Override
    public void onError(final AIError error) {
        t.setText(error.toString());
    }
    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {
        Toast.makeText(this, "Listening started", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
