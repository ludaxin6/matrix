package sample.tencent.matrix;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MyTestActivity extends AppCompatActivity {
    private static final String TAG = "Matrix.MyTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_new_page);


//        IssueFilter.setCurrentFilter(IssueFilter.ISSUE_TRACE);
//
//        Plugin plugin = Matrix.with().getPluginByClass(TracePlugin.class);
//        if (!plugin.isPluginStarted()) {
//            MatrixLog.i(TAG, "plugin-trace start");
//            plugin.start();
//        }

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemClock.sleep(20 * 1000);
            }
        });
    }
}
