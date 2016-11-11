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


import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class AnimationHandler
{

	private java.util.ArrayList<Animation> anims;
	private java.util.ArrayList<Integer> animRes;
	private java.util.ArrayList<View> views;
	private int count = 0;

	public AnimationHandler()
	{
		animRes = new java.util.ArrayList<Integer>();
		views = new java.util.ArrayList<View>();
	}

	public int addAnimation(View v, Animation anim)
	{
		views.add(v);
		animRes.add(anim);
		Animation animation = AnimationUtils.loadAnimation(this, anim);
		anims.add(animation);
		return ++count;
	}

	public void clearAnimations()
	{
		animRes.clear();
		views.clear();
	}

	public void deleteAnimation(int id)
	{
		animRes.remove(id);	
		views.remove(id);	
	}

	public void startAnimations(java.util.ArrayList<Integer> animRes)
	{
		for(int i = 0; i<animRes.size(); i++)
		{
			Animation animation = AnimationUtils.loadAnimation(this, this.animRes.get(i));
			views.get(i).startAnimation(animation);
		}

	}

	private void setAnimationListener(int id)
	{

	    if(v != null)
		{
			anims.get(id).setFillAfter(false);
			views.get(id).setAnimation(anim);

			anims.get(id).setAnimationListener(new AnimationListener()
			{
                    public void onAnimationStart(Animation anim)
                    {
                    };
                    public void onAnimationRepeat(Animation anim)
                    {
                    };
                    public void onAnimationEnd(Animation anim)
                    {
                    };

            });                     

            views.get(id).startAnimation(anims.get(id));                 
		}
	}
}


