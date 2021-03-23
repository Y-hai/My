package �ɻ���ս;

import java.awt.image.BufferedImage;

/** Ӣ�ۻ�: �Ƿ����� */
public class Hero extends FlyingObject {
	private int fire; // ����ֵ
	private int life; // ��
	private BufferedImage[] images; // ���л���ͼƬ����
	private int index; // Э��ͼƬ�л�

	/** ���췽�� */
	public Hero() {
		// �ȸ����и�����ı�����ֵ
		image = ShootGame.hero0; // ͼƬ
		width = image.getWidth(); // ��
		height = image.getHeight(); // ��
		x = 200; // x:�̶���ֵ
		y = 500; // y:�̶���ֵ
		fire = 0; // Ĭ��Ϊ0(��������)
		life = 3; // Ĭ��3����
		images = new BufferedImage[] { ShootGame.hero0, ShootGame.hero1 }; // ����ͼƬ�л�
		index = 0; // Э��ͼƬ�л�
	}

	/** ��дstep()�߲� */
	public void step() { // 10������һ��
		image = images[index++ / 10 % images.length];
	}

	/** Ӣ�ۻ������ӵ�(�����ӵ�����) */
	public Bullet[] shoot() {
		int xStep = this.width / 4; // xStep:1/4Ӣ�ۻ��Ŀ�
		int yStep = 20; // yStep:�̶���20
		if (fire > 1) { // ˫��
			if (fire > 1500) {
				Bullet[] bs = new Bullet[3]; // 3���ӵ�
				bs[0] = new Bullet(this.x + 1 * xStep, this.y - yStep);
				Bullet s = new Bullet(this.x + 2 * xStep + 15, this.y - yStep - 20);
				s.image = ShootGame.bullet_s;
				bs[1] = s;
				bs[2] = new Bullet(this.x + 3 * xStep, this.y - yStep);
				fire -= 3; // ����һ��˫�������������ֵ��2
				return bs;
			}
			Bullet[] bs = new Bullet[2]; // 2���ӵ�
			// x:Ӣ�ۻ���x+1/4Ӣ�ۻ��Ŀ� y:Ӣ�ۻ���y-20
			bs[0] = new Bullet(this.x + 1 * xStep, this.y - yStep);
			// x:Ӣ�ۻ���x+3/4Ӣ�ۻ��Ŀ� y:Ӣ�ۻ���y-20
			bs[1] = new Bullet(this.x + 3 * xStep, this.y - yStep);
			fire -= 2; // ����һ��˫�������������ֵ��2
			return bs;
		} else { // ����
			Bullet[] bs = new Bullet[1]; // 1���ӵ�
			bs[0] = new Bullet(this.x + 2 * xStep, this.y - yStep); // x:Ӣ�ۻ���x+2/4Ӣ�ۻ��Ŀ� y:Ӣ�ۻ���y-20
			return bs;
		}
	}

	/** Ӣ�ۻ�������궯 x:����x y:����y */
	public void moveTo(int x, int y) {
		this.x = x - this.width / 2; // Ӣ�ۻ���x=����x-1/2Ӣ�ۻ��Ŀ�
		this.y = y - this.height / 2; // Ӣ�ۻ���y=����y-1/2Ӣ�ۻ��ĸ�
	}

	/** ��дoutOfBounds()����Ƿ�Խ�� */
	public boolean outOfBounds() {
		return false; // ����Խ��
	}

	/** Ӣ�ۻ����� */
	public void addLife() {
		life++; // ������1
	}

	/** ��ȡӢ�ۻ����� */
	public int getLife() {
		return life; // ��������
	}

	/** Ӣ�ۻ����� */
	public void subtractLife() {
		life--; // ������1
	}

	/** Ӣ�ۻ������� */
	public void addFire() {
		fire += 700; // ����ֵ��700
	}

	/** ����Ӣ�ۻ��Ļ���ֵ */
	public void downFire() {
		fire -= 1000;
	}

	/** ��ȡӢ�ۻ��Ļ��� */
	public int getFire() {
		return fire; // ��������
	}

	/** Ӣ�ۻ�����˵���ײ�㷨 this:Ӣ�ۻ� other:���� */
	public boolean hit(FlyingObject other) {
		int x1 = other.x - this.width / 2; // x1:���˵�x-1/2Ӣ�ۻ��Ŀ�
		int x2 = other.x + other.width + this.width / 2; // x2:���˵�x+���˵Ŀ�+1/2Ӣ�ۻ��Ŀ�
		int y1 = other.y - this.height / 2; // y1:���˵�y-1/2Ӣ�ۻ��ĸ�
		int y2 = other.y + other.height + this.height / 2; // y2:���˵�y+���˵ĸ�+1/2Ӣ�ۻ��ĸ�
		int x = this.x + this.width / 2; // x:Ӣ�ۻ���x+1/2Ӣ�ۻ��Ŀ�
		int y = this.y + this.height / 2; // y:Ӣ�ۻ���y+1/2Ӣ�ۻ��ĸ�

		return x >= x1 && x <= x2 && y >= y1 && y <= y2; // x��x1��x2֮�䣬���ң�y��y1��y2֮�䣬��Ϊײ����
	}
	
	/** Ӣ�ۻ�������ӵ�����ײ�㷨 this:Ӣ�ۻ� other:�����ӵ� */
	public boolean hit_x(Bullet_x other) {
		int x = this.x + this.width / 2;
		int y = this.y + this.height / 2;
		int x1 = other.x + 10;
		int y1 = other.y + 10;
		int x2 = other.x + other.width - 10;
		int y2 = other.y + other.height - 10;

		return x >= x1 && x <= x2 && y >= y1 && y <= y2; // x��x1��x2֮�䣬���ң�y��y1��y2֮�䣬��Ϊײ����
	}

}
