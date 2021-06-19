import pymssql
import matplotlib.pyplot as plt
# 登录界面


def log():
    dic = {"1", "2", "3", "4", "5", "0"}
    connect = pymssql.connect('LAPTOP-KPQREEGP\SQL2012',
                              'sa', '123456', 'universe')  # 服务器名，账户，密码，数据库名
    cursor = connect.cursor()

    cursor.execute("select name from users")
    users = cursor.fetchall()
    nameset = set()     # 建一个set集合用来存所有查询结果
    for i in users:
        nameset.add(i[0])

    print("准备登录，请输入用户名：")
    username = input()
    while username not in nameset:
        print("用户名错误或不存在，请重新输入用户名：")
        username = input()

    cursor.execute("select word from users where name = '"+username+"'")
    test = cursor.fetchall()
    pw = test[0][0]
    print("请输入密码：")
    password = input()
    while password != pw:
        print("密码错误，请重新输入：")
        password = input()

    connect.commit()    # 提交，不提交的话数据库会回滚
    cursor.close()      # 关闭游标
    connect.close()     # 关闭连接（如果不关闭，python会一直占用）

    connect = pymssql.connect('LAPTOP-KPQREEGP\SQL2012',
                              'sa', '123456', username)  # 服务器名，账户，密码，数据库名
    cursor = connect.cursor()
    print("登录成功")

    def log_1():
        # 查询所有卡片
        cursor.execute("select * from cards")
        cards = cursor.fetchall()
        nameset = set()     # 建一个set集合用来存所有查询结果
        for i in cards:
            nameset.add(i[0])
        print("您的卡片名有：")
        for i in nameset:
            print(i)
        return

    def log_2():
        # 余额一览
        cursor.execute("select * from cards")
        cards = cursor.fetchall()
        nameset = set()     # 建一个set集合用来存所有查询结果
        for i in cards:
            nameset.add(i[0])
        nameList = []
        valueList = []

        for i in nameset:
            cursor.execute("select value from cards where name = '"+i+"'")
            test = cursor.fetchall()
            nameList.append(i)
            valueList.append(test[0][0])
            print(i+":"+str(test[0][0]))
        print("查询完成")
        return

    def log_3():
        # 开卡
        cursor.execute("select * from cards")
        cards = cursor.fetchall()
        nameset = set()     # 建一个set集合用来存所有查询结果
        for i in cards:
            nameset.add(i[0])

        print("欢迎开卡，请输入卡名：")
        name = input()
        while name in nameset:
            print("该卡名已被您使用，请重新输入卡名：")
            name = input()
        cursor.execute("create table "+name+"(value int not null)")
        cursor.execute("insert into cards values ('" + name+"', 0)")
        connect.commit()
        print("开卡成功")
        return

    def log_4():
        # 删卡
        cursor.execute("select * from cards")
        cards = cursor.fetchall()
        nameset = set()     # 建一个set集合用来存所有查询结果
        for i in cards:
            nameset.add(i[0])

        print("正在准备删卡，请输入卡名：")
        name = input()
        while name not in nameset:
            print("卡名错误或不存在，请重新输入卡名：")
            name = input()
        cursor.execute("drop table "+name)
        cursor.execute("delete from cards where name = '"+name+"'")
        connect.commit()
        print("删卡成功")
        return

    def log_5():
        # 存取款
        dic1 = {"1", "2", "3", "0"}
        cursor.execute("select * from cards")
        cards = cursor.fetchall()
        nameset = set()     # 建一个set集合用来存所有查询结果
        for i in cards:
            nameset.add(i[0])

        print("准备存取款，请输入卡名：")
        name = input()
        while name not in nameset:
            print("卡名错误或不存在，请重新输入卡名：")
            name = input()

        ok = 1
        while ok != 0:
            print("请选择服务：")
            print("1.存款  2.取款  3.流水图  0.退出当前卡片")
            ok = input()
            while not(ok in dic1):
                print("输入格式错误，请重新输入：")
                print("1.存款  2.取款  3.流水图  0.退出当前卡片")
                ok = input()

            ok = int(ok)

            if ok == 1:
                # 存款
                print("请输入存款金额：")
                money = int(input())
                while money <= 0:
                    print("输入格式错误，请输入一个大于零的整数：")
                    money = int(input())

                cursor.execute("insert into "+name+" values ("+str(money)+")")
                cursor.execute("update cards set value = value+" +
                               str(money)+" where name = '"+name+"'")
                connect.commit()
                print("存款成功")
            elif ok == 2:
                # 取款
                cursor.execute(
                    "select value from cards where name = '"+name+"'")
                test = cursor.fetchall()
                yue = test[0][0]
                print("您卡片"+name+"中的余额为："+str(yue))
                print("请输入存款金额：")
                money = int(input())
                while money <= 0:
                    print("输入格式错误，请输入一个大于零的整数：")
                    money = int(input())

                money = -1*money
                cursor.execute("insert into "+name+" values ("+str(money)+")")
                cursor.execute("update cards set value = value+" +
                               str(money)+" where name = '"+name+"'")

                connect.commit()
                print("取款成功")
            elif ok == 3:
                # 流水图，
                cursor.execute("select value from "+name)
                values = cursor.fetchall()
                valueList = []
                for i in values:
                    valueList.append(i[0])
                count = range(1, len(values)+1)

                plt.plot(count, valueList)
                plt.xlabel("Count")  # 设置x，y轴的标签
                plt.ylabel("Change")
                plt.grid()
                ax = plt.gca()
                ax.spines['bottom'].set_position(('data', 0))
                plt.show()
                print("绘制完毕")
        print("已安全退出当前卡片")
        return

    ok = 1
    while ok != 0:
        print("请选择服务：")
        print("1.查询卡片信息  2.余额一览  3.开卡  4.删卡  5.存取款及流水  0.退出当前账户")
        ok = input()
        while not(ok in dic):
            print("输入格式错误，请重新输入：")
            print("1.查询卡片信息  2.余额一览  3.开卡  4.删卡  5.存取款及流水  0.退出当前账户")
            ok = input()

        ok = int(ok)

        if ok == 1:
            log_1()
        elif ok == 2:
            log_2()
        elif ok == 3:
            log_3()
        elif ok == 4:
            log_4()
        elif ok == 5:
            log_5()

    print("已安全退出账户")
    connect.commit()    # 提交，不提交的话数据库会回滚
    cursor.close()      # 关闭游标
    connect.close()     # 关闭连接（如果不关闭，python会一直占用）
    return
