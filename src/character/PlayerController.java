package character;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import utils.SoundEffect;
import world.Background;
import config.Config;
import managers.InputManager;


public class PlayerController extends Player{
	
	private InputManager _input = InputManager.getInstance();
	
	private float _xspeed = 3.0f;
	private float _orgxspeed = 3.0f;
	private float _yspeed = 1.5f;
	private boolean _keyPressed = false;
	private boolean _attkKeyUp = true;
	private int _topLocked = 0;
	private int _bottomLocked = 0;
	private int _leftLocked = 0;
	private int _rightLocked = 0;
	private boolean _movePress = false;
	
	private final int _runDelay = 250;
	private int _runDelayTimerRight = 0;
	private boolean _ableToRunRight = false;
	private boolean _isWalkingRight = false;
	private boolean _runningRight = false;
	private int _runDelayTimerLeft= 0;
	private boolean _ableToRunLeft = false;
	private boolean _isWalkingLeft = false;
	private boolean _runningLeft = false;
	
	
	private int _playerUP;
	private int _playerDOWN;
	private int _playerRIGHT;
	private int _playerLEFT;
	private int _playerJUMP;
	private int _playerMELEE;
	private int _playerRANGE;
	
	private boolean _afr;
	private boolean _afl;
	
	
	public PlayerController(String animName, boolean player1){
		super(animName);
		if(player1){
			_playerUP = Config.PLAYER1_UP;
			_playerDOWN = Config.PLAYER1_DOWN;
			_playerRIGHT = Config.PLAYER1_RIGHT;
			_playerLEFT = Config.PLAYER1_LEFT;
			_playerJUMP = Config.PLAYER1_JUMP;
			_playerMELEE = Config.PLAYER1_MELEE;
			_playerRANGE = Config.PLAYER1_RANGE;
			
		}else{
			_playerUP = Config.PLAYER2_UP;
			_playerDOWN = Config.PLAYER2_DOWN;
			_playerRIGHT = Config.PLAYER2_RIGHT;
			_playerLEFT = Config.PLAYER2_LEFT;
			_playerJUMP = Config.PLAYER2_JUMP;
			_playerMELEE = Config.PLAYER2_MELEE;
			_playerRANGE = Config.PLAYER2_RANGE;
		}
	}

	
	public void update(Graphics2D g,double delta){

		super.update(g,delta);
		_handleMovement(delta)	;

	}
	
