package com.example.tomahawk.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton; // Use for having a button with just an image
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mBackButton;
    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;
    private int TokenArray[] = {7, 7, 7, 7, 7, 7}; // 7 used as default value
    //private int i = 0;

    /**
     * To disable the buttons to complete the challenge...
     *
     * Add extra bool variable to the question object.
     * Mark if question was answered.---> requires some logic and algorithms
     * Store marked questions in array or hash map to cross check if answered
     * Create methods to check if question at given index has been answered and if so disable button, if not enable button
     *
     */

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);// Calling this method to inflate the layout, use resource id to specify which layout

        if (savedInstanceState != null){
            Log.d(TAG, "Checking savedInstanceState 1");
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            Log.d(TAG, "Checking savedInstanceState 2");
        }

        Log.d(TAG, "QuestionTextView");
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        // This is a challenge that did not get commented out because it's hidden and kinda fun to use
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){ // Set an onClickListener to the TextView
            public void onClick(View v){                                 // thereby allowing the user to change questions by clicking the text
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
            }
        });

        Log.d(TAG, "True Button");
        // BNR uses anonymous classes to keep things simple,
        // the new class is the listener(OnClickListener), and we pass its entire implementation to setOnClickListener
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int i = 0;
                checkAnswer(true); // Calling the checkAnswer method to check the answer
                //isCheckedTrue(); // Dunno if I need this or not, bout to find out
                do {
                    mTrueButton.setEnabled(true);

                }
                while(mCurrentIndex != TokenArray[i]);

                if(i == 6){
                    i = 0;
                }else{
                    i++;
                }

                //Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show(); // This is how we make a toast, one way at least

            }
        });

        Log.d(TAG, "False Button");
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this, R.string.wrong_toast, Toast.LENGTH_SHORT).show();
                isCheckedFalse();
                checkAnswer(false); // Same as above
            }
        });

        Log.d(TAG, "Next Button");
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){ // OnClickListener
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length; // Calculates question index
                int question = mQuestionBank[mCurrentIndex].getTextResId(); // Uses index to extract res id and sets to question
                mQuestionTextView.setText(question); // Uses question (the text res id), to set text to the textView
            }
        });

        Log.d(TAG, "Back Button");
        // This is a challenge but it does not change the main function of the app so I didn't comment it out,
        // I did however fix the crash problem with the back button, hence the extra logic below
        mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(mCurrentIndex == 0){
                    mCurrentIndex = 0;
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    int question = mQuestionBank[mCurrentIndex].getTextResId();
                    mQuestionTextView.setText(question);
                }
            }
        });

        updateQuestion(); // Call to the updateQuestion method

    }


    private void isCheckedTrue(){
        for(int i = 0; i < TokenArray.length; i++){
            if(mCurrentIndex == TokenArray[i]){
               mTrueButton.setEnabled(false);
            }else{
                TokenArray[i] = mCurrentIndex;
                mTrueButton.setEnabled(true);
            }
        }
    }

    private void isCheckedFalse(){
        for(int i = 0; i < TokenArray.length; i++){
            if(mCurrentIndex == TokenArray[i]){
                mFalseButton.setEnabled(false);
            }else{
                TokenArray[i] = mCurrentIndex;
                mFalseButton.setEnabled(true);
            }
        }
    }

    private void updateQuestion(){ // Method that gets a question based on index and sets it to the TextView
        Log.d(TAG, "updateQuestion() called");
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

   private void checkAnswer(boolean userPressedTrue){ // Method that checks if the user got the answer right or wrong
       boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue(); // Retrieves the boolean from the question and sets it to answerIsTrue

       int messageResId = 0;

       if(userPressedTrue == answerIsTrue){ // Logic to check if user answered correctly
           messageResId = R.string.correct_toast;
       } else {
           messageResId = R.string.wrong_toast;
       }
       Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
   }

    // Overriding these methods to learn logging and stack tracing
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}
