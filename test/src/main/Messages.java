package main;

public class Messages {

	public final static int White = 4;
	public final static int Black = 2;

	private Grid mGrid;
	private Client mClient;

	public Messages() {
		mClient = new Client(this);

		mClient.start();
	}

	public void playWith(String pions, int color) {
		mGrid = new Grid(pions, Grid.TYPE_DECODE_SERVER, color);

	}

	long start;

	public void getCoup() {
		// Thread th = new Thread(mGrid);
		// th.start();

		Thread r = new Rototo(this);
		r.start();

		// do what you want to do before sleeping
		// try {
		// Thread.currentThread().sleep(200);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }//sleep for 1000 ms
		// do what you want to do after sleeptig

		// System.out.println(mGrid.getBestMove());
		// return mGrid.getBestMove();

		start = System.nanoTime();

	}

	public void setCoup(String coup) {

		mClient.envoieCoup(coup);

		long end = System.nanoTime();
		float time = end - start;

		System.out.println("time s= " + time / 1000000000 + " mls=" + time
				+ 1000000 + " mcs=" + time / 1000 + " ns=" + time);

	}

	public void setCoupAdversaire(String moveString) {
		// D6 - D5
		long move = 0;
		move = 1L << ('H' - (moveString.charAt(1)) + (moveString.charAt(2) - '1') * 8);
		move |= 1L << (('H' - moveString.charAt(6)) + (moveString.charAt(7) - '1') * 8);

		// mGrid.printBits(move);

		mGrid.coupAdvAndUpdate(move);

	}

	/*
	 * public void printlnLOAs(){ mGrid.pringLOAs(); }
	 */
	private class Rototo extends Thread {

		Messages msg;

		private Rototo(Messages msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			// mGrid.getBestMove(3);
			msg.setCoup(mGrid.getBestMove(4));
			System.gc();
		}
	}
}
