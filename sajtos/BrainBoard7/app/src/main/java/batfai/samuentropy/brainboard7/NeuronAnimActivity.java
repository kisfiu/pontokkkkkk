/*
 * NeuronAnimActivity.java
 *
* Norbiron
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
package batfai.samuentropy.brainboard7;

        import android.view.animation.AnimationSet;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.LinearLayout;
        import android.view.ViewTreeObserver;
        import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class NeuronAnimActivity extends android.app.Activity
{
        private NorbironSurfaceView surfaceView;

    private android.widget.ImageView iv;
    private Animation anim_rotate;
    private Animation anim_orbit;
    private Animation anim_spin;
    private LinearLayout mainLayout;

    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.norbiron_anim);

        mainLayout = (LinearLayout)findViewById(R.id.mainlayout);
        ViewTreeObserver vto = mainLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {

            @Override
            public void onGlobalLayout()
            {
                mainLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                startAnim();

            }
        });


    }



    public void startAnim()
    {
        iv = (android.widget.ImageView) findViewById(R.id.neuronanimation);
        anim_rotate = AnimationUtils.loadAnimation(this, R.anim.bg_anim_scale_bigger);
        anim_orbit = AnimationUtils.loadAnimation(this, R.anim.bg_anim_translate_rightup_v2);
        anim_spin = AnimationUtils.loadAnimation(this, R.anim.bg_anim_rotate_left);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(anim_rotate);
        animationSet.addAnimation(anim_orbit);
        animationSet.addAnimation(anim_spin);
        iv.startAnimation(animationSet);
    }
    
    
    
    
    /*

@Override
    public boolean onTouchEvent(android.view.MotionEvent event)
    {
        scaleGestureDetector.onTouchEvent(event);

        float x = event.getX() / scaleFactor;
        float y = event.getY() / scaleFactor;

        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN)
        {
            fromsx = x;
            fromsy = y;

            NeuronBox nb2 = norbironMap.getNearestNeuron(x + startsx, y + startsy);

            if(nb2 != null)
            {
                if (nb2.getType() == 0)
                {
                    newNode();
                }
                else if (nb2.getType() == 1)
                {
                    newBox();
                }
                else
                {
                    nb2.setCover(!nb2.getCover());
                    nb2.setSelected(!nb2.getSelected());
                    selNb = nb2;
                }
            }
            else
            {
                selNb = null;
            }
        }

        else if (event.getAction() == android.view.MotionEvent.ACTION_POINTER_DOWN)
        {
            selNb = null;
        }

        else if (event.getAction() == android.view.MotionEvent.ACTION_CANCEL)
        {

        }

        else if (event.getAction() == android.view.MotionEvent.ACTION_MOVE)
        {

            if (selNb != null)
            {

                int nx,ny;

                nx = (int)(x + startsx) / SLOT_SIZE;
                ny = (int)(y + startsy) / SLOT_SIZE;

                selNb.setXY(nx,ny);
                norbironMap.saveMapToServer(currentUser);

                fromsx = x;
                fromsy = y;

            }

            else if (Math.abs(fromsx - x) + Math.abs(fromsy - y) > 25)
            {
                startsx += (fromsx - x);
                startsy += (fromsy - y);

                fromsx = x;
                fromsy = y;
            }

            repaint();
        }

        else if (event.getAction() == android.view.MotionEvent.ACTION_UP)
        {
            if (selNb != null)
            {
                int nx,ny;

                nx = (int)(x + startsx) / SLOT_SIZE;
                ny = (int)(y + startsy) / SLOT_SIZE;

                selNb.setXY(nx,ny);


                fromsx = x;
                fromsy = y;

                selNb = null;
            }
        }
        return true;
    }
    */

}

