package world;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Timer;

import managers.GameStateManager;
import managers.ScreenManager;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import character.ActorObject;
import character.AmmoObject;
import character.BossController;
import character.EnemyController;
import character.GameObject;
import character.HealthObject;
import character.Hounds;
import character.PathHandler;
import character.PlayerController;
import character.Projectile;
import sprite.Animation;
import sprite.Animator;
import sprite.GameAnimations;
import sprite.Image;
import sprite.Sprite;
import sprite.Transform;
import states.MenuNewGameState;
import utils.Score;
import utils.SoundEffect;
import utils.XMLHandler;
import world.Phase.EnemyEntity;
import config.Config;
import enemies.BossEnemy;
import enemies.Ghoul;
import enemies.Hound;
import enemies.Zombie1;
import enemies.Zombie2;
import enemies.zBoss;


public class World {
	
	// Welt Kooardinate
	private int _x;
	
	// Sequence Liste und Phasen
	private ArrayList<Sequences> _seq;
	private int _currentSeqIndex = 0;
	private int _currentPhaseIndex = 0;
	private boolean _inPhase = false;
	
	// Derzeitiges Level und Schwierigkeitsgrad
	public static int currentLevel = 0;
	public static int hardnessLevel = 1;
	
	// Wenn _nextLevel gesetzt ist, wird zum nächsten Level gesprungen
	private boolean _nextLevel = false;
	private boolean _loadNextLevel = false;
	
	// Wenn alle Spieler gestorben sind
	private boolean _finalDeath = false;
	
	// Alle benötigten ArrayLists
	private ArrayList<EnemyController> _enemies;
	private ArrayList<Hounds> _hounds;
	private ArrayList<EnemyController> _deadBodies;
	private ArrayList<GameObject> _renderList;
	private ArrayList<AmmoObject> _ammoItems;
	private ArrayList<HealthObject> _healItems;
	private ArrayList<Animator> _bloodList;
	
	// Statische PlayerListe 
	public static ArrayList<PlayerController> players = new ArrayList<PlayerController>();
	
	// Backgroundklasse
	private Background _background;
	
	// GO Blinker Handler
	private GOBlinker _goBlinker;
	
	// Benutzerinterfaceklasse
	private UserInterface _ui;

	// Bosscontroller ( Nur im Endlevel )
	public static BossController _boss = null;
	
	// Sounds
	private SoundEffect blockSound = new SoundEffect("BLOCK");
	private SoundEffect dieSound1 = new SoundEffect("DIE1");
	private SoundEffect dieSound2 = new SoundEffect("DIE2");
	private SoundEffect startVoice = new SoundEffect("STARTVOICE");
	private SoundEffect houndSound = new SoundEffect("HOUNDS");
	private SoundEffect zombieSound = new SoundEffect("ZOMBIE");
	private SoundEffect ammoSound = new SoundEffect("AMMO");
	private SoundEffect healSound = new SoundEffect("HEAL");
	private SoundEffect goSound1 = new SoundEffect("GO1");
	private SoundEffect goSound2 = new SoundEffect("GO2");
	private SoundEffect thatsItSnd = new SoundEffect("THATS_IT");
	private SoundEffect bossEndSnd = new SoundEffect("BOSS_END");
	private SoundEffect bossDeadSnd = new SoundEffect("BOSS_DEAD");
	public World(int level){
		
		// Weltkooardiate initialisieren
		_x = 0;
		
		// Alle benötigten Listen erneuern, Level Laden und Spieler erstellen
		String levelName = _getLevelName(level);
		_initLists();
		_loadLevel(levelName);
		_createPlayers();
		
		// Startsound abspielen
		startVoice.play();
		
		// Background und GO Blinker initialisieren
		_background = new Background(levelName);
		_goBlinker = new GOBlinker(new Sprite("res/misc/arrow.gif"));
		
		
		if(level==Config.BOSS_LEVEL){
			_spawnBoss();
		}
		
		// BenutzerInterface erstellen
		_ui = new UserInterface(players);
	}
	
