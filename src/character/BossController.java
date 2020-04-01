package character;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import states.LarsEddyState;
import utils.SoundEffect;
import utils.Vector2d;
import world.Background;
import world.World;
import config.Config;
import effects.BloodEffect;
import enemies.Ghoul;
import enemies.Hound;
import enemies.Zombie1;
import enemies.Zombie2;
import managers.InputManager;
import managers.ScreenManager;


public class BossController extends Boss{
	
	private InputManager _input = InputManager.getInstance();
	
	private boolean _movePress = false;
	Path pathFirst;
	Path pathSecond;
	
	private int _pathIndex;
	private int currentPointIndex = 0;
	private Point position;
	private Path pathToFollow;
	private ArrayList<PlayerController> _player;
	private ArrayList<EnemyController> _enemies;
	private ArrayList<GameObject> _renderList;
	private ArrayList<Hounds> _hounds;
	private boolean _firstMove;
	private boolean _secondMove;
	private boolean _thirdMove;
	private boolean _fourthMove;
	private boolean _fifthMove;
	private boolean _sixthMove;
	
	private double _speed;
	
	
	private final int _startDelay = 2000;
	private int _startTimer = _startDelay;
	
	private final int _secondMoveDelay = 10000;
	private int _secondMoveTimer = _secondMoveDelay;
	
	private final int _hittableDelay = 5000;
	private int _hittableTimer = _hittableDelay;

	private int _zombe1Size = 3;
	private int _zombe2Size = 4;
	private int _ghoulSize = 2;
	private int _houndSize = 11;
	
	
	private SoundEffect houndSound = new SoundEffect("HOUNDS");
	private SoundEffect zombieSound = new SoundEffect("ZOMBIE");
	private SoundEffect attack1 = new SoundEffect("BOSS_LAUGH");
	private SoundEffect attack2 = new SoundEffect("BOSS_DIE");
	private SoundEffect attack3 = new SoundEffect("BOSS_WORLD");
	private SoundEffect attack4 = new SoundEffect("BOSS_DESTROY");
	private boolean _isHitAble;
	
	public BossController(String animName,ArrayList<PlayerController> players,ArrayList<EnemyController> enemies,ArrayList<GameObject> renderList,ArrayList<Hounds> hounds){
		super(animName);
		
		_player = players;
		_enemies = enemies;
		_renderList = renderList;
		_hounds = hounds;
		pathFirst = new Path();
		move(480,240);
		pathSecond = new Path();
		pathFirst.addVector(Config.BOUND_RIGHT,Config.BOUND_TOP+20);
		pathFirst.addVector(Config.BOUND_LEFT,Config.BOUND_TOP+20);
		pathFirst.addVector(Config.BOUND_LEFT,Config.BOUND_BOTTOM);
		pathFirst.addVector(Config.BOUND_LEFT+100,Config.BOUND_TOP+20);
		pathFirst.addVector(Config.BOUND_LEFT+100,Config.BOUND_BOTTOM);
		pathFirst.addVector(Config.BOUND_LEFT+200,Config.BOUND_TOP+20);
		pathFirst.addVector(Config.BOUND_LEFT+200,Config.BOUND_BOTTOM);
		pathFirst.addVector(Config.BOUND_LEFT+300,Config.BOUND_TOP+20);
		pathFirst.addVector(Config.BOUND_LEFT+300,Config.BOUND_BOTTOM);
		pathFirst.addVector(Config.BOUND_LEFT+400,Config.BOUND_TOP+20);
		pathFirst.addVector(Config.BOUND_LEFT+400,Config.BOUND_BOTTOM);
		pathFirst.addVector(Config.BOUND_RIGHT,Config.BOUND_TOP+20);
		pathFirst.addVector(Config.BOUND_RIGHT,Config.BOUND_BOTTOM);
		pathFirst.addVector(340,240);
		pathToFollow = pathFirst;
		
		_pathIndex = 0;
		_isHitAble = false;
		_firstMove = true;
		_secondMove = false;
		_thirdMove = false;
		_fourthMove = false;
		_fifthMove = false;
		_sixthMove = false;
		
		_speed = 5.0;
		
		attack1.play();
		
	}

	
	
