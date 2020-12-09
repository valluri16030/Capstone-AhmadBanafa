package com.npu.mathfun.mathfun;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class GameScreen extends Activity{
    Button button0 , button1 , button2 , button3 , button4 , button5 , button6 ,
            button7 , button8 , button9 ,
              buttonC , buttonEqual ;

    EditText edt1 ;
    ImageView animatedImage2,animatedImage1,animatedImage3,energybar;
    private Animation mAnimation,mAnimation2,mAnimation3;
    TextView textView_one,textView_two,textView_three,score;
    float mValueOne , mValueTwo ;
    int myScore=0;

    ArrayList<String> questions = new ArrayList<>();
    ArrayList<Integer> answers = new ArrayList<>();

    String selectedDigit = null;
    String selectedOperation = null;
    MediaPlayer mp;
    MediaPlayer mpSound_special,mpSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);

        textView_one=(TextView)findViewById(R.id.textview_one);
        textView_two=(TextView)findViewById(R.id.textView2);
        textView_three=(TextView)findViewById(R.id.textView3);

        mp= MediaPlayer.create(this, R.raw.jungle_sound_one);
        mpSound_special =MediaPlayer.create(this,R.raw.button_sound_special);
        mpSound =MediaPlayer.create(this,R.raw.button_sound_special_two);
        playMusic();
        playSound_special();
        playSound();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                selectedDigit= null;
                selectedOperation=null;
            } else {
                selectedDigit= extras.getString("STRING_SELECTED_DIGIT");
                selectedOperation= extras.getString("STRING_SELECTED_OPERATION");
            }
        } else {
            selectedDigit= (String) savedInstanceState.getSerializable("STRING_SELECTED_DIGIT");
            selectedOperation= (String) savedInstanceState.getSerializable("STRING_SELECTED_OPERATION");
        }

        buttonC = (Button) findViewById(R.id.buttonC);
        buttonEqual = (Button) findViewById(R.id.buttoneql);
        edt1 = (EditText) findViewById(R.id.edt1);
        score=(TextView)findViewById(R.id.score);
        energybar=(ImageView)findViewById(R.id.energybar);

        score.setText("Score:"+myScore);

        edt1.setInputType(InputType.TYPE_NULL);

        String displayOperation = selectedOperation;
        Random rand = new Random();
        for(int k=0; k<5; k++){
            int num1;
            int num2;
            int ans = 0;

            if(selectedOperation.equalsIgnoreCase("combined")){
                int display = rand.nextInt(4) + 1;
                if(display == 1){
                    displayOperation = "addition";
                } else if(display == 2){
                    displayOperation = "subtract";
                } else if(display == 3){
                    displayOperation = "multiply";
                } else {
                    displayOperation = "subtract";
                }
            }
            if(displayOperation.equalsIgnoreCase("division")){
                num1 = rand.nextInt(7) + 2;
                num2 = rand.nextInt(11) + 2;
            } else if(selectedDigit.equalsIgnoreCase("one")){
                num1 = rand.nextInt(9) + 1;
                num2 = rand.nextInt(9) + 1;
            } else {
                num1 = rand.nextInt(20) + 10;
                num2 = rand.nextInt(20) + 10;
            }

            if(displayOperation.equalsIgnoreCase("addition")){
                questions.add(num1 + " + " + num2);
                ans = num1 + num2;
                answers.add(ans);
            } else if (displayOperation.equalsIgnoreCase("multiply")){
                questions.add(num1 + " x " + num2);
                ans = num1 * num2;
                answers.add(ans);
            } else if (displayOperation.equalsIgnoreCase("subtract")){
                if(num1 > num2) {
                    questions.add(num1 + " - " + num2);
                    ans = num1-num2;
                } else {
                    questions.add(num2 + " - " + num1);
                    ans = num2 - num1;
                }
                answers.add(ans);
            } else if (displayOperation.equalsIgnoreCase("division")){
                num1 = num1*num2;
                questions.add(num1 + " / " + num2);
                ans = num1 / num2;
                answers.add(ans);
            }
        }


        textView_one.setText(questions.get(0));
        textView_two.setText(questions.get(1));
        textView_three.setText(questions.get(2));

        animatedImage1=(ImageView)findViewById(R.id.animatedImage1);
        animatedImage2=(ImageView)findViewById(R.id.animatedImage2);
        animatedImage3=(ImageView)findViewById(R.id.animatedImage3);

        mAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);

        mAnimation2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_two);

        mAnimation3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_three);

        animatedImage1.setVisibility(View.VISIBLE);
        animatedImage2.setVisibility(View.VISIBLE);
        animatedImage3.setVisibility(View.VISIBLE);

        // start the animation
        animatedImage1.startAnimation(mAnimation);
        animatedImage2.startAnimation(mAnimation2);
        animatedImage3.startAnimation(mAnimation3);
        textView_one.startAnimation(mAnimation);
        textView_two.startAnimation(mAnimation2);
        textView_three.startAnimation(mAnimation3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"4");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"5");
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"6");
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"7");
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"8");
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"9");
            }
        });
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText()+"0");
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueTwo = Float.parseFloat(edt1.getText() + "");
                onAnimationEnd();
            }
        });


        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText("");
            }
        });
    }

    public void onAnimationEnd() {
        // Take any action after completing the animation
        if ( mValueTwo==answers.get(0)){
            Toast.makeText(getApplicationContext(), "1",
                    Toast.LENGTH_SHORT).show();
            myScore=10;
            score.setText("Score:"+String.valueOf(myScore));
        }else if ( mValueTwo==answers.get(1)){
            Toast.makeText(getApplicationContext(), "2",
                    Toast.LENGTH_SHORT).show();
            myScore=myScore+10;
            score.setText("Score:"+String.valueOf(myScore));
        }else if ( mValueTwo==answers.get(2)){
            Toast.makeText(getApplicationContext(), "3",
                    Toast.LENGTH_SHORT).show();
            myScore=myScore+10;
            score.setText("Score:" + String.valueOf(myScore));
        }else {
            Toast.makeText(getApplicationContext(), "Wrong",
                    Toast.LENGTH_SHORT).show();
        }

        //Setting energybar for corresponding score
        if (myScore==0){
            energybar.setBackgroundResource(R.drawable.energybar_empty);
        }else if (myScore>=10){
            energybar.setBackgroundResource(R.drawable.energybar_red);
        }else if (myScore>=20){
            energybar.setBackgroundResource(R.drawable.energybar_blue);
        }else if (myScore>=30){
            energybar.setBackgroundResource(R.drawable.energybar_orange);
        }else{
            energybar.setBackgroundResource(R.drawable.energybar_green);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(GameScreen.this,SelectOperation.class);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
        startActivity(intent);
        GameScreen.this.finish();
        mp.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.stop();
    }
    private void playMusic() {

        if (OptionsPage.musicOn == null) {
            mp.start();
        } else if (OptionsPage.musicOn.equalsIgnoreCase("true")) {
            mp.start();
        }else {
            HomeActivity.toggleOff=true;
        }
    }
    private void playSound_special() {

        if (OptionsPage.soundOn == null) {
            mpSound_special.start();
        } else if (OptionsPage.soundOn.equalsIgnoreCase("true")) {
            mpSound_special.start();
        }else {
            HomeActivity.toggleOff=true;
        }
    }
    private void playSound() {

        if (OptionsPage.soundOn == null) {
            mpSound.start();
        } else if (OptionsPage.soundOn.equalsIgnoreCase("true")) {
            mpSound.start();
        }else {
            HomeActivity.toggleOff=true;
        }
    }

}






