	// Falls Boss Level wird der Boss gespawnt
	private void _spawnBoss(){
		
		_boss = new BossController(Config.ENEMY_BOSS,players,_enemies,_renderList,_hounds);
		new BossEnemy().create(_boss);
		_renderList.add(_boss);
		
	}
	
	// Behandelt die Levels 
	private String _getLevelName(int level){
		String levelName = "";
		currentLevel = level;
		switch(level){
			case 1:
				levelName = "level1";
				break;
			case 2:
				levelName = "level2";
				break;
			case 3:
				levelName = "level3";
				break;
			case 4:
				levelName = "level4";
				break;
			case 5:
				levelName = "level5";
				break;
			default:
				System.out.println("LEVEL mit ID: "+level + " existiert nicht");
				System.exit(1);
		}
		return levelName;
	}
	
	// Hauptupdatefunktion der Weltklasse
	public void update(Graphics2D g, double delta){

		// X Weltkooardinate aktualisieren
		_x += Background.getMoveX();
		
		// Hintergrund zuerst zeichnen
		_background.update(g, delta,players);
		
		_handleLevelStates();
		
		handleWorldSequences();
		handleDeadBodies();
		handleEntities(g,delta);
		renderObjects(g,delta);
		
		_goBlinker.update(g);
	
		

		handleBlood(g);
		
		_background.drawFog(g, delta);
		_ui.update(g, delta);
		
		
		
	}
	
	
	private void _handleLevelStates(){
		if(_nextLevel){
			if(currentLevel==1){
				GameStateManager.getInstance().setState("LEVEL_2");
				_nextLevel = false;
				_loadNextLevel = true;
			}else if(currentLevel==2){
				GameStateManager.getInstance().setState("LEVEL_3");
				_nextLevel = false;
				_loadNextLevel = true;
			}else if(currentLevel==3){
				GameStateManager.getInstance().setState("ENDLEVELSCENE");
				_nextLevel = false;
				_loadNextLevel = true;
			}else if(currentLevel==4){
				GameStateManager.getInstance().setState("BOSSSCENE");
				_nextLevel = false;
				_loadNextLevel = true;
			}else if(currentLevel==5){
				GameStateManager.getInstance().setState("OUTRO");
				_nextLevel = false;
				_loadNextLevel = true;
				hardnessLevel++;
			}
		}
	}
	
	private void _initLists(){
		_seq = new ArrayList<Sequences>();
		_enemies = new ArrayList<EnemyController>();
		_deadBodies = new ArrayList<EnemyController>();
		_renderList = new ArrayList<GameObject>();
		_ammoItems = new ArrayList<AmmoObject>();
		_healItems = new ArrayList<HealthObject>();
		_hounds = new ArrayList<Hounds>();
		_bloodList = new ArrayList<Animator>();
	}
	

	private void _createPlayers(){
		if(Score.getScore()==0){
			switch(MenuNewGameState.chooseP1){
				case 1:
					_addPlayerToWorld(Config.PLAYER_JASMIN,true);
					break;
				case 2:
					_addPlayerToWorld(Config.PLAYER_CHRIS,true);
					break;
				case 3:
					_addPlayerToWorld(Config.PLAYER_JACK,true);
					break;
				default:
					break;
			}
	
			switch(MenuNewGameState.chooseP2){
				case 1:
					_addPlayerToWorld(Config.PLAYER_JASMIN,false);
					break;
				case 2:
					_addPlayerToWorld(Config.PLAYER_CHRIS,false);
					break;
				case 3:
					_addPlayerToWorld(Config.PLAYER_JACK,false);
					break;
				default:
					break;
			}
		}else{
			for(int i = 0;i<players.size();i++)
			{
				players.get(i).move(60-players.get(i).getX(),220+i*100-players.get(i).getZ());
				players.get(i).move(1,0);
				players.get(i).setForceBlock(true);
				if(currentLevel!=Config.BOSS_LEVEL){
					players.get(i).setLockedBounds(Config.BOUND_TOP, Config.BOUND_BOTTOM, 
							Config.BOUND_LEFT, Config.BOUND_RIGHT);
				}else{ // Boss Level besitzt andere Bodengröße
					players.get(i).setLockedBounds(Config.BOUND_TOP+20, Config.BOUND_BOTTOM, 
							Config.BOUND_LEFT, Config.BOUND_RIGHT);
				}
				
				_renderList.add(players.get(i));
			}
		}
		

	}
	
	
	private void _addPlayerToWorld(String name,boolean isP1){
	
		PlayerController player = new PlayerController(name,isP1);
		if(isP1){
			player.move(60,320);
		}else{
			player.move(60,220);
		}
		
		if(name==Config.PLAYER_JASMIN){
			player.getTransform().setOrigin(72, 144);
			player.setCollider(64, 44, 18, 98);
		}else if(name==Config.PLAYER_CHRIS){
			player.getTransform().setOrigin(68, 144);
		}else if(name==Config.PLAYER_JACK){
			player.getTransform().setOrigin(72, 144);
			player.setCollider(64, 44, 18, 98);
		}
		
		if(currentLevel!=Config.BOSS_LEVEL){
			player.setLockedBounds(Config.BOUND_TOP, Config.BOUND_BOTTOM, 
					Config.BOUND_LEFT, Config.BOUND_RIGHT);
		}else{ // Boss Level besitzt andere Bodengröße
			player.setLockedBounds(Config.BOUND_TOP+20, Config.BOUND_BOTTOM, 
					Config.BOUND_LEFT, Config.BOUND_RIGHT);
		}
		
		player.getTransform().scale(2, 2);
		
		players.add(player);
		_renderList.add(player);
		
	}
	
