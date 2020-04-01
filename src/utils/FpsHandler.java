package utils;

/**
* FPS Handler Klasse dient dazu Frames zu berechnen
* @author  Eduard Heller
* @version 1.0
*/
public class FpsHandler{
	
	private long _lastFpsTime;
	private int _fps;
	private int _displayFps;
	private long _seconds = 1000000000;
	
	/**
	 * @param Update Zeit einer Gameschleife um Bilder pro Sekunde berechnen zu können
	 * @return Frames pro Sekunde
	 */
	public int getFps(long updateTime){
		_lastFpsTime += updateTime;
		_fps++;
		if (_lastFpsTime >= _seconds)
		{
			_displayFps = _fps;
		   _lastFpsTime = 0;
		   _fps = 0;
		}
		return _displayFps;
	}
	
}
