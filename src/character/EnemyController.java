package character;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import states.LarsEddyState;
import utils.SoundEffect;
import utils.Vector2d;
import world.Background;
import config.Config;
import effects.BloodEffect;
import managers.InputManager;


public class EnemyController extends Enemy{
	
	private InputManager _input = InputManager.getInstance();
	
	private double _speed = 1.5;
	
	
	private boolean _keyPressed = false;
	private boolean _attkKeyUp = true;
	private int _topLocked = 0;
	private int _bottomLocked = 0;
	private int _leftLocked = 0;
	private int _rightLocked = 0;
	private boolean _movePress = false;
	private ArrayList<PlayerController> _player;
	private BloodEffect beffect;
	private int _xAttackDist = 40;
	private int _yAttackDist = 0;
	
	private final int _maxWaitTime = 5000;
	private final int _minWaitTime = 1000;
	private int _waitTimer;
	private boolean  _isWaiting = false;
	
	//Path Variablen
	
	private PathHandler _ph;
	private Path pathToFollow;
	private int _pathIndex;
	private boolean _rw = false;
	private double _flipCord = 1;
	//Ki Variablen
	
	private  int _attacking;
	private int _delayRemaining = getAttackDelay();
	
	
	public EnemyController(String animName,ArrayList<PlayerController> players){
		super(animName);
		_attacking = -1;
		_player = players;
		_ph = PathHandler.getInstance();
		pathToFollow = _ph.getFreePath();
		_pathIndex = 0;

	}

	
	public void update(Graphics2D g,double delta){
	
		super.update(g,delta);
		if(!_isWaiting || isDead()){
			move(0,0);
		}
	
		if(_attacking > _player.size() -1){
			_attacking = -1;
		}
		_handleKi();
		_handleMovement(delta);

	}
	
	private void _handleKi(){
		if(getzBoss()){
			if(_player.size()>1){
				if(_attacking<0){
					_attacking = 0;
				}
				Random r = new Random();
				if(r.nextInt(5000) == 1){
					if(_attacking == 0){
						_attacking = 1;	
					}
					else{
						_attacking = 0;	
					}	
				}
			}else if(_player.size()==1){
				_attacking = 0;
			}
		}
		else{	
			for(int i=0; i<_player.size(); i++){
				if(_player.get(i).getX() > this.getX() 
						&& !_player.get(i).getAfr("l") 
						&& _attacking < 0){
					
					_player.get(i).setAfr("l");
					_attacking = i;
					
				}
				else if(_player.get(i).getX() < this.getX() 
						&& !_player.get(i).getAfr("r") && _attacking < 0){
					_player.get(i).setAfr("r");
					
					_attacking = i;
				}
				
			}
		}
	}

	
	private void _handleMovement(double delta){
		
		tick(delta);
		
	}
	
	public void followPath(double delta){
		
			if(_attacking<0 && !_isWaiting){
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
			
				
				
				double xResult = Math.round(normalizedResult.dX*100.0)/100.0;
				double yResult = Math.round(normalizedResult.dY*100.0)/100.0;
				
				double xTowards = Math.round(normalizedTowards.dX*100.0)/100.0;
				double yTowards = Math.round(normalizedTowards.dY*100.0)/100.0;
				
				
				if(xTowards != xResult || yTowards != yResult){
					
					_pathIndex++;
					if(_pathIndex == pathToFollow.vec2.size()){
						_pathIndex = 0;
					}
				}			
				
				
				
				if((position.dX-pathToFollow.get(_pathIndex).dX)<0 && !getTransform().isFlippedRight()){
					getTransform().flip();
				}
				if((position.dX-pathToFollow.get(_pathIndex).dX
						)>=0 && getTransform().isFlippedRight()){
					getTransform().flip();
				}	
				
			}else if(!_isWaiting){
				if(!_isInRange()){
					Vector2d towards = new Vector2d(_player.get(_attacking).getX()-getX(),
							_player.get(_attacking).getZ()-getZ());


					Vector2d normalizedTowards = towards.normalize();
					
					Vector2d posVector = new Vector2d(normalizedTowards.dX,normalizedTowards.dY);

					posVector.scale(_speed);
					posVector.scale(delta);
					posVector.add(new Vector2d(getX(),getZ()));
					
					setPosition(posVector.dX,posVector.dY);
					
				
					if((getX()-_player.get(_attacking).getX())<0 && !getTransform().isFlippedRight()){
						getTransform().flip();
					}
					
					if((getX()-_player.get(_attacking).getX())>=0 && getTransform().isFlippedRight()){
						getTransform().flip();
					}	
					
				}
			}


			
	}
	
	private boolean _isInRange(){
		return Math.abs(getX() - _player.get(_attacking).getX()) < 
				getAttackDst() && Math.abs(getZ() - _player.get(_attacking).getZ()) < 
				Config.Z_FIGHT_DIST;
	}
	
	public void tick(double delta){

		
		followPath(delta);
		
		if(!_isWaiting){
			if(_attacking>-1){
				if(_isInRange()){
					if(!getAnimator().isPlaying("idle") 
							&& !getAnimator().isPlaying("attk") 
							&& !getAnimator().isPlaying("dead")
							&& !getAnimator().isPlaying("die")
							&& !getAnimator().isPlaying("block")
							){
						getAnimator().setAnimation("idle");
					}
					_delayRemaining-=Config.TIMER_DIF*delta;
					if(_delayRemaining <=0){
						attack();
						_delayRemaining = getAttackDelay();
					}
				}
			}
			Random r = new Random();
			int waitnmb = r.nextInt(700);
				
			if(waitnmb == 0){
				_isWaiting = true;
				_waitTimer = _minWaitTime + r.nextInt(_maxWaitTime-_minWaitTime+1);
				if(!getAnimator().isPlaying("idle") 
						&& !getAnimator().isPlaying("attk") 
						&& !getAnimator().isPlaying("dead")
						&& !getAnimator().isPlaying("die")
						&& !getAnimator().isPlaying("block")
						){
					getAnimator().setAnimation("idle");
				}
				
			}
			
		}

		if(_isWaiting){
			_waitTimer -= Config.TIMER_DIF*delta;
			if(_waitTimer<=0){
				_isWaiting = false;
				_waitTimer = 0;
			}
		}
	}
	
	
	public void setLockedBounds(int top,int bottom,int left, int right){
		_topLocked = top;
		_bottomLocked = bottom;
		_rightLocked = right;
		_leftLocked = left;
	}

	public boolean hasMovePressed(){
		return _movePress;
	}

	
	
	public void freeAttack(){
		
		if(_attacking>-1 && _player.get(_attacking).getAfr("l")){
			
			_player.get(_attacking).unsetAfr("l");
			pathToFollow._fp = true;
			_attacking = -1;
			
		}
		else if(_attacking>-1 && _player.get(_attacking).getAfr("r")){
			pathToFollow._fp = true;
			_player.get(_attacking).unsetAfr("r");
			_attacking = -1;
		}
		
	}
	
}