	public void handleWorldSequences(){
		
		if(_currentSeqIndex<_seq.size()){
			Sequences currSequence = _seq.get(_currentSeqIndex);
			if(currSequence.getX()<=getX()){
				
				_background.setLocked(true);
				
				if(!_inPhase){
					
					int yhoundcount = 0;
					int xhoundcount = 0;
					
					ArrayList<EnemyEntity> enemyEntities = currSequence.getPhaseAt(_currentPhaseIndex).allEnemies;
					
					for(int i=0;i<=enemyEntities.size()-1;i++){
						EnemyEntity currentEnemy = enemyEntities.get(i); 
						// Hunde brauchen anderen Spawner, als andere Gegnerarten
						if(currentEnemy.enemy==Config.ENEMY_HOUND){
							
							
							Random r = new Random();
							int random = r.nextInt(Config.HOUND_DIFF);
							
							houndSound.play();
							
							Hound hound = new Hound();
							Hounds enemy = hound.create(currentEnemy.enemy);
							
							if(yhoundcount==4){ yhoundcount = 0; }
							
							hound.spawn(xhoundcount, yhoundcount);
							
							_hounds.add(enemy);
							_renderList.add(enemy);
							

							
							if(random == 0){
								double hx = enemy.getX();
								double hy = enemy.getZ();
								hound = new Hound();
								enemy = hound.create(currentEnemy.enemy);
								hound.spawn(hx+50, hy);
								_hounds.add(enemy);
								_renderList.add(enemy);
							}
							if(random == 1){
								double hx = enemy.getX();
								double hy = enemy.getZ();
								hound = new Hound();
								enemy = hound.create(Config.ENEMY_HOUND);
								if(yhoundcount==3){
									hound.spawn(hx-82, hy-50);
								}else{
									hound.spawn(hx-82, hy+50);
								}
								_hounds.add(enemy);
								_renderList.add(enemy);
								
							}
							
							yhoundcount++;
							xhoundcount++;
	
						}else{ 
							
							EnemyController enemy = new EnemyController(currentEnemy.enemy,players);
							Random ran = new Random();
							zombieSound.play();
							switch(currentEnemy.enemy){
								case Config.ENEMY_ZOMBIE_1:
									new Zombie1().create(enemy);
									break;
								case Config.ENEMY_ZOMBIE_2:
									new Zombie2().create(enemy);
									break;
								case Config.ENEMY_GHOUL:
									new Ghoul().create(enemy);
									break;
								case Config.ENEMY_ZBOSS:
									new zBoss().create(enemy);
									break;
								default:
									System.out.println("Kann nicht "+currentEnemy.enemy+" spawnen.");
							}
				
							// Gegner an den Ränden spawnen lassen ( Zufällig )
							if(currentLevel!=Config.BOSS_LEVEL){
								if(currentEnemy.isLeft){
									enemy.move(-(200 + ran.nextInt(400 - 200 + 1)),
											Config.BOUND_TOP + ran.nextInt(Config.BOUND_BOTTOM - Config.BOUND_TOP + 1));
								}else{
									enemy.move(800 + ran.nextInt(1000 - 800 + 1),
											Config.BOUND_TOP + ran.nextInt(Config.BOUND_BOTTOM - Config.BOUND_TOP + 1));
								}
							}
							
							// Gegner Itemstatus geben
							if(currentEnemy.hasAmmo){
								enemy.setAmmoItem();
							}else if(currentEnemy.hasHeal){
								enemy.setHealItem();
							}
							
							// HP nach Schwierigkeit setzen
							enemy.setHp(enemy.getHp()*hardnessLevel);
							
							_enemies.add(enemy);
							_renderList.add(enemy);							
						}

					}
					
					_inPhase = true;
					
				}
			}	
		}else{ // Hier hier man das Level geschafft
			if(currentLevel != Config.BOSS_LEVEL){
				_background.setLocked(true);
				for(int i = 0; i<players.size();i++){
					if(currentLevel!=Config.BOSS_LEVEL){
						players.get(i).setLockedBounds(Config.BOUND_TOP, Config.BOUND_BOTTOM, Config.BOUND_LEFT, Config.BOUND_RIGHT+500);
					}else{
						players.get(i).setLockedBounds(Config.BOUND_TOP+20, Config.BOUND_BOTTOM, Config.BOUND_LEFT, Config.BOUND_RIGHT+500);
					}
					if(players.get(i).getX()>Config.BOUND_RIGHT+150 && !_loadNextLevel){
						_nextLevel = true;
					}else{
						_nextLevel = false;
					}
				}
			}else{
				_background.setLocked(true);
				for(int i = 0; i<players.size();i++){
					players.get(i).setLockedBounds(Config.BOUND_TOP+20, Config.BOUND_BOTTOM, Config.BOUND_LEFT, Config.BOUND_RIGHT);
					if(_boss==null){
						players.get(i).setLockedBounds(Config.BOUND_TOP+20, Config.BOUND_BOTTOM, Config.BOUND_LEFT, Config.BOUND_RIGHT+500);
						if(players.get(i).getX()>Config.BOUND_RIGHT+150 && !_loadNextLevel){
							_nextLevel = true;
						}else{
							_nextLevel = false;
						}
					}
				}
				
			}

		}
		
		if(_inPhase && _enemies.size()==0 && _hounds.size()==0){
			_inPhase = false;
			_currentPhaseIndex++;
			PathHandler.getInstance().freePaths();
			if(_seq.get(_currentSeqIndex).getPhases().size()==_currentPhaseIndex){
				_currentPhaseIndex = 0;
				_currentSeqIndex++;
				goSound1.play();
				goSound2.play();
				_goBlinker.start();
				_background.setLocked(false);
			}
		}
	}
	
	
	
