package GamblingPackage;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player implements Comparable<Player>{

	int money = 1000;
	static int bettingPrice = 0;
	static int raiseBettingPrice = 0;
	int currentBettingPrice = 0;
	int playerNum;
	int prevRaise = 0;
	int usedMoney = 0;
	
	public Player(int num) {
		this.playerNum = num;
	}
	
	Panel player = new Panel();
	Panel nameMoney = new Panel();
	Label Playermoney = new Label("money : " + this.money);
	TextField name = new TextField("name");
	Panel buttons = new Panel();
	Button check = new Button("Check");
	Button call = new Button("Call");
	Button raise = new Button("Raise");
	Button raiseReset = new Button("Raise Reset");
	Panel upDown = new Panel();
	Button up = new Button("+");
	Button down = new Button("-");
	Button doubleUp = new Button("++");
	Button doubleDown = new Button("--");
	Button die = new Button("Die");
	Label willPay = new Label("" + (Player.this.currentBettingPrice - Player.bettingPrice));
	Label willPay2 = new Label("" + (Player.this.currentBettingPrice - Player.bettingPrice));
	Label usedMoneyLabel = new Label("" + usedMoney);
	Button winnerButton = new Button("Winner");
	
	boolean alive = true;
	boolean king = false;
	boolean myturn = false;
	boolean lastRaise = false;
	
	public void playerRewind() {
		if(!myturn) {
			check.setEnabled(false);
			call.setEnabled(false);
			raise.setEnabled(false);
			die.setEnabled(false);
			doubleUp.setEnabled(false);
			up.setEnabled(false);
			down.setEnabled(false);
			doubleDown.setEnabled(false);
			raiseReset.setEnabled(false);
		}
		
		if(myturn && alive) {
			call.setEnabled(true);
			die.setEnabled(true);
			doubleUp.setEnabled(true);
			up.setEnabled(true);
			down.setEnabled(true);
			doubleDown.setEnabled(true);
			raiseReset.setEnabled(true);
			if(this.king) {
				if(bettingPrice == 0) {
					call.setEnabled(false);
					check.setEnabled(true);
				}
				
					
			}
		}
		
		if(BettingEx.turn == 5) {
			if(Player.this.alive) {
				winnerButton.setEnabled(true);
				check.setEnabled(false);
				call.setEnabled(false);
				raise.setEnabled(false);
				die.setEnabled(false);
				doubleUp.setEnabled(false);
				up.setEnabled(false);
				down.setEnabled(false);
				doubleDown.setEnabled(false);
				raiseReset.setEnabled(false);
			}	
		}
		else {
			winnerButton.setEnabled(false);
		}
		
		willPay.setText("" + prevRaise);
		willPay2.setText("" + (Player.this.currentBettingPrice - Player.bettingPrice));
		Playermoney.setText("money : " + this.money);
		
		if(!alive) {
			Player.this.willPay.setText("die");
			Player.this.willPay2.setText("die");
		}
		BettingEx.rewordLabel.setText("Reword : " + BettingEx.reword);
		BettingEx.turnStatus.setText("Turn : " + BettingEx.turn);
		usedMoneyLabel.setText("" + usedMoney);
	}
	
	public void PlayerRewind2() {
		willPay2.setText("" + (Player.this.currentBettingPrice - Player.bettingPrice - Player.raiseBettingPrice) + "(" + Player.raiseBettingPrice + ")");
		if(Player.raiseBettingPrice > 0) {
			Player.this.raise.setEnabled(true);
		}
		else {
			Player.this.raise.setEnabled(false);
		}
	}
	
	
	
	public void PlayerSet() {

		player.setLayout(new BorderLayout());
		player.setBackground(Color.magenta);
		name.selectAll();
		nameMoney.add(name);
		nameMoney.add(Playermoney);
		player.add(nameMoney, "North");
		buttons.setLayout(new GridLayout(6,2));
		buttons.add(check);
		buttons.add(new Label("" + 0));
		buttons.add(call);
		buttons.add(willPay);
		buttons.add(raise);
		buttons.add(willPay2);
		upDown.add(doubleDown);
		upDown.add(down);
		upDown.add(up);
		upDown.add(doubleUp);
		buttons.add(upDown);
		buttons.add(raiseReset);
		buttons.add(die);
		buttons.add(usedMoneyLabel);
		player.add(buttons, "Center");
		buttons.add(winnerButton);
		
		check.setEnabled(false);
		call.setEnabled(false);
		raise.setEnabled(false);
		die.setEnabled(false);
		doubleUp.setEnabled(false);
		up.setEnabled(false);
		down.setEnabled(false);
		doubleDown.setEnabled(false);
		raiseReset.setEnabled(false);
		winnerButton.setEnabled(false);

		if(king) {
			lastRaise = true;
			myturn = true;
		}
		
		
		if(myturn && alive) {
			call.setEnabled(true);
			raise.setEnabled(true);
			die.setEnabled(true);
			doubleUp.setEnabled(true);
			up.setEnabled(true);
			down.setEnabled(true);
			doubleDown.setEnabled(true);
			raiseReset.setEnabled(true);
			if(this.king) {
				check.setEnabled(true);
			}
		}
		
		this.check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player.this.myturn = false;
				Player.this.lastRaise = true;
				Player.raiseBettingPrice = 0;
				Player.this.playerRewind();
				Player nextPlayer = Player.this.NextPlayer();
				while(true) {
					if(nextPlayer.alive) {
						nextPlayer.myturn = true;
						nextPlayer.playerRewind();
						break;
					}
					nextPlayer = nextPlayer.NextPlayer();
				}
			}
		});
		
		this.call.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player.this.myturn = false;
				Player.this.money += Player.this.prevRaise;
				BettingEx.reword -= Player.this.prevRaise;
				Player.this.currentBettingPrice = Player.bettingPrice;
				Player.this.usedMoney -= Player.this.prevRaise;
				Player.raiseBettingPrice = 0;
				Player.this.prevRaise = Player.this.currentBettingPrice - Player.bettingPrice;
				Player.this.playerRewind();
				Player nextPlayer = Player.this.NextPlayer();
				while(true) {
					if(nextPlayer.alive) {
						if(nextPlayer.lastRaise) {
							BettingEx.turnStatus.setText("Turn : " + ++BettingEx.turn);
							Player.bettingPrice = 0;
							Player.raiseBettingPrice = 0;
							currentBettingPriceResetAll();
							nextPlayer = searchKing();
						}
						nextPlayer.myturn = true;
						nextPlayer.playerRewind();
						break;
					}
					nextPlayer = nextPlayer.NextPlayer();
				}
				if(BettingEx.turn == 5) {
					UpdateAllPlayer();
				}
				System.out.println("betting price : " + Player.bettingPrice);
			}
			
		});
		
		this.raise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myturn = false;
				LastRaiseSet();
				Player.bettingPrice += Player.raiseBettingPrice;
				Player.raiseBettingPrice = 0;
				Player.this.money -= (Player.bettingPrice - Player.this.currentBettingPrice);
				BettingEx.reword += (Player.bettingPrice - Player.this.currentBettingPrice);
				Player.this.usedMoney += (Player.bettingPrice - Player.this.currentBettingPrice);
				Player.this.currentBettingPrice = Player.bettingPrice;
				
				Player.this.playerRewind();
				Player nextPlayer = Player.this.NextPlayer();
				while(true) {
					if(nextPlayer.alive) {
						nextPlayer.myturn = true;
						nextPlayer.playerRewind();
						break;
					}
					nextPlayer = nextPlayer.NextPlayer();
				}
				UpdateAllPlayer();
				System.out.println("betting price : " + Player.bettingPrice);
			}
		});
		
		this.die.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myturn = false;
				alive = false;
				Player.this.currentBettingPrice = 0;
				Player.this.playerRewind();

				
				Player nextPlayer = Player.this.NextPlayer();
				while(true) {
					if(nextPlayer.alive) {
						if(Player.this.king) {
							Player.this.king = false;
							nextPlayer.king = true;
						}
						if(nextPlayer.lastRaise && (nextPlayer.prevRaise==0)) {
							BettingEx.turnStatus.setText("Turn : " + ++BettingEx.turn);
							Player.bettingPrice = 0;
							Player.raiseBettingPrice = 0;
							currentBettingPriceResetAll();
							nextPlayer = searchKing();
						}
						nextPlayer.myturn = true;
						nextPlayer.playerRewind();
						break;
					}
					nextPlayer = nextPlayer.NextPlayer();
				}
				if(BettingEx.turn == 5) {
					UpdateAllPlayer();
				}
				
				BettingEx.liveCount--;
				System.out.println("live count " + BettingEx.liveCount);
				if(BettingEx.liveCount == 1) {
					searchKing().money += BettingEx.reword;
					BettingEx.turn = 1;		
					BettingEx.ShowResult();
					Player.this.AllAlive();
					Player.this.NewSet();
				}
			}
		});
		
		this.doubleUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player.raiseBettingPrice += 5;
				Player.this.PlayerRewind2();
				
			}
		});
		
		this.up.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Player.raiseBettingPrice++;
				Player.this.PlayerRewind2();

			}
		});
		
		this.down.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Player.raiseBettingPrice>=1) {
					Player.raiseBettingPrice--;
					Player.this.PlayerRewind2();
				}
			}
		});
		
		this.doubleDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Player.raiseBettingPrice>=5) {
					Player.raiseBettingPrice -= 5;
					Player.this.PlayerRewind2();
				}
				else {
					Player.raiseBettingPrice = 0;
					Player.this.PlayerRewind2();
				}
			}
		});
		
		this.raiseReset.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Player.raiseBettingPrice = 0;
				Player.this.PlayerRewind2();
			}
		});
		
		this.winnerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player.this.money += BettingEx.reword;
				BettingEx.turn = 1;
				Player.this.KingSetAndAllAlive();				
				BettingEx.ShowResult();
				Player.this.NewSet();
				
			}
		});
	}
	
	public void NewSet() {
		Player.bettingPrice = 0;
		Player.raiseBettingPrice = 0;
		BettingEx.liveCount = BettingEx.playerCount;
		for(Player player : BettingEx.players) {
			player.currentBettingPrice = 0;
			player.prevRaise = 0;
			//player.usedMoney = 0;
			player.playerRewind();
		}
	}
	
	public void currentBettingPriceResetAll() {
		for(Player player : BettingEx.players) {
			player.currentBettingPrice = 0;
		}
	}
	
	public Player NextPlayer() {
		if(this.playerNum+1 == BettingEx.playerCount) {
			return BettingEx.players.get(0);
		}
		return BettingEx.players.get(this.playerNum+1);
	}
	
	public void UpdateAllPlayer() {
		for(Player player : BettingEx.players) {
			if(player.alive) {
				player.prevRaise = player.currentBettingPrice - Player.bettingPrice;
				player.playerRewind();
			}
		}
	}
	
	public void LastRaiseSet() {
		for(Player player : BettingEx.players) {
			if(player == this) {
				player.lastRaise = true;
			}
			else {
				player.lastRaise = false;
			}
		}
	}
	
	public void AllAlive() {
		for(Player player : BettingEx.players) {
			player.alive = true;
			if(!player.king) {
				player.lastRaise = false;
			}
		}
	}
	
	public void KingSetAndAllAlive() {
		for(Player player : BettingEx.players) {
			player.alive = true;
			if(player == this) {
				player.king = true;
				player.myturn = true;
			}
			else {
				player.king = false;
				player.myturn = false;
				player.lastRaise = false;
			}
		}
	}
	
	public Player searchKing() {
		for(Player player : BettingEx.players) {
			if(player.king) {
				return player;
			}
		}
		return null;
	}

	@Override
	public int compareTo(Player o) {
		return o.money- this.money;
	}
	
	


	

		
}
