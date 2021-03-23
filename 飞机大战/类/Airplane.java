package 飞机大战;

import java.util.Random;

/** 敌机: 是飞行物，也是敌人 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 2; // 移动的速度
	private int life; // 命

	/** 构造方法 */
	public Airplane() {
		image = ShootGame.airplane; // 图片
		width = image.getWidth(); // 宽
		height = image.getHeight(); // 高
		Random rand = new Random(); // 随机数对象
		x = rand.nextInt(ShootGame.WIDTH - this.width); // x:0到(窗口宽-敌机宽)之间的随机数
		y = -this.height; // y:负的敌机的高
		life = 7;
	}

	/** 重写getScore()得分 */
	public int getScore() {
		return 15; // 打掉一个敌机得15分
	}
	
	/** 敌机发射子弹 */
	public Bullet_x shoot_x() {
		int xStep = this.width / 2; // xStep:1/2敌机的宽
		int yStep = 5; // yStep:固定的5
		Bullet_x bs = new Bullet_x(this.x + xStep - 29, this.y + width + yStep); // x:敌机的x+1/2敌机的宽 y:敌机的y+20
		return bs;
	}

	/** 重写step()走步 */
	public void step() {
		y += speed; // y+(向下)
	}

	public void upLife(int up) {
		life += up;
	}

	public void subtractLife() {
		life--;
	}

	public int getLife() {
		return life; // 返回命数
	}

	/** 重写outOfBounds()检查是否越界 */
	public boolean outOfBounds() {
		return this.y >= ShootGame.HEIGHT; // 敌机的y>=窗口的高，即为越界了
	}
}