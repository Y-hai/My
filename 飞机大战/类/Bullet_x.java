package �ɻ���ս;

public class Bullet_x extends FlyingObject {
	private int speed = 3; //�ƶ����ٶ�
	/** ���췽�� �ӵ��ĳ�ʼ�������ŵл���*/
	public Bullet_x(int x, int y) {
		image = ShootGame.bullet_x; //ͼƬ
		width = image.getWidth();   //��
		height = image.getHeight(); //��
		this.x = x; //x:��л�
		this.y = y; //y:��л�
	}

	/** ��дstep()�߲� */
	public void step(){
		y+=speed; //y+(����)
	}
	
	/** ��дoutOfBounds()����Ƿ�Խ�� */
	public boolean outOfBounds(){
		return this.y>=ShootGame.HEIGHT; //�ӵ���y>=���ڵĸߣ���ΪԽ����
	}
}
