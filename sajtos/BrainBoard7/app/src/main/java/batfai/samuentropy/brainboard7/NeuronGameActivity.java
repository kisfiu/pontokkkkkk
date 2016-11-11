package batfai.samuentropy.brainboard7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class NeuronGameActivity extends android.app.Activity
{
	private String currentUser;
	private RelativeLayout rl;

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		if (savedInstanceState == null)
		{
			Bundle extras = getIntent().getExtras();
			if(extras == null)
			{
				currentUser = "";
			}
			else
			{
				currentUser = extras.getString("username");
			}
		}
		else
		{
			currentUser = (String) savedInstanceState.getSerializable("username");
		}

		NorbironSurfaceView nsv = new NorbironSurfaceView(this, currentUser);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		//params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		//params.leftMargin = 107

		rl = (RelativeLayout) findViewById(R.id.relativeLayout);
		rl.addView(nsv, params);
	}

}
