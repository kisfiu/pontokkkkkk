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
import android.widget.RelativeLayout;          
import java.util.*;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class UserMenuActivity extends android.app.Activity 
{

	int MENU_ITEM_SIZE = 90;
	int MENU_CIRCLE_RADIUS = 150;
	int MENU_CIRCLE_DIAMETER = 300;
	int MENU_CIRCLE_MID_X = 0;
	int MENU_CIRCLE_MID_Y = 0;
	double CIRCLE_INTERVAL = 60.0;
	double MAX_TAPPING_DISTANCE_TO_DETECT = 50;

	double MENU_ITEM_MAX_X_ORBIT = 0;
	double MENU_ITEM_MAX_Y_ORBIT = 0;
	double MENU_ITEM_MIN_X_ORBIT = 0;
	double MENU_ITEM_MIN_Y_ORBIT = 0;

	private float fromX = 0;
	private float fromY = 0;
	private int currentMenuItem = -1;

	private RelativeLayout menuContainer;
	private List<ImageView> menuItems;

    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_user);

   		menuItems = new ArrayList<ImageView>();

		menuContainer = (RelativeLayout)findViewById(R.id.menu_container);
		ViewTreeObserver vto = menuContainer.getViewTreeObserver(); 
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{ 
	
	    	@Override 
	    	public void onGlobalLayout()
			{ 
	    	    menuContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this); 

	    	    MENU_CIRCLE_MID_X = findViewById(R.id.menu_background).getMeasuredWidth() / 2;
	    	    MENU_CIRCLE_MID_Y = findViewById(R.id.menu_background).getMeasuredHeight() / 2;  
				
				addMenuItem();
				addMenuItem();
				addMenuItem();
				addMenuItem();

	    	} 
		});
    }

	public int getMenuItemCount()
	{
		return menuItems.size();	
	}

	public ImageView addMenuItem()
	{
		ImageView iv = new ImageView(this);
		iv.setId(getMenuItemCount() + 1);
		iv.setImageResource(R.drawable.img_a);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(MENU_ITEM_SIZE, MENU_ITEM_SIZE);

		double deg = (double)getMenuItemCount() * CIRCLE_INTERVAL * Math.PI / 180.0;
		double X = Math.cos(deg) * (MENU_CIRCLE_RADIUS + MENU_ITEM_SIZE / 2) + MENU_CIRCLE_MID_X - MENU_ITEM_SIZE / 2;
		double Y = Math.sin(deg) * (MENU_CIRCLE_RADIUS + MENU_ITEM_SIZE / 2) + MENU_CIRCLE_MID_Y - MENU_ITEM_SIZE / 2;

		params.leftMargin = (int)X;
		params.topMargin =  (int)Y;
		menuContainer.addView(iv, params);

		menuItems.add(iv);
		
		return iv;
	}

	public class Vector2d
	{
		public Vector2d(float x, float y)
		{
			this.x = x;
			this.y = y;
		}

		public float getX() {return x;}
		public float getY() {return y;}
		public void setX(float x) {this.x = x;}
		public void setY(float y) {this.y = y;}
		
		protected float x,y;
				
	}

	public Vector2d getVectorOfTwoPoints(float x1, float y1, float x2, float y2)
	{
		Vector2d v2d = new Vector2d(x2-x1,y2-y1);
		return v2d;
	}

	public Vector2d normalize(Vector2d v)
	{
		float l = (float)Math.sqrt( (double)(v.getX()*v.getX() + v.getY()*v.getY()) );
		v.setX(v.getX() / l);	
		v.setY(v.getY() / l);

		return v;	
	}

	public Vector2d rotateVector(Vector2d v, float deg)
	{
		Vector2d v_ = new Vector2d(0,0);
		v_.setX( (float)(Math.cos(deg)*v.getX() - Math.sin(deg)*v.getY()) );
		v_.setY( (float)(Math.sin(deg)*v.getX() + Math.cos(deg)*v.getY()) );
		return v_;
	}

	public Vector2d addVectorToVector(Vector2d v1, Vector2d v2)
	{
		Vector2d v = new Vector2d(v1.getX() + v2.getX(), v1.getY() + v2.getY());
		return v;
	}

    @Override
    public boolean onTouchEvent(android.view.MotionEvent event)
	{
		
		float x = event.getX();
        float y = event.getY();

		if (event.getAction() == android.view.MotionEvent.ACTION_DOWN)
		{
			fromX = x;
            fromY = y;
			currentMenuItem = getNearestMenuItem(x,y);
		}

		else if (event.getAction() == android.view.MotionEvent.ACTION_CANCEL)
		{
			currentMenuItem = -1;
			fromX = 0;
			fromY = 0;		
		}

		else if (event.getAction() == android.view.MotionEvent.ACTION_MOVE)
		{
			if (currentMenuItem != -1)
			{

				Vector2d v = new Vector2d(menuItems.get(currentMenuItem).getX() + MENU_ITEM_SIZE / 2 - MENU_CIRCLE_MID_X,
					menuItems.get(currentMenuItem).getY() + MENU_ITEM_SIZE / 2 - MENU_CIRCLE_MID_Y);

				v = rotateVector(v, 0.05f);
				Vector2d newCoords = new Vector2d(0,0);
				Vector2d tmp = new Vector2d((float)(MENU_CIRCLE_MID_X), (float)(MENU_CIRCLE_MID_Y));
				newCoords.setX( addVectorToVector( tmp, v ).getX() );
				newCoords.setY( addVectorToVector( tmp, v ).getY() );
				menuItems.get(currentMenuItem).setX( newCoords.getX() - MENU_ITEM_SIZE / 2 );
				menuItems.get(currentMenuItem).setY( newCoords.getY() - MENU_ITEM_SIZE / 2 );
		
                fromX = newCoords.getX();
                fromY = newCoords.getY();		
			}		
		}

		else if (event.getAction() == android.view.MotionEvent.ACTION_UP)
		{
			currentMenuItem = -1;
			fromX = 0;
			fromY = 0;
		}

		return true;
	}

    public float getDistance(float x1, float y1, float x2, float y2)
	{
        return (float)Math.sqrt( (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) );
    }

	protected int getNearestMenuItem(float x, float y)
	{
		int id = -1;
		double minDistance = MAX_TAPPING_DISTANCE_TO_DETECT;		
		
		for(int i = 0; i<menuItems.size(); i++)
		{
			float d = getDistance(menuItems.get(i).getX() + menuItems.get(i).getWidth() / 2, 
				menuItems.get(i).getY() + menuItems.get(i).getHeight() / 2, 
				x,y);
			if( d < minDistance )
			{
				id = i;
				minDistance = d;
			}
		}

		return id;
	}


}

