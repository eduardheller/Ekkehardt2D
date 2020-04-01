package world;

import java.util.ArrayList;
import java.util.Random;

import character.*;

public class Phase {
	ArrayList<String> leftEnemies;
	ArrayList<String> rightEnemies;
	ArrayList<EnemyEntity> allEnemies; 
	private int _ammos;
	private int _heal;
	
	public Phase(){
		leftEnemies = new ArrayList<String>();
		rightEnemies = new ArrayList<String>();
		allEnemies = new ArrayList<EnemyEntity>();
	}
	
	public class EnemyEntity{
		
		public String enemy;
		public boolean hasAmmo;
		public boolean hasHeal;
		public boolean isLeft;
		
	}
	
	public void addLeftEnemy(String enemy){
		leftEnemies.add(enemy);
	}
	
	public void addRightEnemy(String enemy){
		rightEnemies.add(enemy);
	}
	
	public void setItemAmmo(int ammo){
		_ammos = ammo;
	}
	
	public int getItemAmmo(){
		return _ammos;
	}
	
	public void setItemHeal(int heal){
		_heal = heal;
	}
	
	public int getItemHeal(){
		return _heal;
	}
	
	public void generateItems(){
		
		int count = leftEnemies.size() + rightEnemies.size() - 1;
		Random r = new Random();
		ArrayList<Integer> itemList = new ArrayList<Integer>();
		
		for(int i = 0 ; i<= count ; i++){
			itemList.add(0);
		}
		
		for(int i = 0 ; i< getItemAmmo() ; i++){
			
			int index = 0;
			for(index = r.nextInt(count); itemList.get(itemList.get(index))==1; ){
				index = r.nextInt(count);
				
			}
			
			itemList.set(index,1);
			
		}
		
		for(int i = 0 ; i< getItemHeal() ; i++){
			
			int index = 0;
			for(index = r.nextInt(count); itemList.get(itemList.get(index))==1 ||  itemList.get(itemList.get(index))==2; ){
				index = r.nextInt(count);
				
			}
			itemList.set(index,2);
			
		}
		
		for(int i = 0; i<= leftEnemies.size()-1; i++){
			EnemyEntity enemyObj = new EnemyEntity();
			enemyObj.enemy = leftEnemies.get(i);
			enemyObj.isLeft = true;
			if(itemList.get(i)==1){
				enemyObj.hasAmmo = true;
			}else if(itemList.get(i)==2){
				enemyObj.hasHeal = true;
			}
			allEnemies.add(enemyObj);
	
		}
		
		for(int i = 0; i<= rightEnemies.size()-1; i++){
			EnemyEntity enemyObj = new EnemyEntity();
			enemyObj.enemy = rightEnemies.get(i);
			enemyObj.isLeft = false;
			int size = 0;
			if(leftEnemies.size()!=0){
				size = leftEnemies.size()-1;
			}
			if(itemList.get(size+i)==1){
				enemyObj.hasAmmo = true;
			}else if(itemList.get(size+i)==2){
				enemyObj.hasHeal = true;
			}
			allEnemies.add(enemyObj);
			
		}
	}
	
}
