package 飞机大战;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;//图像缓冲区
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;//随机数
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;//框架
import javax.swing.JPanel;

//主窗口类
public class ShootGame extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3714181327161434445L;

	public static final int WIDTH = 530; // 窗口宽
	public static final int HEIGHT = 815; // 窗口高

	public static BufferedImage background; // 背景图
	public static BufferedImage start; // 启动图
	public static BufferedImage pause; // 暂停图
	public static BufferedImage gameover; // 游戏结束图
	public static BufferedImage airplane; // 敌机
	public static BufferedImage plane; // 小敌机
	public static BufferedImage bullet; // 子弹
	public static BufferedImage bullet_x; // 子弹
	public static BufferedImage bullet_s; // 子弹
	public static BufferedImage hero0; // 英雄机0
	public static BufferedImage hero1; // 英雄机1
	static { // 初始化静态图片，异常捕获
		try {
			// 获取路径、读入图片
			background = ImageIO.read(ShootGame.class.getResource("../img/背景1.jpg"));
			start = ImageIO.read(ShootGame.class.getResource("../img/飞机大战.png"));
			pause = ImageIO.read(ShootGame.class.getResource("../img/pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("../img/game over.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("../img/敌机.png"));
			plane = ImageIO.read(ShootGame.class.getResource("../img/小敌机.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("../img/子弹.png"));
			bullet_x = ImageIO.read(ShootGame.class.getResource("../img/子弹_x.png"));
			bullet_s = ImageIO.read(ShootGame.class.getResource("../img/子弹中.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("../img/英雄机1.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("../img/英雄机2.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Hero hero = new Hero(); // 一个英雄机
	private FlyingObject[] flyings = {}; // 一堆敌人(敌机+小蜜蜂+敌人子弹)
	private Bullet[] bullets = {}; // 一堆子弹

	public static final int START = 0; // 启动状态
	public static final int RUNNING = 1; // 运行状态
	public static final int PAUSE = 2; // 暂停状态
	public static final int GAME_OVER = 3; // 游戏结束状态
	private int state = START; // 当前状态(默认启动状态)

	/** 生成敌人(敌机+小敌机)对象 */
	public FlyingObject nextOne() {
		Random rand = new Random(); // 随机数对象
		int type = rand.nextInt(20); // 0到19之间的随机数
		if (type < 6) { // 0到5时，生成小敌机对象
			return new Plane();
		} else { // 6到19时，生成敌机对象
			return new Airplane();
		}
	}

	int flyIndex = 0; // 敌人入场计数

	/** 敌人(敌机+小敌机)入场 */
	public void enterAction() { // 10毫秒走一次
		flyIndex++; // 每10毫秒增1
		if (flyIndex % 50 == 0) { // 500(10*50)毫秒走一次
			FlyingObject obj = nextOne(); // 获取敌人(敌机+小敌机)对象
			int up = flyIndex / 600;
			if (up != 0) {
				if (obj instanceof Enemy)
					((Airplane) obj).upLife(up);
				if (obj instanceof Award)
					((Plane) obj).upLife(up);
			}
			flyings = Arrays.copyOf(flyings, flyings.length + 1); // 扩容
			flyings[flyings.length - 1] = obj; // 将敌人添加到最后一个元素上
		}
	}

	/** 飞行物(敌机+小敌机+敌机子弹+英雄机)走一步 */
	public void stepAction() { // 10毫秒走一次
		hero.step(); // 英雄机走一步
		for (int i = 0; i < flyings.length; i++) { // 遍历所有敌人（含子弹）
			flyings[i].step(); // 敌人走一步
		}
		for (int i = 0; i < bullets.length; i++) { // 遍历所有英雄机子弹
			bullets[i].step(); // 子弹走一步
		}
	}

	int shootIndex = 0; // 射击计数
	int shootIndex_x = 0; // 射击计数

	/** 子弹入场(英雄机发射子弹) */
	public void shootAction() { // 10毫秒走一次
		shootIndex++; // 每10毫秒增1
		if (shootIndex % 6 == 0) { // 60(10*6)毫秒走一次
			Bullet[] bs = hero.shoot(); // 获取子弹对象
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // 扩容(bs有几个元素就扩大几个容量)
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length); // 数组的追加
		}
	}

	/** 子弹入场(敌机发射子弹) */
	public void shootAction_x() { // 10毫秒走一次
		shootIndex_x++; // 每10毫秒增1
		if (shootIndex_x % 300 == 0) { // 3000(10*300)毫秒走一次
			for (int i = 0; i <= flyings.length; i++) {
				FlyingObject one = flyings[i];
				if (one instanceof Enemy) { // 若是敌人
					Airplane a = (Airplane) one; // 强转为敌人
					Bullet_x bs = a.shoot_x(); // 获取子弹对象
					flyings = Arrays.copyOf(flyings, flyings.length + 1); // 扩容
					flyings[flyings.length - 1] = bs; // 将敌人添加到最后一个元素上
				}
				if (one instanceof Award) { // 若是奖励
					Plane a = (Plane) one; // 强转为奖励
					Bullet_x bs = a.shoot_x(); // 获取子弹对象
					flyings = Arrays.copyOf(flyings, flyings.length + 1); // 扩容
					flyings[flyings.length - 1] = bs; // 将敌人添加到最后一个元素上
				}
			}
		}
	}

	/** 删除越界的飞行物(敌机+小敌机+子弹) */
	public void outOfBoundsAction() { // 10毫秒走一次
		int index = 0; // 1)不越界敌人数组下标 2)不越界敌人个数
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // 不越界敌人数组
		for (int i = 0; i < flyings.length; i++) { // 遍历所有敌人
			FlyingObject f = flyings[i]; // 获取每一个敌人
			if (!f.outOfBounds()) { // 不越界
				flyingLives[index] = f; // 将不越界敌人对象添加到不越界敌人数组中
				index++; // 1)不越界敌人数组下标增一 2)不越界敌人个数增一
			}
		}
		flyings = Arrays.copyOf(flyingLives, index); // 将不越界敌人数组复制到flyings中，flyings的长度为index(不越界敌人个数)

		index = 0; // 1)不越界子弹数组下标归零 2)不越界子弹个数归零
		Bullet[] bulletLives = new Bullet[bullets.length]; // 不越界子弹数组
		for (int i = 0; i < bullets.length; i++) { // 遍历所有子弹
			Bullet b = bullets[i]; // 获取每一个子弹
			if (!b.outOfBounds()) { // 不越界
				bulletLives[index] = b; // 将不越界子弹对象添加到不越界子弹数组中
				index++; // 1)不越界子弹数组下标增一 2)不越界子弹个数增一
			}
		}
		bullets = Arrays.copyOf(bulletLives, index); // 将不越界子弹数组复制到bullets中，bullets的长度为index(不越界子弹个数)

	}

	/** 子弹与所有敌人(敌机+小敌机)的碰撞 */
	public void bangAction() { // 10毫秒走一次
		for (int i = 0; i < bullets.length; i++) { // 遍历所有子弹
			Bullet b = bullets[i]; // 获取每一个子弹
			bang(b, i); // 实现一个子弹与所有敌人(敌机+小敌机)的碰撞
		}
	}

	int score = 0; // 玩家的得分
	int top = 0; // 最高得分

	/** 一个子弹与所有敌人(敌机+小敌机)的碰撞 */
	public void bang(Bullet b, int bulIndex) {
		int index = -1; // 被撞敌人的下标
		for (int i = 0; i < flyings.length; i++) { // 遍历所有敌人
			FlyingObject f = flyings[i]; // 获取每一个敌人
			if ((f instanceof Enemy) || (f instanceof Award)) {
				if (f.shootBy(b)) { // 撞上了
					index = i; // 记录被撞敌人的下标
					break; // 其余敌人不再参与比较了
				}
			}
		}
		if (index != -1) { // 撞上了
			FlyingObject one = flyings[index]; // 被撞的敌人
			if (one instanceof Enemy) { // 若是敌人
				Airplane e = (Airplane) one; // 强转为敌人
				e.subtractLife();
				if (e.getLife() == 0) {
					score += e.getScore(); // 玩家得分
					// 交换被撞的敌人与数组中的最后一个元素
					FlyingObject t = flyings[index];
					flyings[index] = flyings[flyings.length - 1];
					flyings[flyings.length - 1] = t;
					// 缩容(去掉最后一个元素，即被撞的敌人对象)
					flyings = Arrays.copyOf(flyings, flyings.length - 1);
				}
			}
			if (one instanceof Award) { // 若是奖励
				Plane a = (Plane) one; // 强转为奖励
				a.subtractLife();
				if (a.getLife() == 0) {
					int type = a.getType(); // 获取奖励类型
					switch (type) { // 根据奖励类型获取不同的奖励
					case Award.DOUBLE_FIRE: // 若奖励为火力
						hero.addFire(); // 则英雄机增火力
						break;
					case Award.LIFE: // 若奖励为命
						hero.addLife(); // 则英雄机增命
						break;
					}
					// 交换被撞的敌人与数组中的最后一个元素
					FlyingObject t = flyings[index];
					flyings[index] = flyings[flyings.length - 1];
					flyings[flyings.length - 1] = t;
					// 缩容(去掉最后一个元素，即被撞的敌人对象)
					flyings = Arrays.copyOf(flyings, flyings.length - 1);
				}
			}
			// 删除子弹
			Bullet x = bullets[bulIndex];
			bullets[bulIndex] = bullets[bullets.length - 1];
			bullets[bullets.length - 1] = x;
			bullets = Arrays.copyOf(bullets, bullets.length - 1);
		}
	}

	/** 英雄机与敌人(敌机+小敌机+敌机子弹)的碰撞 */
	public void hitAction() {// 10毫秒走一次
		for (int i = 0; i < flyings.length; i++) { // 遍历所有敌人
			FlyingObject f = flyings[i]; // 获取每一个敌人
			if ((f instanceof Enemy) || (f instanceof Award)) {
				if (hero.hit(f)) { // 撞上了
					hero.subtractLife(); // 英雄机减命
					hero.downFire(); // 英雄机火力值下降
					if (f instanceof Enemy) { // 若是大敌机
						Airplane e = (Airplane) f; // 强转为大敌机
						score += e.getScore(); // 玩家得分
					}
					// 交换被撞的敌人与数组中的最后一个元素
					FlyingObject t = flyings[i];
					flyings[i] = flyings[flyings.length - 1];
					flyings[flyings.length - 1] = t;
					// 缩容(去掉最后一个元素，即被撞的敌人对象)
					flyings = Arrays.copyOf(flyings, flyings.length - 1);
				}
			} else {
				Bullet_x fx = (Bullet_x) f;
				if (hero.hit_x(fx)) { // 撞上了
					hero.subtractLife(); // 英雄机减命
					// 交换被撞的敌人与数组中的最后一个元素
					FlyingObject t = flyings[i];
					flyings[i] = flyings[flyings.length - 1];
					flyings[flyings.length - 1] = t;
					// 缩容(去掉最后一个元素，即被撞的敌人对象)
					flyings = Arrays.copyOf(flyings, flyings.length - 1);
				}
			}
		}
	}

	/**
	 * 判断游戏结束
	 * 
	 * @throws IOException
	 */
	public void checkGameOverAction() throws IOException { // 10毫秒走一次
		if (hero.getLife() <= 0) { // 游戏结束了
			Score.outPut(score);
			state = GAME_OVER; // 修改当前状态为游戏结束状态
		}
	}

	/** 启动程序的执行 */
	public void action() {
		// 创建鼠标侦听器
		MouseAdapter l = new MouseAdapter() {
			/** 重写mouseMoved()鼠标移动事件 */
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) { // 运行状态下执行
					int x = e.getX(); // 获取鼠标的x坐标
					int y = e.getY(); // 获取鼠标的y坐标
					hero.moveTo(x, y); // 英雄机随着鼠标移动
				}
			}

			/** 重写mouseClicked()鼠标点击事件 */
			public void mouseClicked(MouseEvent e) {
				switch (state) { // 根据当前状态做不同的操作
				case START: // 启动状态时
					try {
						top = Score.inPut();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					state = RUNNING; // 修改为运行状态
					break;
				case GAME_OVER: // 游戏结束状态时
					score = 0; // 清理现场
					flyIndex = 0;
					hero = new Hero();
					flyings = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START; // 修改为启动状态
					break;
				}
			}

			/** 重写mouseExited()鼠标移出事件 */
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) { // 运行状态时
					state = PAUSE; // 修改为暂停状态
				}
			}

			/** 重写mouseEntered()鼠标移入事件 */
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) { // 暂停状态时
					state = RUNNING; // 修改为运行状态
				}
			}
		};
		this.addMouseListener(l); // 处理鼠标操作事件
		this.addMouseMotionListener(l); // 处理鼠标滑动事件

		Timer timer = new Timer(); // 创建定时器对象
		int intervel = 10; // 时间间隔(以毫秒为单位)
		timer.schedule(new TimerTask() {
			// 以规定时间间隔运行run方法
			public void run() { // 定时干的那个事--10毫秒走一次
				try {
					if (state == RUNNING) { // 运行状态时执行
						enterAction(); // 敌人(敌机+小敌机)入场
						stepAction(); // 飞行物(敌机+小敌机+英雄机子弹+英雄机)走一步
						shootAction(); // 子弹入场(英雄机发射子弹)
						shootAction_x();// 子弹入场(敌机发射子弹)
						outOfBoundsAction(); // 删除越界的飞行物(敌机+小敌机+所有子弹)
						bangAction(); // 子弹与敌人(敌机+小敌机)的碰撞
						hitAction(); // 英雄机与敌人(敌机+小敌机+敌机子弹)的碰撞
						checkGameOverAction(); // 判断游戏结束
					}
					repaint(); // 重画(调用paint()方法)
				} catch (Exception e) {
				}
			}
		}, intervel, intervel); // 定时计划
	}

	/** 重写paint() g:画笔 */
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null); // 画背景图
		paintHero(g); // 画英雄机对象
		paintFlyingObjects(g); // 画敌人(敌机+小敌机+敌机子弹)对象
		paintBullets(g); // 画英雄机子弹对象
		paintScoreAndLife(g); // 画分和画命
		paintState(g); // 画状态
	}

	/** 画英雄机对象 */
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null); // 画英雄机对象
	}

	/** 画敌人(敌机+小敌机+敌机子弹)对象 */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) { // 遍历所有敌人(敌机+小敌机+敌机子弹)
			FlyingObject f = flyings[i]; // 获取每一个敌人(敌机+小敌机+敌机子弹)
			g.drawImage(f.image, f.x, f.y, null); // 画敌人(敌机+小敌机+敌机子弹)对象
		}
	}

	/** 画英雄机子弹对象 */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) { // 遍历所有英雄机子弹
			Bullet b = bullets[i]; // 获取每一个英雄机子弹
			g.drawImage(b.image, b.x, b.y, null); // 画英雄机子弹对象
		}
	}

	/**
	 * 画分和画命
	 * 
	 * @throws IOException
	 */
	public void paintScoreAndLife(Graphics g) {
		g.setColor(new Color(0xFF0000)); // 设置颜色(纯红)
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24)); // 设置字体样式(字体:SANS_SERIF 样式:BOLD加粗 字号:24)
		g.drawString("TOP: " + top, 10, 25);
		g.drawString("SCORE: " + score, 10, 45);
		g.drawString("LIFE: " + hero.getLife(), 10, 65);
		g.drawString("FIRE: " + hero.getFire(), 10, 85);
	}

	/** 画状态 */
	public void paintState(Graphics g) {
		switch (state) { // 根据当前状态的不同画不同的图
		case START: // 启动状态时画启动图
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE: // 暂停状态时画暂停图
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER: // 游戏结束状态时画游戏结束图
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly"); // 创建窗口对象
		ShootGame game = new ShootGame(); // 创建面板对象
		frame.add(game); // 将面板添加到窗口中
		frame.setSize(WIDTH, HEIGHT); // 设置窗口大小
		frame.setAlwaysOnTop(true); // 设置总是在最上面
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口默认关闭操作(关闭窗口时退出程序)
		frame.setLocationRelativeTo(null); // 设置居中显示
		frame.setVisible(true); // 1)设置窗口可见 2)尽快调用paint()方法

		game.action(); // 启动程序的执行
	}
}