	public void handleBlood(Graphics2D g){
		for(int i = 0; i<_bloodList.size(); i++){
			_bloodList.get(i).show(g);
			if(_bloodList.get(i).getCurrentFrame()==_bloodList.get(i).getMaxFrame()){
				_bloodList.remove(i);
			}
		}
	}
	
	
	public void handleDeadBodies(){
		for(int i = 0; i<=_deadBodies.size()-1;i++){
			EnemyController currEnemy = _deadBodies.get(i);
			if(currEnemy.getAnimator().isPlaying("dead")){
				if(currEnemy.hasAmmo()){
					AmmoObject currAmmo = new AmmoObject(new Sprite("res/misc/ammo.png"),currEnemy.getX(),currEnemy.getY()+30,0,0,22,22);
					currAmmo.getTransform().scale(1.6, 1.6);
					_ammoItems.add(currAmmo);
					_renderList.add(currAmmo);
				}else if(currEnemy.hasHeal()){
					HealthObject currHealth = new HealthObject(new Animator("heal"),currEnemy.getX(),currEnemy.getY()+30,0,0,22,22);		
					currHealth.getTransform().scale(1.6, 1.6);
					_healItems.add(currHealth);
					_renderList.add(currHealth);
				}
				currEnemy.freeAttack();
				_renderList.remove(currEnemy);
				_deadBodies.remove(i);
			}
		}
	}
	
