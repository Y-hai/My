package 飞机大战;

public class Bullet_x extends FlyingObject {
	private int speed = 3; //移动的速度
	/** 构造方法 子弹的初始坐标随着敌机定*/
	public Bullet_x(int x, int y) {
		image = ShootGame.bullet_x; //图片
		width = image.getWidth();   //宽
		height = image.getHeight(); //高
		this.x = x; //x:随敌机
		this.y = y; //y:随敌机
	}

	/** 重写step()走步 */
	public void step(){
		y+=speed; //y+(向下)
	}
	
	/** 重写outOfBounds()检查是否越界 */
	public boolean outOfBounds(){
		return this.y>=ShootGame.HEIGHT; //子弹的y>=窗口的高，即为越界了
	}
}
