package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

class Client extends Thread {

	// private final Callback mCallback;

	private final Messages mMessages;

	private Socket MyClient;
	private BufferedInputStream input;
	private BufferedOutputStream output;

	public Client(Messages mess) {
		mMessages = mess;

		try {
			// MyClient = new Socket("localhost", 8888);
			MyClient = new Socket("10.196.113.15", 8888);
			input = new BufferedInputStream(MyClient.getInputStream());
			output = new BufferedOutputStream(MyClient.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		// int[][] board = new int[8][8];
		try {

			// BufferedReader console = new BufferedReader(new
			// InputStreamReader(
			// System.in));
			while (true) {
				char cmd = 0;

				cmd = (char) input.read();

				// D�but de la partie en joueur blanc
				if (cmd == '1') {
					byte[] aBuffer = new byte[1024];

					int size = input.available();
					// System.out.println("size " + size);
					input.read(aBuffer, 0, size);
					String s = new String(aBuffer).trim();
					String boardValues;
					boardValues = s.replace(" ", "");

					mMessages.playWith(boardValues, true);

					// System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : ");

					mMessages.getCoup(); // null;
					// move = console.readLine();
					// output.write(move.getBytes(),0,move.length());
					// output.flush();

				}
				// D�but de la partie en joueur Noir
				if (cmd == '2') {
					System.out
							.println("Nouvelle partie! Vous jouer noir, attendez le coup des blancs");
					byte[] aBuffer = new byte[1024];

					int size = input.available();
					// System.out.println("size " + size);
					input.read(aBuffer, 0, size);
					String s = new String(aBuffer).trim();

					String boardValues = s.replace(" ", "");

					mMessages.playWith(boardValues, false);

					// String[] boardValues;
					// boardValues = s.split(" ");
					// int x=0,y=0;
					// for(int i=0; i<boardValues.length;i++){
					// board[x][y] = Integer.parseInt(boardValues[i]);
					// x++;
					// if(x == 8){
					// x = 0;
					// y++;
					// }
					// }
				}

				// Le serveur demande le prochain coup
				// Le message contient aussi le dernier coup jou�.
				if (cmd == '3') {
					byte[] aBuffer = new byte[16];

					int size = input.available();
					// System.out.println("size " + size);
					input.read(aBuffer, 0, size);

					String s = new String(aBuffer);
					// System.out.println("Dernier coup : " + s);
					mMessages.setCoupAdversaire(s);

					// System.out.println("Entrez votre coup : ");
					mMessages.getCoup();
					// move = console.readLine();
					// output.write(move.getBytes(),0,move.length());
					// output.flush();

				}
				// Le dernier coup est invalide
				if (cmd == '4') {
					// System.out.println("Coup invalide, entrez un nouveau coup : ");
					// String move = null;
					// mMessages.printlnLOAs();
					mMessages.getCoup();

				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	public void envoieCoup(String coup) {
		try {
			output.write(coup.getBytes(), 0, coup.length());
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