	public void handleEntities(Graphics2D g, double delta){
		for(int i = players.size()-1;i>=0;i--){
			PlayerController currPlayer = players.get(i);
			
			if(Config.DEBUG){
				g.setColor(Color.blue);
				g.draw(currPlayer.getHitBox());
			}
			
			
			
			for(int z=_enemies.size()-1;z>=0;z--){
				EnemyController currEnemy = _enemies.get(z);
				if(currPlayer.isHit(currEnemy)){
					currPlayer.takeDamage(currEnemy);
					new BloodHandler(currPlayer,_bloodList,true);
				}
				
				if(Config.DEBUG){
					g.setColor(Color.blue);
					g.draw(currEnemy.getHitBox());
				}
				if(currEnemy.isHit(currPlayer)){
					currEnemy.takeDamage(currPlayer);
					new BloodHandler(currPlayer,_bloodList,false);
					if(currEnemy.isDead()){
						Random r2 = new Random();
						if(r2.nextInt(2)==0){
							dieSound1.play();
						}else{
							dieSound2.play();
						}
						currEnemy.freeAttack();
						Score.addScore(currEnemy.getScore());
						_deadBodies.add(currEnemy);
						_enemies.remove(z);
					}
				}
			}
			
			if(currentLevel==Config.BOSS_LEVEL){
				if(_boss!=null){
					if(currPlayer.isHit(_boss)){
						currPlayer.takeDamage(_boss);
						new BloodHandler(currPlayer,_bloodList,true);
					}
					
					if(_boss.isHitAble()){
						if(_boss.isHit(currPlayer)){
							_boss.takeDamage(currPlayer);
							new BloodHandler(_boss,_bloodList,false);
						}
					}
					if(Config.DEBUG){
						g.setColor(Color.blue);
						g.draw(_boss.getHitBox());
					}
					for(int j = 0; j<_boss.getProjectiles().size();j++){
						Projectile currProj = _boss.getProjectiles().get(j);
						if(!_renderList.contains(currProj)){
							_renderList.add(currProj);
						}
						
						if(Config.DEBUG){
							g.setColor(Color.red);
							g.draw(currProj.getHurtBox());
//							g.drawRect(currProj.getX(),currProj.getZ(),5,5);
						}


						if(currPlayer.isHit(currProj)){
							currPlayer.takeDamage(currProj);
							_renderList.remove(currProj);
							_boss.getProjectiles().remove(j);
							new BloodHandler(currPlayer,_bloodList,true);
						}
					
					}
					if(_boss.isDead()){
						_boss = null;
						bossDeadSnd.play();
						bossEndSnd.play();
					}
				}
			
			}
			
			
			for(int j = currPlayer.getProjectiles().size()-1;j>=0;j--){
				Projectile currProj = currPlayer.getProjectiles().get(j);
				
				if(Config.DEBUG){
					g.setColor(Color.red);
					g.draw(currProj.getHurtBox());
//					g.drawRect(currProj.getX(),currProj.getZ(),5,5);
				}
				if(!_renderList.contains(currProj)){
					_renderList.add(currProj);
				}
				
				for(int z = 0; z<=_enemies.size()-1;z++){
					EnemyController currEnemy = _enemies.get(z);
					if(currEnemy.isHit(currProj)){
						currEnemy.takeDamage(currProj);
						new BloodHandler(currEnemy,_bloodList,false);
						
						if(currEnemy.isDead()){
							Random r2 = new Random();
							if(r2.nextInt(2)==0){
								dieSound1.play();
							}else{
								dieSound2.play();
							}
							currEnemy.freeAttack();
							Score.addScore(currEnemy.getScore());
							_deadBodies.add(currEnemy);
							_enemies.remove(z);
						}
						currPlayer.getProjectiles().remove(currProj);
						_renderList.remove(currProj);
					}
					
				}
			}
			
			for(int j = 0; j<= _ammoItems.size()-1;j++) {
				AmmoObject currItem = _ammoItems.get(j);
				if(currItem.isHit(currPlayer)){
					ammoSound.play();
					_renderList.remove(currItem);
					_ammoItems.remove(currItem);
				}
			}
			
			for(int j = 0; j<= _healItems.size()-1;j++) {
				HealthObject currItem = _healItems.get(j);
				if(currItem.isHit(currPlayer)){
					healSound.play();
					_renderList.remove(currItem);
					_healItems.remove(currItem);
				}
			}
			
			for(int j = 0; j<= _hounds.size()-1;j++) {
				Hounds currHound = _hounds.get(j);
				if(currPlayer.isHit(currHound)){
					blockSound.play();
					currPlayer.takeDamage(currHound);
					currHound.isHit(currPlayer);
				}
				
				if(currHound.getX()<-100){
					_hounds.remove(currHound);
				}
				if(Config.DEBUG){
					g.setColor(Color.red);
					g.draw(currHound.getHitBox());
				}
			}
			
			if(currPlayer.isGameOver()){
				players.remove(i);
				_renderList.remove(currPlayer);
			}
			
		}
		
		
		if(_isGameOver()){
			GameStateManager.getInstance().setState("GAMEOVER");
			_finalDeath = true;
		}
	}
	
