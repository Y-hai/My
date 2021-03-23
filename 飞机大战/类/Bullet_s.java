package 飞机大战;

public class Bullet_s extends FlyingObject {
	private int speed = 6; // 移动的速度

	/** 构造方法 子弹的初始坐标随着英雄机定 */
	public Bullet_s(int x, int y) {
		image = ShootGame.bullet_s; // 图片
		width = image.getWidth(); // 宽
		height = image.getHeight(); // 高
		this.x = x - 28; // x:随英雄机
		this.y = y; // y:随英雄机
	}

	/** 重写step()走步 */
	public void step() {
		y -= speed; // y-(向上)
	}

	/** 重写outOfBounds()检查是否越界 */
		public boolean outOfBounds(){
			return this.y<=-this.height; //子弹的y<=负的子弹的高，即为越界了
		}
}