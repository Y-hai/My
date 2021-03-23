package 飞机大战;

import java.util.Random;

/** 小敌机: 是飞行物， */
public class Plane extends FlyingObject implements Award {
	private int xSpeed = 2; // x坐标移动速度
	private int ySpeed = 3; // y坐标移动速度
	private int life = 7;
	private int awardType; // 奖励的类型(0或1)

	/** 构造方法 */
	public Plane() {
		image = ShootGame.plane; // 图片
		width = image.getWidth(); // 宽
		height = image.getHeight(); // 高
		Random rand = new Random(); // 随机数对象
		x = rand.nextInt(ShootGame.WIDTH - this.width); // x:0到(窗口宽-小敌机宽)之间的随机数
		y = -this.height; // y:负的小敌机的高
		awardType = rand.nextInt(2); // 0和1之间的随机数
	}

	public Bullet_x shoot_x() {
		int xStep = this.width / 2; // xStep:1/2敌机的宽
		int yStep = 5; // yStep:固定的20
		Bullet_x bs = new Bullet_x(this.x + xStep - 29, this.y + width + yStep); // x:敌机的x+1/2敌机的宽 y:敌机的y+20
		return bs;
	}

	/** 获取奖励类型 */
	public int getType() {
		return awardType; // 返回奖励类型
	}

	/** 重写step()走步 */
	public void step() {
		x += xSpeed; // x+(向左或向右)
		y += ySpeed; // y+(向下)
		if (x >= ShootGame.WIDTH - this.width) { // 若x>=(窗口宽-敌机宽)，则xSpeed为-1，加-1即为向左
			xSpeed = -1;
		}
		if (x <= 0) { // 若x<=0，则xSpeed为1，加1即为向右
			xSpeed = 1;
		}
	}

	public void subtractLife() {
		life--;
	}

	public void upLife(int up) {
		life += up;
	}

	public int getLife() {
		return life; // 返回命数
	}

	/** 重写outOfBounds()检查是否越界 */
	public boolean outOfBounds() {
		return this.y >= ShootGame.HEIGHT; // 小敌机的y>=窗口的高，即为越界了
	}
}