	private boolean _isGameOver(){
		return players.size()==0 && !_finalDeath;
	}
	
	
	public void renderObjects(Graphics2D g, double delta){
		Collections.sort(_renderList);
		for(int i = _renderList.size()-1;i>=0;i--){
			GameObject obj = _renderList.get(i);
			g.setColor(Color.red);
			obj.update(g, delta);
			if(Config.DEBUG){
				g.draw(obj.getHurtBox());
				g.drawRect((int)obj.getX(),(int)obj.getZ(),5,5);
			}

		}
	}
	
	private void _loadLevel(String level){
		XMLHandler rxml = new XMLHandler(Config.LEVEL_FILE);
		NodeList nList = rxml.getDoc().getElementsByTagName(level).item(0).getChildNodes();
		for (int i = 0; i < nList.getLength(); i++) {
			if (nList.item(i).getNodeType() == 1) {
				NamedNodeMap attributes = nList.item(i).getAttributes();
				int xseq = Integer.parseInt(attributes.item(0).getNodeValue());
				NodeList iList;
				_seq.add(new Sequences(xseq));
				
				iList = nList.item(i).getChildNodes();
				for(int j = 0; j < iList.getLength(); j++){
					if (iList.item(j).getNodeType() == 1){
						NamedNodeMap attributesPhase = iList.item(j).getAttributes();
						
						_seq.get(_seq.size()-1).addPhase(new Phase());
						_seq.get(_seq.size()-1).getLastPhase().setItemAmmo(Integer.parseInt(attributesPhase.item(0).getNodeValue()));
						_seq.get(_seq.size()-1).getLastPhase().setItemHeal(Integer.parseInt(attributesPhase.item(1).getNodeValue()));
						NodeList xList;
						xList = iList.item(j).getChildNodes();
						for(int z = 0; z < xList.getLength(); z++){
							if (xList.item(z).getNodeType() == 1){
								NamedNodeMap attributesEnemy = xList.item(z).getAttributes();
								int count = Integer.parseInt(attributesEnemy.item(0).getNodeValue());
								String dir = attributesEnemy.item(1).getNodeValue();
								String enemyname = xList.item(z).getNodeName();
			
								if(dir.equals("r")){
								
									for(int k = 0; k<=count-1; k++){
										_seq.get(_seq.size()-1).getLastPhase().addRightEnemy(enemyname);
									}
								}else{
									
									for(int k = 0; k<=count-1; k++){
										_seq.get(_seq.size()-1).getLastPhase().addLeftEnemy(enemyname);
									}
									
								}
							}
						}
						_seq.get(_seq.size()-1).getLastPhase().generateItems();
					}
				}
				
			}
		}
	}
	

	
	public int getX(){
		return _x;
	}
	

}
