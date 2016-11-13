package batfai.samuentropy.brainboard7;

import android.app.Application;

import com.firebase.client.Firebase;

public class BrainBoard extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