	private void _handleMovement(double delta){

		_keyPressed = false;
		_movePress = false;
		_xspeed = _orgxspeed;
		if(!isForceBlocked()){
			if(_input.isKeyDown(_playerUP) && getZ()>_topLocked){
				move(Background.getMoveX(),-_yspeed*delta);
				_keyPressed = true;
			}
			if(_input.isKeyDown(_playerDOWN)  && getZ()<_bottomLocked){
				move(Background.getMoveX(),_yspeed*delta);
				_keyPressed = true;
			}
			
			if(_input.isKeyDown(_playerRIGHT) &&  getAnimator().getX()<_rightLocked) {
				
				if(_ableToRunRight && _runDelayTimerRight>0){
					_runningRight = true;
					setRunning(true); 
				}
				if(_runningRight){
					if(!getAnimator().isPlaying("run") && !getAnimator().isPlaying("attk") && !getAnimator().isPlaying("jump")
							&& !getAnimator().isPlaying("attkShoot") && !getAnimator().isPlaying("run") 
							&& !getAnimator().isPlaying("dead")
							&& !getAnimator().isPlaying("block")
							){
						getAnimator().setAnimation("run");
					}
					_xspeed = _orgxspeed*2;
					move(_xspeed*delta,0);
					_isWalkingRight = false;
				}else{
					_runningRight = false;
					_ableToRunRight = false;
					move(_xspeed*delta,0);
					if(_runDelayTimerRight<=0 && !_isWalkingRight){
						_runDelayTimerRight = _runDelay;
					}
					_isWalkingRight = true;

				}
				_keyPressed = true;
				_movePress = true;
				
			}

			if(_runDelayTimerRight>0){
				_runDelayTimerRight -= Config.TIMER_DIF*delta;
			}
			
			if(_input.isKeyUp(_playerRIGHT) && _runningRight){
				_ableToRunRight = false;
				_isWalkingRight = false;
				_runningRight = false;
				setRunning(false); 
				if(getAnimator().isPlaying("run")){
					getAnimator().setAnimation("idle");
				}
			}
			
			if(_input.isKeyUp(_playerRIGHT) && _isWalkingRight){
				_ableToRunRight = true;
				_isWalkingRight = false;
			}
			

			
			if(_input.isKeyDown(_playerLEFT) &&  getAnimator().getX()>_leftLocked){
				if(_ableToRunLeft && _runDelayTimerLeft>0){
					_runningLeft = true;
					setRunning(true); 
				}
				if(_runningLeft){
					if(!getAnimator().isPlaying("run") && !getAnimator().isPlaying("attk") && !getAnimator().isPlaying("jump")
							&& !getAnimator().isPlaying("attkShoot") && !getAnimator().isPlaying("run")){
						getAnimator().setAnimation("run");
					}
					_xspeed = _orgxspeed*2;
					move(-_xspeed*delta,0);
					_isWalkingLeft = false;
				}else{
					_runningLeft = false;
					_ableToRunLeft = false;
					move(-_xspeed*delta,0);
					if(_runDelayTimerLeft<=0 && !_isWalkingLeft){
						_runDelayTimerLeft = _runDelay;
					}
					_isWalkingLeft = true;

				}
				_keyPressed = true;
				_movePress = true;
				
				
				
			}

			if(getAnimator().isPlaying("attkRun")){
				if (getAnimator().getX()>_leftLocked && getAnimator().getX()<_rightLocked){
					_xspeed = _orgxspeed*2;
					if(getTransform().isFlippedRight()){
						move(_xspeed*delta,0);
					}else{
						move(-_xspeed*delta,0);
					}
				}
			}
			
			if(_runDelayTimerLeft>0){
				_runDelayTimerLeft -= Config.TIMER_DIF*delta;
			}
			
			if(_input.isKeyUp(_playerLEFT) && _runningLeft){
				_ableToRunLeft = false;
				_isWalkingLeft = false;
				_runningLeft = false;
				setRunning(false); 
				if(getAnimator().isPlaying("run")){
					getAnimator().setAnimation("idle");
				}
			}
			
			if(_input.isKeyUp(_playerLEFT) && _isWalkingLeft){
				_ableToRunLeft = true;
				_isWalkingLeft = false;
			}
			
			
			if(_input.isKeyDown(_playerRIGHT) &&  getAnimator().getX()>=_rightLocked) {
				_movePress = true;
				move(Background.getMoveX(),0);
				
			}
			if(_input.isKeyDown(_playerLEFT) &&  getAnimator().getX()<=_leftLocked) {
				_movePress = true;
				move(Background.getMoveX(),0);
			}
			
			
			if(_input.isKeyDown(_playerJUMP)){
				jump();
				_keyPressed = true;
			}
			if(_input.isKeyDown(_playerMELEE) && _attkKeyUp){
				attack();
				_attkKeyUp = false;
				_keyPressed = true;
				
				_ableToRunLeft = false;
				_isWalkingLeft = false;
				_runningLeft = false;
				_ableToRunRight = false;
				_isWalkingRight = false;
				_runningRight = false;
				setRunning(false); 
			}
			
			if(isBlocked()){
				_ableToRunLeft = false;
				_isWalkingLeft = false;
				_runningLeft = false;
				_ableToRunRight = false;
				_isWalkingRight = false;
				_runningRight = false;
			}
			
			if(_input.isKeyUp(_playerMELEE) && !_attkKeyUp){
				_attkKeyUp = true;
			}
			
			if(_input.isKeyDown(_playerRANGE) && _attkKeyUp){
			
				rangedAttack();
				_keyPressed = true;
				
			}
			if(_input.isKeyUp(_playerRANGE) && !_attkKeyUp){
				_attkKeyUp = true;
			}			
			if(!_keyPressed && !_movePress){
				idle();
				getTransform().translate(-Background.getMoveX(),0);
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

	public float getMoveSpeedX(){
		return _xspeed;
	}
	
	public void setAfr(String dir){
		
		if(dir == "l"){
			
			_afl = true;
			
		}
		else{
			
			_afr = true;
			
		}
		
	}
	
	public void unsetAfr(String dir){
		
		if(dir == "l"){
			_afl = false;	
		}
		else{
			_afr = false;
		}
		
	}
	
	public boolean getAfr(String dir){
		
		if(dir == "l"){
			return _afl;
		}
		else{
			return _afr;
			
		}
		
	}
	

}
