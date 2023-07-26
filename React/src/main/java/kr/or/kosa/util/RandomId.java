package kr.or.kosa.util;
import java.util.Random;

public class RandomId { //객체에 사용할 난수 만드는 객체
	
	private int randomNumber;
	
	Random random = new Random();
	public RandomId() {
		this.randomNumber = random.nextInt(1000000000)+1;
		
	}
	public int getRandomNumber() {
		return randomNumber;
	}
	public void setRandomNumber(int randomNumber) {
		this.randomNumber = randomNumber;
	}
	public Random getRandom() {
		return random;
	}
	public void setRandom(Random random) {
		this.random = random;
	}
	 
}	
