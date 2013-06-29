package main;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Grid {

	private static Map<Integer,Long> MASK;
	
	private static Map<Byte,Integer> MASK_NB_BITS;
	
	private long mPions = 0;
	private long mPionsAdv = 0;
	
	byte[][] mOrth = new byte[2][8];
	byte[][] mVert = new byte[2][15];
	
	
	public Grid(String str){
		int offset = 0;
		for (char c : str.toCharArray()) {
			if(c != '0')
				mPions |= (long)1<<63-offset;
			++offset;
		}
		MASK = new HashMap<Integer, Long>();
		for(int i = 0; i < 64; ++i){
			if(i%8 == 0){ //bord de droite
				MASK.put(i, 1L<<i+1 | 3L <<i+7 | ((i-8 >= 0)? 3L << i-8: 0));
			}else if((i+1)%8 == 0){// bord de gauche
				MASK.put(i, (i+7<64?3L <<i+7:0) | 1L<<i-1 | 3L<< i-9);
			}else{//general
				MASK.put(i, 1L<<i+1 | (i+7<64? 7L<<i+7:0L) | 1L<<i-1 | (i-9>=0?7L<< i-9:0));
			}
				
		}
			
		//MASK.put(64, (long)1<<63 | 1 << 1);
		
		MASK_NB_BITS = new HashMap<Byte, Integer>(256);
		for (int i = 0; i < 256; ++i) 
		{
			MASK_NB_BITS.put((byte) i, Integer.bitCount(i));
			System.out.println(Integer.toBinaryString(i)+" : " + MASK_NB_BITS.get((byte)i) );
		}
	}
	
	
	public boolean isConnected(){
		return isConnected(mPions);
	}
	
	private boolean isConnected(long pions){
		
		if(pions != 0){
			int i = 63 - Long.numberOfLeadingZeros(pions);
			
			
			long nexts = 1L<<i;
			while(nexts !=0 ){
				
				long comboMask = 0;

				pions ^= nexts;
				while (nexts != 0) {
					int j = 63 - Long.numberOfLeadingZeros(nexts);
					comboMask |= MASK.get(j);
					nexts ^= 1L << j;
				}
				
				
				nexts = pions & comboMask;
				
			}
			
		}
		
		
		
		
		return pions == 0;
	}
	
	public void printBits(){
		System.out.println(Long.toBinaryString(mPions));
		
	}

	/**
	 * 
	 * @param pions
	 * @return
	 * 	the List of possible move
	 */
	public List<Long> generatePossibleMvt(long pions)
	{
		List<Long> possMvt = new ArrayList<>();
		
		//parcour chaque bits (boucle de 64)
		//+ condition si 1L<<i et pions != 0

				
		for (int i = 63; i >=0 ; --i) {
			
			long maskPion = 1L << i;
			
			if((maskPion & pions)  != 0)
			{
				
				
				
				possMvt.add(maskPion);
			}
		}
		
		return possMvt;
		
	}
	
	public long getmPions() {
		return mPions;
	}
	
	public void init()
	{
		
		long completeGrid = mPions | mPionsAdv;
		System.out.println(Long.toBinaryString(completeGrid));
		
		for (int i = 7; i >=0; --i) {
			System.out.println(Long.toBinaryString((long)1<<63 |((completeGrid & (255L << i*8))>>>i*8)));
		}
	}
	
	
	public void update(long move)
	{
		
	}
	
}
