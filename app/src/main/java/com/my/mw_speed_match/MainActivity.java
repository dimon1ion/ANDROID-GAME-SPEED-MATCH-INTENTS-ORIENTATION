package com.my.mw_speed_match;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int PREVIEW_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    int scoreResult = 0;
    int maxScore = 10;
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreText = findViewById(R.id.text_score);
        scoreText.setText(scoreResult + " / " + maxScore);

        findViewById(R.id.start_button).setOnClickListener(view -> {
            Intent previewImageActivity = new Intent(this, PreviewImageActivity.class);
            previewImageActivity.putExtra("maxScore", maxScore);
            startActivityForResult(previewImageActivity, PREVIEW_IMAGE_ACTIVITY_REQUEST_CODE);
            Log.i("game", "Get Result");
        });
        Log.i("game", "Main: Create -> Get Result");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PREVIEW_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            scoreResult = data.getIntExtra("scores", 0);
            Log.i("result", Integer.toString(scoreResult));
            scoreText.setText(scoreResult + " / " + maxScore);
            if (scoreResult == maxScore){
                TextView winnerText = findViewById(R.id.winner);
                winnerText.setText(R.string.you_won);
            }
            else{
                TextView winnerText = findViewById(R.id.winner);
                winnerText.setText("");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("scoreResult", scoreResult);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        scoreResult = savedInstanceState.getInt("scoreResult", 0);
    }
}