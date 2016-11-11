/*
 * NorbironSurfaceView.java
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

/**
 *
 * @author nbatfai
 */


package batfai.samuentropy.brainboard8;

import java.util.List;


public class NorbironSurfaceView extends android.view.SurfaceView implements Runnable {

	public int SLOT_SIZE = 120;
	private NorbironMap norbironMap = new NorbironMap(this);
	
    private float startsx = 0;
    private float startsy = 0;
    private float width = 2048;
    private float height = 2048;

    protected float swidth;
    protected float sheight;
    protected float fromsx;
    protected float fromsy;

    //private static java.util.List<NeuronBox> nodeBoxes = new java.util.ArrayList<NeuronBox>();
    //private static java.util.List<Integer> nodeIds = new java.util.ArrayList<Integer>();
	private static java.util.ArrayList<BlockState> savedStates = new java.util.ArrayList<BlockState>();

    private android.view.SurfaceHolder surfaceHolder;
    private android.view.ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

	private static boolean inited;
    private boolean running = true;
    private android.content.Context context;
    protected NeuronBox selNb = null;

	/*
    public static List<Integer> getNodeIds()
	{
        return nodeIds;
    }
	*/

    public void setScaleFactor(float scaleFactor)
	{
        this.scaleFactor = scaleFactor;
    }

    public float getScaleFactor()
	{
        return scaleFactor;
    }

    public NorbironSurfaceView(android.content.Context context)
	{
        super(context);
        cinit(context);
    }

    public NorbironSurfaceView(android.content.Context context, android.util.AttributeSet attrs)
	{
        super(context, attrs);
        cinit(context);
    }

    public NorbironSurfaceView(android.content.Context context, android.util.AttributeSet attrs, int defStyle)
	{
        super(context, attrs, defStyle);
        cinit(context);
    }


    @Override
    protected void onSizeChanged(int newx, int newy, int x, int y)
	{

        super.onSizeChanged(newx, newy, x, y);

        width = newx;
        height = newy;
        swidth = width / 2 - SLOT_SIZE / 2;
        sheight = height / 2 - SLOT_SIZE / 2;

    }

    public void initMenuNodes()
	{
        if (!inited)
		{
			norbironMap.addMenu(0,4,3);
			norbironMap.addMenu(1,5,3);
			inited = true;
        }
    }
	

    private void cinit(android.content.Context context)
	{

        this.context = context;
		norbironMap.setSurfaceView(this);
		norbironMap.restoreStates(savedStates);
        initMenuNodes();

        android.content.Intent intent = ((NeuronGameActivity) context).getIntent();
        android.os.Bundle bundle = intent.getExtras();

        if (bundle != null)
		{
            int i = bundle.getInt("selectedNode");
			norbironMap.addProc(i, 0,3);
        }

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceEvents(this));
        scaleGestureDetector = new android.view.ScaleGestureDetector(context, new ScaleAdapter(this));

    }

    @Override
    public void onDraw(android.graphics.Canvas canvas)
	{

        if (surfaceHolder.getSurface().isValid())
		{
			norbironMap.draw(canvas, scaleFactor, startsx, startsy);
        }
    }

    public void repaint()
	{

        android.graphics.Canvas canvas = null;
        try
		{
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null)
			{
                onDraw(canvas);
            }
        }
		
		finally
		{
            if (canvas != null)
			{
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void newNode()
	{

        android.content.Intent intent = new android.content.Intent(context, NodeActivity.class);
		savedStates = norbironMap.saveStates();
        intent.putIntegerArrayListExtra("nodeIds", norbironMap.getProcResIDs());
        context.startActivity(intent);

    }

    public void newBox()
	{
        //nodeBoxes.clear();
        initMenuNodes();
        android.widget.Toast.makeText(context, "Table cleared", android.widget.Toast.LENGTH_SHORT).show();

    }

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


    public void stop()
	{
        running = false;
    }

    @Override
    public void run()
	{
	    long now = System.currentTimeMillis(), newnow;
        running = true;
        while (running)
		{
            if ((newnow = System.currentTimeMillis()) - now > 100)
			{
				norbironMap.stepNeurons();
                repaint();
                now = newnow;
            }
        }
    }

}
