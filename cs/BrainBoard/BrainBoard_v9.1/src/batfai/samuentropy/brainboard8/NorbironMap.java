/*
 * NorbironMap.java
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


import java.util.List;

public class NorbironMap
{
	private	int n = 13;
	private int m = 10;
	private int[][] map;
	private int BLOCK_SIZE = 120;
	private NorbironSurfaceView surfaceView;
	private NorbironResources norbironResources;
	private java.util.ArrayList<BlockState> blockStates;
	private NeuronBox selectedNode = null;
	private java.util.ArrayList<NeuronBox> neuronBoxes;

	public NorbironMap(NorbironSurfaceView surfaceView)
	{
		this.surfaceView = surfaceView;
		initMap();
		norbironResources = new NorbironResources(BLOCK_SIZE, this.surfaceView);
		blockStates = new java.util.ArrayList<BlockState>();
		neuronBoxes = new java.util.ArrayList<NeuronBox>();
	}

	public void initMap()
	{
		// 0 - simple
		// 1 - red
		// 2 - board

		this.map = new int[][]
		{
		  { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		  { 1, 1, 1, 2, -1, -1, -1, 1, 1, 1 },
		  { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		  { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },
		  { 0, 0, 0, 1, 1, 1, 1, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
		};
	}

	public void draw(android.graphics.Canvas canvas, float scaleFactor, float startsx, float startsy)
	{
		canvas.save();
		canvas.scale(scaleFactor, scaleFactor);
		canvas.drawColor(android.graphics.Color.BLACK);

		for(int i = 0; i<m; i++)
		{
			for(int j = 0; j<n; j++)
			{
				if(map[j][i] == -1) continue;
				canvas.drawBitmap(norbironResources.getImage(this.map[j][i]), -startsx + i * BLOCK_SIZE, -startsy + j * BLOCK_SIZE, null);
			}		
		}
	
		for(int i = 0; i<blockStates.size(); i++)
		{
			blockStates.get(i).getNeuronBox().draw(-startsx, -startsy, canvas);	
		}

		canvas.restore();

	}

	public void addMenu(int id, int x, int y)
	{
		BlockState blockState = new BlockState(0, x, y, (NeuronBox)norbironResources.getMenu(id).clone() );
		blockState.getNeuronBox().setXY(x,y);
		blockState.getNeuronBox().setType(id);
		blockStates.add(blockState);
	}

	public void addProc(int id, int x, int y)
	{
		BlockState blockState = new BlockState(1, x, y, (NeuronBox)norbironResources.getProc(id).clone() );
		blockState.getNeuronBox().setXY(x,y);
		blockStates.add(blockState);
	}

	public void stepNeurons()
	{
		for(int i = 0; i<blockStates.size(); i++)
		{
			blockStates.get(i).getNeuronBox().step();
		}
	}

	public NeuronBox getNearestNeuron(float x, float y)
	{
		NeuronBox tmp = null;
        float max = 10000, m;

        for (int i = 0; i<blockStates.size(); i++)
		{

            if ((m = distance(blockStates.get(i).getNeuronBox().getX() + blockStates.get(i).getNeuronBox().getWidth() / 2, 
					blockStates.get(i).getNeuronBox().getY() + blockStates.get(i).getNeuronBox().getHeight() / 2,(int)x,(int)y)) < max)
			{
                max = m;
                tmp = blockStates.get(i).getNeuronBox();
            }
        }

        return tmp;

	}

	public float distance(int x1, int y1, int x2, int y2)
	{
		return (float)( (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) );
	}
	
	public void setSurfaceView(NorbironSurfaceView surfaceView)
	{
		this.surfaceView= surfaceView;
		this.norbironResources = new NorbironResources(BLOCK_SIZE, this.surfaceView);
	}

	public java.util.ArrayList<BlockState> saveStates()
	{
		return blockStates;
	}

	public void restoreStates(java.util.ArrayList<BlockState> states)
	{
		this.blockStates = states;
	}
	
	public java.util.ArrayList<Integer> getProcResIDs()
	{
		return this.norbironResources.getProcResIDs();
	}

}

