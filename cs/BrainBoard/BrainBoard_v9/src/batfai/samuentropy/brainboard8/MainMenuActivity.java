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


class MenuAdapter extends android.widget.BaseAdapter
{

    private android.content.Context context;
    java.util.ArrayList<Integer> menuItems = new java.util.ArrayList<Integer>();

    public void setMenuItems(java.util.ArrayList<Integer> menuItems)
	{
        this.menuItems = menuItems;
    }

    public MenuAdapter(android.content.Context context)
	{
        cinit(context);
    }

    public MenuAdapter(android.content.Context context, android.util.AttributeSet attrs)
	{
        cinit(context);
    }

    public MenuAdapter(android.content.Context context,
            android.util.AttributeSet attrs, int defStyle)
	{
        cinit(context);
    }

    private void cinit(android.content.Context context)
	{
        this.context = context;
    }

    public int getCount()
	{
        return menuItems.size();
    }

    public long getItemId(int position)
	{
        return menuItems.get(position);
    }

    public Object getItem(int position)
	{
        return menuItems.get(position);
    }

    public android.view.View getView(int position, android.view.View oldView, android.view.ViewGroup parent)
	{
        android.widget.ImageView imageView;

        if (oldView != null)
		{
            imageView = (android.widget.ImageView) oldView;
        }
		else
		{
            imageView = new android.widget.ImageView(context);
        }

		imageView.setAdjustViewBounds(true);
        imageView.setImageResource(menuItems.get(position));
        return imageView;
    }
}




public class MainMenuActivity extends android.app.Activity
{

	private java.util.ArrayList<Integer> menuItems;
	private android.widget.RelativeLayout relativeLayout;

    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
	{

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        android.content.Intent intent = getIntent();

		init();

    }

	public void init()
	{

		menuItems = new java.util.ArrayList<Integer>();
		menuItems.add(R.drawable.main_menu_account_v2);
		menuItems.add(R.drawable.main_menu_settings_v2);
		menuItems.add(R.drawable.main_menu_games_v2);
		menuItems.add(R.drawable.main_menu_brainboard_v2);
			
        android.widget.GridView gridView = (android.widget.GridView) findViewById(R.id.menuitems);
        MenuAdapter menuAdapter = new MenuAdapter(this);
        menuAdapter.setMenuItems(menuItems);
        gridView.setAdapter(menuAdapter);

        gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{
            public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id)
			{				
				if (position == 3)
				{
	                android.content.Intent intent = new android.content.Intent(view.getContext(), NeuronGameActivity.class);
					startActivity(intent);
				}

            }
        });

		relativeLayout = (android.widget.RelativeLayout)findViewById(R.id.bg);
		android.view.ViewTreeObserver vto = relativeLayout.getViewTreeObserver(); 
		vto.addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener()
		{ 
	
	    	@Override 
	    	public void onGlobalLayout()
			{ 
	    	    relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this); 
				startBackgroundAnimation();
	    	} 
		});
		

	}

	public void startBackgroundAnimation()
	{
		android.view.animation.Animation rotateLeft		= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_rotate_left);
		android.view.animation.Animation rotateRight	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_rotate_right);
		android.view.animation.Animation rotateLeft2		= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_rotate_left_v2);
		android.view.animation.Animation rotateRight2	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_rotate_right_v2);
		android.view.animation.Animation scaleSmaller	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_scale_smaller);
		android.view.animation.Animation scaleBigger	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_scale_bigger);
		android.view.animation.Animation transRightUp	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_translate_rightup);
		android.view.animation.Animation transRightUp2	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_translate_rightup_v2);		
		
		android.view.animation.AnimationSet iv1set = new android.view.animation.AnimationSet(false);
		iv1set.addAnimation(rotateLeft2);
		iv1set.addAnimation(transRightUp);
		findViewById(R.id.img_a).startAnimation(iv1set);

		android.view.animation.AnimationSet iv2set = new android.view.animation.AnimationSet(false);
		iv2set.addAnimation(rotateRight2);
		iv2set.addAnimation(transRightUp2);
		findViewById(R.id.img_b).startAnimation(iv2set);

		android.view.animation.AnimationSet iv3set = new android.view.animation.AnimationSet(false);
		iv3set.addAnimation(rotateLeft);
		iv3set.addAnimation(transRightUp);
		findViewById(R.id.img_c).startAnimation(iv3set);

		android.view.animation.AnimationSet iv4set = new android.view.animation.AnimationSet(false);
		iv4set.addAnimation(rotateRight);
		iv4set.addAnimation(transRightUp2);
		findViewById(R.id.img_d).startAnimation(iv4set);
		
	}


}
