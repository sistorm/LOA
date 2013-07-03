package main;

import java.io.IOException;

import javax.swing.plaf.SliderUI;

public class Messages {

	
	public final static int White = 4;
	public final static int Black = 2;
	
	
	private Grid mGrid;
	
	public void playWith(String pions, int color){
		mGrid = new Grid(pions, Grid.TYPE_DECODE_SERVER,color);
		
		// commence les calcule de grid
		
		
	}
	
	
	public String getCoup()
	{
		Thread th = new Thread(mGrid);
		th.start();
		
		
			  //do what you want to do before sleeping
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//sleep for 1000 ms
			  //do what you want to do after sleeptig
		
		
		//System.out.println(mGrid.getBestMove());
		return mGrid.getBestMove();
		
		
	}
	
	
	public void setCoupAdversaire(String moveString)
	{
		//D6 - D5
		long move =0;
		move = 1L << ('H'-(moveString.charAt(1))+(moveString.charAt(2)-'1')*8);		
		move |= 1L << (('H'-moveString.charAt(6))+(moveString.charAt(7)-'1')*8);

		//mGrid.printBits(move);
		
		
		mGrid.coupAdvAndUpdate(move);
		
	}
	
	/*public void printlnLOAs(){
		mGrid.pringLOAs();
	}*/
	
}
