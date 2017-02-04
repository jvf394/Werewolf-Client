package fedor.games.werewolf;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


class WerewolfServer implements KeyListener {

    private final static int PORT_NUMBER = 1234;
    private static int totalPlayers;
    private static ServerSocket serverSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static WerewolfProtocol wwp;


	public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            wwp = new WerewolfProtocol();


            //Accepts host and stores username, #players
            System.out.println("Waiting For Players");
            acceptMainPlayer();
            System.out.println("finished accepting first player");

            //Accepts the rest of the players
            for (int i = 2; i < totalPlayers+1; i++) {
                acceptPlayers(i);
            }
            System.out.println("finished accepting all players");

            //Brings up character selection for host, and waiting screen for other players
            wwp.characterSelect();

            //Receives shuffled deck
			String deck = wwp.listenPlayer(3);

            //Deals cards to players
            wwp.dealCards(deck);
            wwp.showCard(); //sends players their card and name
            wwp.beginGame(); //begins game

            System.out.println("WE DID IT");
            //System.out.println(in.readLine() + "checkpoint 2");


        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + PORT_NUMBER + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private static void acceptMainPlayer() {
        try {
            Socket clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("1");
            wwp.playerList(in.readLine(), clientSocket, 1);
            totalPlayers = wwp.getNumPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void acceptPlayers(int playerNumber) {
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("player accepted");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println(playerNumber);
            wwp.playerList(in.readLine(), clientSocket, playerNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.exit(-1);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}