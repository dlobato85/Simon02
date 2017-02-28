package com.dustin_domas_assignment.simon02;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    private BackgroundTask backgroundTask;
    private ArrayList<Integer> data;
    private Object lock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        data = new ArrayList<>();
        lock = new Object();
    }

    class BackgroundListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(backgroundTask==null){
                backgroundTask = new BackgroundTask();
                backgroundTask.execute();
            }
        }

    }
    //background task
    class BackgroundTask extends AsyncTask<Void, Integer, Void> {
        private TextView messageTv;
        private int messageCount;
        BackgroundTask() {
            //messageTv = (TextView) findViewById(R.id.messageTv);

        }

        protected void onPreExecute(){
            messageTv.setText("");
        }

        @Override
        protected Void doInBackground(Void... params) {



            while(!isCancelled()) {
                if (data.size() > 0) {

                    int sum = 0;
                    synchronized (lock) {

                        for (int i : data) {
                            sum += 1;
                        }

                        data.clear();



                    }
                    if(sum > 0) {
                        publishProgress(sum);
                    }
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer...values){

            messageTv.setText("Sum = " + values[0]);
        }//hey

//post execute function

        @Override
        protected void onPostExecute(Void aVoid){
            messageTv.setText("Background Thread Finished");

            backgroundTask = null;
        }

        @Override
        protected void onCancelled() {
            messageTv.setText("Cancelled");
        }

    }



}


