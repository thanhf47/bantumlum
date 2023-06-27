
package manager;

import java.io.IOException;
import java.util.ArrayList;

import elements.EnermyShip;
import elements.Entity;
import elements.ExtraBullet;
import elements.HpMore;
import elements.SpaceShip;
import elements.Stone;
//import elements.ThuyenTim;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lib.Point;
import lib.Timer;
import view.controller.GamePlayController;


import java.util.ArrayList;

import elements.Entity;
import elements.ExtraBullet;
import elements.ExtraUltimate;
import elements.UpgradeBullet;
import elements.HpMore;
import elements.SpaceShip;
import elements.Stone;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lib.Point;
import lib.Timer;
import view.controller.GamePlayController;

public class Event {
	private AnchorPane gamePane;
	private SpaceShip spaceShip;
	private Timer timer = new Timer();
//	private long lastTime = 0;
	private long deltaTime = 0;
	private GamePlayController controller;
	private Text bullet;
	private Text score;
	private ProgressBar hpBar;
	private ArrayList<Entity> E = new ArrayList<Entity>();
	
	
	public Event(GamePlayController controller) {
		hpBar = controller.getHpBar();
		this.controller = controller;
		gamePane = controller.getGamePane();
		spaceShip = controller.getSpaceShip();
		bullet = controller.getCountBullet();
		score = controller.getScore();
	}
	
	public void timeLine(long now) {
		long t = timer.getT();
		timer.setT(now);
		
		if(t != timer.getT()) {
			if(timer.getT()%10==0){
				increase(10);
				
			}
//			ThuyenTim tim = new ThuyenTim();
//			E.add(tim);
//			tim.move(spaceShip, gamePane);
			nemDaDauTay();	
			themDich();
			deltaTime ++;
			if (deltaTime%4 == 2) bonusThemDan();
			if (deltaTime%5 == 3) themHP();
			if (deltaTime%5 == 4) upgradeShoot();
			if (deltaTime%7 == 3) addUltimate();
//			lastTime = now;
		}
		
	}
	public void handleKey(boolean isLeftKeyPressed, boolean isRightKeyPressed, boolean isSpaceKeyPressed, boolean isRKeyPressed, boolean isFKeyPressed) {
		//chuyen 3 cai nay ra cho khac.
//		hpBar.setProgress(spaceShip.getHP()/10.0);
//		bullet.setText("Bullets: "+String.valueOf(spaceShip.getBulletStore()));
//		score.setText("Score: "+ spaceShip.getScore());
		if(spaceShip.canDiChuyen)spaceShip.spaceShipMove(isLeftKeyPressed, isRightKeyPressed);
		if ((isSpaceKeyPressed && spaceShip.canShoot && spaceShip.getBulletStore() >= spaceShip.getCachBan())) {
			
			spaceShip.spaceShipAttack1(gamePane,E, isSpaceKeyPressed);
			
			spaceShip.canShoot = false;
			
			PauseTransition shootDelay = new PauseTransition(Duration.seconds(0.2)); 
			shootDelay.setOnFinished(event->{
				spaceShip.canShoot = true;
			});	
			shootDelay.play();
		}
		if (isRKeyPressed && spaceShip.canUlti) {
			
			spaceShip.spaceShipAttack2(gamePane, E, isRKeyPressed);
			spaceShip.canUlti = false;
			PauseTransition shootDelay = new PauseTransition(Duration.seconds(1.0));
			shootDelay.setOnFinished(event->{
				spaceShip.canUlti = true;
			});
			shootDelay.play();
			
		}
		if (isFKeyPressed && spaceShip.getCachBan() > 1 && spaceShip.canChange) {
		
			spaceShip.setCachBan(spaceShip.getCachBan() - 1);
			spaceShip.canChange = false;
			PauseTransition changeDelay = new PauseTransition(Duration.seconds(1.0));
			changeDelay.setOnFinished(event->{
				spaceShip.canChange = true;
			});
			changeDelay.play();
			
		}
	}
	public void themDich() {
		
		EnermyShip ship = new EnermyShip();
		E.add(ship);
		ship.move(spaceShip, gamePane);
	}
	
	public void increase(int bullets) {
		if (spaceShip.getBulletStore() <= 90) spaceShip.setBulletStore(spaceShip.getBulletStore()+10);
	
          bullet.setText("Bullets: "+String.valueOf(spaceShip.getBulletStore()));
	}
	public void upgradeShoot() {
		UpgradeBullet UpgradeBullet = new UpgradeBullet();
		UpgradeBullet.move(spaceShip, gamePane);
	}
	public void themHP() {
		HpMore hp = new HpMore();
		
		hp.move(spaceShip, gamePane);
	}
	public void addUltimate() {
		ExtraUltimate ulti = new ExtraUltimate();
		ulti.move(spaceShip, gamePane);
	}
	public void bonusThemDan() {
		ExtraBullet extraBullet = new ExtraBullet();
		extraBullet.move(spaceShip, gamePane);
	}
	public void nemDaDauTay() {
		Stone stone = new Stone();
		
		AnimationTimer timer = new AnimationTimer() {
			long lastTime = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
			if(now - lastTime>1e9/10) {
				 if(stone.getCenter().getY()<spaceShip.getCenter().getY()-200) {
					Point position = spaceShip.getCenter();
					
					Point vector = new Point();
					vector.setLocation(stone.getCenter());
				
					vector.mul(-1);
			
					vector.add(position);
			
					vector.mul(3.5/vector.doDai());
			
					stone.setVector(vector);
					
				}
				
			}
			}
			
		};timer.start();
		E.add(stone);
		stone.move(spaceShip, gamePane);
	}
//	public void createBossText() {
//		Text bossText = new Text("BOSS");
//		bossText.setFont(Font.font("Arial", FontWeight.BOLD, 96));
//		bossText.setFill(Color.RED);
//		
//        double bossTextX = gamePane.getWidth()/ 2 - bossText.getBoundsInLocal().getWidth() / 2;
//        double bossTextY = gamePane.getHeight() / 2 - bossText.getBoundsInLocal().getHeight() / 2;
//        bossText.setLayoutX(bossTextX);
//        bossText.setLayoutY(bossTextY);
//
//        gamePane.getChildren().add(bossText);
//	}
	public void gameOver() throws IOException {
			Stage stage = new Stage();
			new GameOver(stage);
			spaceShip.setHP(-1);
		
	}

}