	public void update(Graphics2D g,double delta){
		
		super.update(g,delta);
		
		_handleMovement(delta);
	

	}
	
	private void _handleMovement(double delta){
		
		if(isDead()){
			getTransform().translate(-Background.getMoveX(),0);
		}else{
			if(_firstMove){
				_startTimer -= Config.TIMER_DIF*delta;
				if(_startTimer <=0){
					tick(delta);
					setHitting(true);
					_startTimer = 0;
				}

			}
			if(_secondMove){
				setHitting(false);
				
				if(!getAnimator().isPlaying("shoots")){
					getAnimator().setAnimation("shoots");
					attack2.play();
					spawnHounds();
				}
				rangedAttack();
				_secondMoveTimer -= Config.TIMER_DIF*delta;
				if(_secondMoveTimer<=0){
					_secondMoveTimer = _secondMoveDelay;
					_secondMove = false;
					_sixthMove = true;
					_isHitAble = true;
					attack4.play();
				}
			}
			if(_sixthMove){
				if(!getAnimator().isPlaying("hit") && !isBlocked()){
					getAnimator().setAnimation("hit");
				}
				_hittableTimer -= Config.TIMER_DIF*delta;
				if(_hittableTimer<=0){
					_hittableTimer = _hittableDelay;
					_thirdMove = true;
					_sixthMove = false;
					_speed = 3.0;
					pathSecond = new Path();
					pathSecond.addVector(340, 100);
					pathToFollow = pathSecond;
					_pathIndex = 0;
					getAnimator().setAnimation("walk");
					_isHitAble = false;
				}
			}
			
			if(_thirdMove){
				tick(delta);
			}
			if(_fourthMove){
				
				if(!getAnimator().isPlaying("attk")){
					getAnimator().setAnimation("attk");
					attack3.play();
				}
				if(getAnimator().getCurrentFrame() == getAnimator().getMaxFrame()){
					getAnimator().setAnimation("idle");
					_fifthMove = true;
					_speed = 5.0;
					_fourthMove = false;
					spawnEnemies();
				}
			}
			if(_fifthMove){
				if(_enemies.size()<=0){
					_firstMove = true;
					_fifthMove = false;
					attack1.play();
					_pathIndex = 0;
					pathToFollow = pathFirst;
					PathHandler.getInstance().freePaths();
				}
			}
		}
		
	}
	
	
	public void spawnHounds(){
		
		
		
		int yhoundcount = 0;
		int xhoundcount = 0;
		
		
		for(int i = 0 ; i < _houndSize; i++){
			Random r = new Random();
			int random = r.nextInt(Config.HOUND_DIFF);
			
			houndSound.play();
			
			Hound hound = new Hound();
			Hounds enemy = hound.create(Config.ENEMY_HOUND);
			
			if(yhoundcount==4){ yhoundcount = 0; }
			
			hound.spawn(xhoundcount, yhoundcount);
			
			_hounds.add(enemy);
			_renderList.add(enemy);
			

			
			if(random == 0){
				double hx = enemy.getX();
				double hy = enemy.getZ();
				hound = new Hound();
				enemy = hound.create(Config.ENEMY_HOUND);
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
			
		}

	}
	
	public void spawnEnemies(){
		zombieSound.play();
		for(int i = 0; i<_zombe1Size ; i++){
			EnemyController enemy = new EnemyController(Config.ENEMY_ZOMBIE_1,_player);
			Random ran = new Random();
			new Zombie1().create(enemy);

			if(i%2==0){
				enemy.move(-(200 + ran.nextInt(400 - 200 + 1)),
						Config.BOUND_TOP+20 + ran.nextInt(Config.BOUND_BOTTOM - Config.BOUND_TOP+20 + 1));
			}else{
				enemy.move(800 + ran.nextInt(1000 - 800 + 1),
						Config.BOUND_TOP+20 + ran.nextInt(Config.BOUND_BOTTOM - Config.BOUND_TOP+20 + 1));
			}
			
			
			// HP nach Schwierigkeit setzen
			enemy.setHp(enemy.getHp()*World.hardnessLevel);
			
			_enemies.add(enemy);
			_renderList.add(enemy);		
		}
		
		for(int i = 0; i<_zombe2Size ; i++){
			EnemyController enemy = new EnemyController(Config.ENEMY_ZOMBIE_2,_player);
			Random ran = new Random();
			new Zombie2().create(enemy);

			if(i%2==0){
				enemy.move(-(200 + ran.nextInt(400 - 200 + 1)),
						210 + ran.nextInt(555 - 210 + 1));
			}else{
				enemy.move(800 + ran.nextInt(1000 - 800 + 1),
						210 + ran.nextInt(555 - 210 + 1));
			}
			
			
			// HP nach Schwierigkeit setzen
			enemy.setHp(enemy.getHp()*World.hardnessLevel);
			
			_enemies.add(enemy);
			_renderList.add(enemy);		
		}
		
		for(int i = 0; i<_ghoulSize ; i++){
			EnemyController enemy = new EnemyController(Config.ENEMY_GHOUL,_player);
			Random ran = new Random();
			new Ghoul().create(enemy);

			if(i%2==0){
				enemy.move(-(200 + ran.nextInt(400 - 200 + 1)),
						210 + ran.nextInt(555 - 210 + 1));
			}else{
				enemy.move(800 + ran.nextInt(1000 - 800 + 1),
						210 + ran.nextInt(555 - 210 + 1));
			}
			
			
			// HP nach Schwierigkeit setzen
			enemy.setHp(enemy.getHp()*World.hardnessLevel);
			
			_enemies.add(enemy);
			_renderList.add(enemy);		
		}
		
	}
	
	
	public void followPath(double delta){
		
		
			Vector2d towards = new Vector2d(pathToFollow.get(_pathIndex).dX-getX(),
					pathToFollow.get(_pathIndex).dY-getZ());

			Vector2d position = new Vector2d(getX(),getZ());
			Vector2d normalizedTowards = towards.normalize();
			
			Vector2d posVector = new Vector2d(normalizedTowards.dX,normalizedTowards.dY);

			posVector.scale(_speed);
			posVector.scale(delta);
			posVector.add(new Vector2d(getX(),getZ()));
			
			
			setPosition(posVector.dX,posVector.dY);
			
			
			Vector2d result = new Vector2d(pathToFollow.get(_pathIndex).dX-getX(),pathToFollow.get(_pathIndex).dY-getZ());
			Vector2d normalizedResult = result.normalize();
		
			
			
			
			if(Math.abs(getX()-pathToFollow.get(_pathIndex).dX)<5 
					&& Math.abs(getZ()-pathToFollow.get(_pathIndex).dY)<5){

				_pathIndex++;		
				if(_firstMove && _pathIndex == pathToFollow.vec2.size()-1){
					_speed = 3.0;
				}
				if(_pathIndex == pathToFollow.vec2.size()){
					_pathIndex = 0;
					if(_firstMove){
						_firstMove = false;
						_secondMove = true;
					}
					if(_thirdMove){
						_thirdMove = false;
						_fourthMove = true;
					}
				}
			}			
			
			
			
			if((position.dX-pathToFollow.get(_pathIndex).dX)<0 && !getTransform().isFlippedRight()){
				getTransform().flip();
			}
			if((position.dX-pathToFollow.get(_pathIndex).dX
					)>=0 && getTransform().isFlippedRight()){
				getTransform().flip();
			}	
				
			


			
	}

	
	public void tick(double delta){
		followPath(delta);
	}
	


	public boolean isHitAble(){
		return _isHitAble;
	}

	public boolean hasMovePressed(){
		return _movePress;
	}

	
}
