package GamblingPackage;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class BettingEx {

	static Frame frame = new Frame("Betting");
	static Dialog result = new Dialog(frame, "Result", true);
	static int playerCount = 0;
	static int liveCount = playerCount;
	static int turn = 1;
	static ArrayList<Player> players;
	static int reword = 0;
	static Label rewordLabel = new Label("Reword : " + reword);
	static Label turnStatus = new Label("Turn : " + turn);
	Panel playerCountAndTurn = new Panel();
	static Label[] nameArr;
	static Label[] scoreArr;
	
	
	
	public void BettingExShow() {
		
		frame.setSize(900,900);
		result.setSize(300,300);
		
		CardLayout card = new CardLayout();
		Panel card1 = new Panel();
		card1.setLayout(new BorderLayout());
		card1.setBackground(Color.LIGHT_GRAY);
		Panel getPlayerCount = new Panel();
		
		Panel card2 = new Panel();
		card2.setLayout(new BorderLayout());
		Panel PlayerStatusAll = new Panel();
		PlayerStatusAll.setBackground(Color.blue);


		card2.add(rewordLabel, "South");

		card1.add(new Label("player count"),"Center");
		getPlayerCount.setLayout(new BorderLayout());
		TextField playerCountField = new TextField("PlayerCount");
		playerCountField.selectAll();
		getPlayerCount.add(playerCountField, "Center");

		Panel slide = new Panel();
		slide.setLayout(card);
		slide.add(card1, "1");
		slide.add(card2, "2");
		
		frame.add(slide);
		result.add(new Label("Result"), "North");
		Panel rank = new Panel();
		
		
		
		Button ok = new Button("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerCount = Integer.parseInt(playerCountField.getText().trim());
				liveCount = playerCount;

				playerCountAndTurn.add(new Label("Player Count : " + playerCount));
				playerCountAndTurn.add(turnStatus);
				card2.add(playerCountAndTurn, "North");


				Player firstPlayer = null;
				
				players = new ArrayList<Player>(playerCount+1);
				for(int i=0; i<playerCount; i++) {

					players.add(new Player(i));
					players.get(i).PlayerSet();
					PlayerStatusAll.add(players.get(i).player);
					
					if(i == 0) {
						firstPlayer = players.get(i);
						firstPlayer.king = true;
						firstPlayer.myturn = true;
						firstPlayer.playerRewind();
					}
				}
				
				card2.add(PlayerStatusAll, "Center");
				card.next(slide);
				firstPlayer.name.requestFocus();
				
				rank.setLayout(new GridLayout(BettingEx.playerCount, 2));
				nameArr = new Label[playerCount];
				scoreArr = new Label[playerCount];
				int i = 0;
				for(Player player : BettingEx.players) {
					nameArr[i] = new Label(player.name.getText());
					rank.add(nameArr[i]);
					if(player.king) {
						scoreArr[i] = new Label(player.money + "(+" + (BettingEx.reword-player.usedMoney) + ")");
						rank.add(scoreArr[i++]);
					}
					else {
						scoreArr[i] = new Label(player.money + "(-" + player.usedMoney + ")");
						rank.add(scoreArr[i++]);
					}
				}
				result.add(rank, "Center");
			}
		});
		
		
		
		getPlayerCount.add(ok, "East");
		card1.add(getPlayerCount, "South");
		
		result.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				for(Player player : BettingEx.players) {
					player.playerRewind();
				}
				result.setVisible(false);
			}
		});	
		
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
	}
	
	static public void ShowResult() {
		result.add(new Label("Result"), "North");
		int i = 0;
		ArrayList<Player> temp = (ArrayList<Player>)BettingEx.players.clone();
		temp.sort(null);
		
		for(Player player : temp) {
			nameArr[i].setText("Player Name : " + player.name.getText());
			if(player.king) {
				scoreArr[i++].setText(player.money + "(+" + (BettingEx.reword-player.usedMoney) + ")");
			}
			else {
				scoreArr[i++].setText(player.money + "(-" + player.usedMoney + ")");
			}
			player.usedMoney = 0;
		}
		BettingEx.reword = 0;
		

		result.setVisible(true);
	}
}
