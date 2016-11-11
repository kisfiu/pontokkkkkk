/*
 * MainMenuActivity.java
 *
 * Norbiron Game
 * This is a case study for creating sprites for SamuEntropy/Brainboard.
 *
 * Copyright (C) 2016, Dr. Bátfai Norbert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Ez a program szabad szoftver; terjeszthető illetve módosítható a
 * Free Software Foundation által kiadott GNU General Public License
 * dokumentumában leírtak; akár a licenc 3-as, akár (tetszőleges) későbbi
 * változata szerint.
 *
 * Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
 * de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA
 * VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve.
 * További részleteket a GNU General Public License tartalmaz.
 *
 * A felhasználónak a programmal együtt meg kell kapnia a GNU General
 * Public License egy példányát; ha mégsem kapta meg, akkor
 * tekintse meg a <http://www.gnu.org/licenses/> oldalon.
 *
 * Version history:
 *
 * 0.0.1, 2013.szept.29.
 */

package batfai.samuentropy.brainboard8;

/**
 *
 * @author nbatfai
 */

import android.widget.ImageView;
import android.view.View;
import android.widget.FrameLayout;          
import java.util.*;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.app.FragmentTransaction; 
import android.app.Fragment;

public class MainMenuActivity extends android.app.Activity 
{

	private FrameLayout frameLayout;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private Configuration configInfo;

	

    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
	{

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_new);
		
		frameLayout = (FrameLayout)findViewById(R.id.mainframelayout);
	
		ViewTreeObserver vto = frameLayout.getViewTreeObserver(); 
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{ 
	
	    	@Override 
	    	public void onGlobalLayout()
			{ 

				fragmentManager = getFragmentManager();	
				fragmentTransaction = fragmentManager.beginTransaction();

				configInfo = getResources().getConfiguration();
				UserFragment userFragment = new UserFragment();
				NeurFragment neurFragment = new NeurFragment();
				//fragmentTransaction.add(R.id.mainframelayout, userFragment);
				fragmentTransaction.replace(R.id.mainframelayout, neurFragment);

				fragmentTransaction.commit();

	    	} 
		});
		
    }


    @Override
    public boolean onTouchEvent(android.view.MotionEvent event)
	{
		
		float x = event.getX();
        float y = event.getY();

		if (event.getAction() == android.view.MotionEvent.ACTION_DOWN)
		{


		}

		else if (event.getAction() == android.view.MotionEvent.ACTION_CANCEL)
		{

		}

		else if (event.getAction() == android.view.MotionEvent.ACTION_MOVE)
		{

		}

		else if (event.getAction() == android.view.MotionEvent.ACTION_UP)
		{

		}

		return true;
	}

}

