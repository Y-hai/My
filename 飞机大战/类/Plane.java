package �ɻ���ս;

import java.util.Random;

/** С�л�: �Ƿ���� */
public class Plane extends FlyingObject implements Award {
	private int xSpeed = 2; // x�����ƶ��ٶ�
	private int ySpeed = 3; // y�����ƶ��ٶ�
	private int life = 7;
	private int awardType; // ����������(0��1)

	/** ���췽�� */
	public Plane() {
		image = ShootGame.plane; // ͼƬ
		width = image.getWidth(); // ��
		height = image.getHeight(); // ��
		Random rand = new Random(); // ���������
		x = rand.nextInt(ShootGame.WIDTH - this.width); // x:0��(���ڿ�-С�л���)֮��������
		y = -this.height; // y:����С�л��ĸ�
		awardType = rand.nextInt(2); // 0��1֮��������
	}

	public Bullet_x shoot_x() {
		int xStep = this.width / 2; // xStep:1/2�л��Ŀ�
		int yStep = 5; // yStep:�̶���20
		Bullet_x bs = new Bullet_x(this.x + xStep - 29, this.y + width + yStep); // x:�л���x+1/2�л��Ŀ� y:�л���y+20
		return bs;
	}

	/** ��ȡ�������� */
	public int getType() {
		return awardType; // ���ؽ�������
	}

	/** ��дstep()�߲� */
	public void step() {
		x += xSpeed; // x+(���������)
		y += ySpeed; // y+(����)
		if (x >= ShootGame.WIDTH - this.width) { // ��x>=(���ڿ�-�л���)����xSpeedΪ-1����-1��Ϊ����
			xSpeed = -1;
		}
		if (x <= 0) { // ��x<=0����xSpeedΪ1����1��Ϊ����
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
		return life; // ��������
	}

	/** ��дoutOfBounds()����Ƿ�Խ�� */
	public boolean outOfBounds() {
		return this.y >= ShootGame.HEIGHT; // С�л���y>=���ڵĸߣ���ΪԽ����
	}
}