package �ɻ���ս;

import java.util.Random;

/** �л�: �Ƿ����Ҳ�ǵ��� */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 2; // �ƶ����ٶ�
	private int life; // ��

	/** ���췽�� */
	public Airplane() {
		image = ShootGame.airplane; // ͼƬ
		width = image.getWidth(); // ��
		height = image.getHeight(); // ��
		Random rand = new Random(); // ���������
		x = rand.nextInt(ShootGame.WIDTH - this.width); // x:0��(���ڿ�-�л���)֮��������
		y = -this.height; // y:���ĵл��ĸ�
		life = 7;
	}

	/** ��дgetScore()�÷� */
	public int getScore() {
		return 15; // ���һ���л���15��
	}
	
	/** �л������ӵ� */
	public Bullet_x shoot_x() {
		int xStep = this.width / 2; // xStep:1/2�л��Ŀ�
		int yStep = 5; // yStep:�̶���5
		Bullet_x bs = new Bullet_x(this.x + xStep - 29, this.y + width + yStep); // x:�л���x+1/2�л��Ŀ� y:�л���y+20
		return bs;
	}

	/** ��дstep()�߲� */
	public void step() {
		y += speed; // y+(����)
	}

	public void upLife(int up) {
		life += up;
	}

	public void subtractLife() {
		life--;
	}

	public int getLife() {
		return life; // ��������
	}

	/** ��дoutOfBounds()����Ƿ�Խ�� */
	public boolean outOfBounds() {
		return this.y >= ShootGame.HEIGHT; // �л���y>=���ڵĸߣ���ΪԽ����
	}
}