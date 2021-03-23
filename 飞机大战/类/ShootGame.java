package �ɻ���ս;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;//ͼ�񻺳���
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;//�����
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;//���
import javax.swing.JPanel;

//��������
public class ShootGame extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3714181327161434445L;

	public static final int WIDTH = 530; // ���ڿ�
	public static final int HEIGHT = 815; // ���ڸ�

	public static BufferedImage background; // ����ͼ
	public static BufferedImage start; // ����ͼ
	public static BufferedImage pause; // ��ͣͼ
	public static BufferedImage gameover; // ��Ϸ����ͼ
	public static BufferedImage airplane; // �л�
	public static BufferedImage plane; // С�л�
	public static BufferedImage bullet; // �ӵ�
	public static BufferedImage bullet_x; // �ӵ�
	public static BufferedImage bullet_s; // �ӵ�
	public static BufferedImage hero0; // Ӣ�ۻ�0
	public static BufferedImage hero1; // Ӣ�ۻ�1
	static { // ��ʼ����̬ͼƬ���쳣����
		try {
			// ��ȡ·��������ͼƬ
			background = ImageIO.read(ShootGame.class.getResource("../img/����1.jpg"));
			start = ImageIO.read(ShootGame.class.getResource("../img/�ɻ���ս.png"));
			pause = ImageIO.read(ShootGame.class.getResource("../img/pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("../img/game over.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("../img/�л�.png"));
			plane = ImageIO.read(ShootGame.class.getResource("../img/С�л�.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("../img/�ӵ�.png"));
			bullet_x = ImageIO.read(ShootGame.class.getResource("../img/�ӵ�_x.png"));
			bullet_s = ImageIO.read(ShootGame.class.getResource("../img/�ӵ���.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("../img/Ӣ�ۻ�1.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("../img/Ӣ�ۻ�2.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Hero hero = new Hero(); // һ��Ӣ�ۻ�
	private FlyingObject[] flyings = {}; // һ�ѵ���(�л�+С�۷�+�����ӵ�)
	private Bullet[] bullets = {}; // һ���ӵ�

	public static final int START = 0; // ����״̬
	public static final int RUNNING = 1; // ����״̬
	public static final int PAUSE = 2; // ��ͣ״̬
	public static final int GAME_OVER = 3; // ��Ϸ����״̬
	private int state = START; // ��ǰ״̬(Ĭ������״̬)

	/** ���ɵ���(�л�+С�л�)���� */
	public FlyingObject nextOne() {
		Random rand = new Random(); // ���������
		int type = rand.nextInt(20); // 0��19֮��������
		if (type < 6) { // 0��5ʱ������С�л�����
			return new Plane();
		} else { // 6��19ʱ�����ɵл�����
			return new Airplane();
		}
	}

	int flyIndex = 0; // �����볡����

	/** ����(�л�+С�л�)�볡 */
	public void enterAction() { // 10������һ��
		flyIndex++; // ÿ10������1
		if (flyIndex % 50 == 0) { // 500(10*50)������һ��
			FlyingObject obj = nextOne(); // ��ȡ����(�л�+С�л�)����
			int up = flyIndex / 600;
			if (up != 0) {
				if (obj instanceof Enemy)
					((Airplane) obj).upLife(up);
				if (obj instanceof Award)
					((Plane) obj).upLife(up);
			}
			flyings = Arrays.copyOf(flyings, flyings.length + 1); // ����
			flyings[flyings.length - 1] = obj; // ��������ӵ����һ��Ԫ����
		}
	}

	/** ������(�л�+С�л�+�л��ӵ�+Ӣ�ۻ�)��һ�� */
	public void stepAction() { // 10������һ��
		hero.step(); // Ӣ�ۻ���һ��
		for (int i = 0; i < flyings.length; i++) { // �������е��ˣ����ӵ���
			flyings[i].step(); // ������һ��
		}
		for (int i = 0; i < bullets.length; i++) { // ��������Ӣ�ۻ��ӵ�
			bullets[i].step(); // �ӵ���һ��
		}
	}

	int shootIndex = 0; // �������
	int shootIndex_x = 0; // �������

	/** �ӵ��볡(Ӣ�ۻ������ӵ�) */
	public void shootAction() { // 10������һ��
		shootIndex++; // ÿ10������1
		if (shootIndex % 6 == 0) { // 60(10*6)������һ��
			Bullet[] bs = hero.shoot(); // ��ȡ�ӵ�����
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // ����(bs�м���Ԫ�ؾ����󼸸�����)
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length); // �����׷��
		}
	}

	/** �ӵ��볡(�л������ӵ�) */
	public void shootAction_x() { // 10������һ��
		shootIndex_x++; // ÿ10������1
		if (shootIndex_x % 300 == 0) { // 3000(10*300)������һ��
			for (int i = 0; i <= flyings.length; i++) {
				FlyingObject one = flyings[i];
				if (one instanceof Enemy) { // ���ǵ���
					Airplane a = (Airplane) one; // ǿתΪ����
					Bullet_x bs = a.shoot_x(); // ��ȡ�ӵ�����
					flyings = Arrays.copyOf(flyings, flyings.length + 1); // ����
					flyings[flyings.length - 1] = bs; // ��������ӵ����һ��Ԫ����
				}
				if (one instanceof Award) { // ���ǽ���
					Plane a = (Plane) one; // ǿתΪ����
					Bullet_x bs = a.shoot_x(); // ��ȡ�ӵ�����
					flyings = Arrays.copyOf(flyings, flyings.length + 1); // ����
					flyings[flyings.length - 1] = bs; // ��������ӵ����һ��Ԫ����
				}
			}
		}
	}

	/** ɾ��Խ��ķ�����(�л�+С�л�+�ӵ�) */
	public void outOfBoundsAction() { // 10������һ��
		int index = 0; // 1)��Խ����������±� 2)��Խ����˸���
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // ��Խ���������
		for (int i = 0; i < flyings.length; i++) { // �������е���
			FlyingObject f = flyings[i]; // ��ȡÿһ������
			if (!f.outOfBounds()) { // ��Խ��
				flyingLives[index] = f; // ����Խ����˶�����ӵ���Խ�����������
				index++; // 1)��Խ����������±���һ 2)��Խ����˸�����һ
			}
		}
		flyings = Arrays.copyOf(flyingLives, index); // ����Խ��������鸴�Ƶ�flyings�У�flyings�ĳ���Ϊindex(��Խ����˸���)

		index = 0; // 1)��Խ���ӵ������±���� 2)��Խ���ӵ���������
		Bullet[] bulletLives = new Bullet[bullets.length]; // ��Խ���ӵ�����
		for (int i = 0; i < bullets.length; i++) { // ���������ӵ�
			Bullet b = bullets[i]; // ��ȡÿһ���ӵ�
			if (!b.outOfBounds()) { // ��Խ��
				bulletLives[index] = b; // ����Խ���ӵ�������ӵ���Խ���ӵ�������
				index++; // 1)��Խ���ӵ������±���һ 2)��Խ���ӵ�������һ
			}
		}
		bullets = Arrays.copyOf(bulletLives, index); // ����Խ���ӵ����鸴�Ƶ�bullets�У�bullets�ĳ���Ϊindex(��Խ���ӵ�����)

	}

	/** �ӵ������е���(�л�+С�л�)����ײ */
	public void bangAction() { // 10������һ��
		for (int i = 0; i < bullets.length; i++) { // ���������ӵ�
			Bullet b = bullets[i]; // ��ȡÿһ���ӵ�
			bang(b, i); // ʵ��һ���ӵ������е���(�л�+С�л�)����ײ
		}
	}

	int score = 0; // ��ҵĵ÷�
	int top = 0; // ��ߵ÷�

	/** һ���ӵ������е���(�л�+С�л�)����ײ */
	public void bang(Bullet b, int bulIndex) {
		int index = -1; // ��ײ���˵��±�
		for (int i = 0; i < flyings.length; i++) { // �������е���
			FlyingObject f = flyings[i]; // ��ȡÿһ������
			if ((f instanceof Enemy) || (f instanceof Award)) {
				if (f.shootBy(b)) { // ײ����
					index = i; // ��¼��ײ���˵��±�
					break; // ������˲��ٲ���Ƚ���
				}
			}
		}
		if (index != -1) { // ײ����
			FlyingObject one = flyings[index]; // ��ײ�ĵ���
			if (one instanceof Enemy) { // ���ǵ���
				Airplane e = (Airplane) one; // ǿתΪ����
				e.subtractLife();
				if (e.getLife() == 0) {
					score += e.getScore(); // ��ҵ÷�
					// ������ײ�ĵ����������е����һ��Ԫ��
					FlyingObject t = flyings[index];
					flyings[index] = flyings[flyings.length - 1];
					flyings[flyings.length - 1] = t;
					// ����(ȥ�����һ��Ԫ�أ�����ײ�ĵ��˶���)
					flyings = Arrays.copyOf(flyings, flyings.length - 1);
				}
			}
			if (one instanceof Award) { // ���ǽ���
				Plane a = (Plane) one; // ǿתΪ����
				a.subtractLife();
				if (a.getLife() == 0) {
					int type = a.getType(); // ��ȡ��������
					switch (type) { // ���ݽ������ͻ�ȡ��ͬ�Ľ���
					case Award.DOUBLE_FIRE: // ������Ϊ����
						hero.addFire(); // ��Ӣ�ۻ�������
						break;
					case Award.LIFE: // ������Ϊ��
						hero.addLife(); // ��Ӣ�ۻ�����
						break;
					}
					// ������ײ�ĵ����������е����һ��Ԫ��
					FlyingObject t = flyings[index];
					flyings[index] = flyings[flyings.length - 1];
					flyings[flyings.length - 1] = t;
					// ����(ȥ�����һ��Ԫ�أ�����ײ�ĵ��˶���)
					flyings = Arrays.copyOf(flyings, flyings.length - 1);
				}
			}
			// ɾ���ӵ�
			Bullet x = bullets[bulIndex];
			bullets[bulIndex] = bullets[bullets.length - 1];
			bullets[bullets.length - 1] = x;
			bullets = Arrays.copyOf(bullets, bullets.length - 1);
		}
	}

	/** Ӣ�ۻ������(�л�+С�л�+�л��ӵ�)����ײ */
	public void hitAction() {// 10������һ��
		for (int i = 0; i < flyings.length; i++) { // �������е���
			FlyingObject f = flyings[i]; // ��ȡÿһ������
			if ((f instanceof Enemy) || (f instanceof Award)) {
				if (hero.hit(f)) { // ײ����
					hero.subtractLife(); // Ӣ�ۻ�����
					hero.downFire(); // Ӣ�ۻ�����ֵ�½�
					if (f instanceof Enemy) { // ���Ǵ�л�
						Airplane e = (Airplane) f; // ǿתΪ��л�
						score += e.getScore(); // ��ҵ÷�
					}
					// ������ײ�ĵ����������е����һ��Ԫ��
					FlyingObject t = flyings[i];
					flyings[i] = flyings[flyings.length - 1];
					flyings[flyings.length - 1] = t;
					// ����(ȥ�����һ��Ԫ�أ�����ײ�ĵ��˶���)
					flyings = Arrays.copyOf(flyings, flyings.length - 1);
				}
			} else {
				Bullet_x fx = (Bullet_x) f;
				if (hero.hit_x(fx)) { // ײ����
					hero.subtractLife(); // Ӣ�ۻ�����
					// ������ײ�ĵ����������е����һ��Ԫ��
					FlyingObject t = flyings[i];
					flyings[i] = flyings[flyings.length - 1];
					flyings[flyings.length - 1] = t;
					// ����(ȥ�����һ��Ԫ�أ�����ײ�ĵ��˶���)
					flyings = Arrays.copyOf(flyings, flyings.length - 1);
				}
			}
		}
	}

	/**
	 * �ж���Ϸ����
	 * 
	 * @throws IOException
	 */
	public void checkGameOverAction() throws IOException { // 10������һ��
		if (hero.getLife() <= 0) { // ��Ϸ������
			Score.outPut(score);
			state = GAME_OVER; // �޸ĵ�ǰ״̬Ϊ��Ϸ����״̬
		}
	}

	/** ���������ִ�� */
	public void action() {
		// �������������
		MouseAdapter l = new MouseAdapter() {
			/** ��дmouseMoved()����ƶ��¼� */
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) { // ����״̬��ִ��
					int x = e.getX(); // ��ȡ����x����
					int y = e.getY(); // ��ȡ����y����
					hero.moveTo(x, y); // Ӣ�ۻ���������ƶ�
				}
			}

			/** ��дmouseClicked()������¼� */
			public void mouseClicked(MouseEvent e) {
				switch (state) { // ���ݵ�ǰ״̬����ͬ�Ĳ���
				case START: // ����״̬ʱ
					try {
						top = Score.inPut();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					state = RUNNING; // �޸�Ϊ����״̬
					break;
				case GAME_OVER: // ��Ϸ����״̬ʱ
					score = 0; // �����ֳ�
					flyIndex = 0;
					hero = new Hero();
					flyings = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START; // �޸�Ϊ����״̬
					break;
				}
			}

			/** ��дmouseExited()����Ƴ��¼� */
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) { // ����״̬ʱ
					state = PAUSE; // �޸�Ϊ��ͣ״̬
				}
			}

			/** ��дmouseEntered()��������¼� */
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) { // ��ͣ״̬ʱ
					state = RUNNING; // �޸�Ϊ����״̬
				}
			}
		};
		this.addMouseListener(l); // �����������¼�
		this.addMouseMotionListener(l); // ������껬���¼�

		Timer timer = new Timer(); // ������ʱ������
		int intervel = 10; // ʱ����(�Ժ���Ϊ��λ)
		timer.schedule(new TimerTask() {
			// �Թ涨ʱ��������run����
			public void run() { // ��ʱ�ɵ��Ǹ���--10������һ��
				try {
					if (state == RUNNING) { // ����״̬ʱִ��
						enterAction(); // ����(�л�+С�л�)�볡
						stepAction(); // ������(�л�+С�л�+Ӣ�ۻ��ӵ�+Ӣ�ۻ�)��һ��
						shootAction(); // �ӵ��볡(Ӣ�ۻ������ӵ�)
						shootAction_x();// �ӵ��볡(�л������ӵ�)
						outOfBoundsAction(); // ɾ��Խ��ķ�����(�л�+С�л�+�����ӵ�)
						bangAction(); // �ӵ������(�л�+С�л�)����ײ
						hitAction(); // Ӣ�ۻ������(�л�+С�л�+�л��ӵ�)����ײ
						checkGameOverAction(); // �ж���Ϸ����
					}
					repaint(); // �ػ�(����paint()����)
				} catch (Exception e) {
				}
			}
		}, intervel, intervel); // ��ʱ�ƻ�
	}

	/** ��дpaint() g:���� */
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null); // ������ͼ
		paintHero(g); // ��Ӣ�ۻ�����
		paintFlyingObjects(g); // ������(�л�+С�л�+�л��ӵ�)����
		paintBullets(g); // ��Ӣ�ۻ��ӵ�����
		paintScoreAndLife(g); // ���ֺͻ���
		paintState(g); // ��״̬
	}

	/** ��Ӣ�ۻ����� */
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null); // ��Ӣ�ۻ�����
	}

	/** ������(�л�+С�л�+�л��ӵ�)���� */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) { // �������е���(�л�+С�л�+�л��ӵ�)
			FlyingObject f = flyings[i]; // ��ȡÿһ������(�л�+С�л�+�л��ӵ�)
			g.drawImage(f.image, f.x, f.y, null); // ������(�л�+С�л�+�л��ӵ�)����
		}
	}

	/** ��Ӣ�ۻ��ӵ����� */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) { // ��������Ӣ�ۻ��ӵ�
			Bullet b = bullets[i]; // ��ȡÿһ��Ӣ�ۻ��ӵ�
			g.drawImage(b.image, b.x, b.y, null); // ��Ӣ�ۻ��ӵ�����
		}
	}

	/**
	 * ���ֺͻ���
	 * 
	 * @throws IOException
	 */
	public void paintScoreAndLife(Graphics g) {
		g.setColor(new Color(0xFF0000)); // ������ɫ(����)
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24)); // ����������ʽ(����:SANS_SERIF ��ʽ:BOLD�Ӵ� �ֺ�:24)
		g.drawString("TOP: " + top, 10, 25);
		g.drawString("SCORE: " + score, 10, 45);
		g.drawString("LIFE: " + hero.getLife(), 10, 65);
		g.drawString("FIRE: " + hero.getFire(), 10, 85);
	}

	/** ��״̬ */
	public void paintState(Graphics g) {
		switch (state) { // ���ݵ�ǰ״̬�Ĳ�ͬ����ͬ��ͼ
		case START: // ����״̬ʱ������ͼ
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE: // ��ͣ״̬ʱ����ͣͼ
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER: // ��Ϸ����״̬ʱ����Ϸ����ͼ
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly"); // �������ڶ���
		ShootGame game = new ShootGame(); // ����������
		frame.add(game); // �������ӵ�������
		frame.setSize(WIDTH, HEIGHT); // ���ô��ڴ�С
		frame.setAlwaysOnTop(true); // ����������������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���ô���Ĭ�Ϲرղ���(�رմ���ʱ�˳�����)
		frame.setLocationRelativeTo(null); // ���þ�����ʾ
		frame.setVisible(true); // 1)���ô��ڿɼ� 2)�������paint()����

		game.action(); // ���������ִ��
	}
}