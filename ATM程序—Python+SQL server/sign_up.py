import pymssql


def sign():
    # 注册
    connect = pymssql.connect('LAPTOP-KPQREEGP\SQL2012',
                              'sa', '123456', 'universe')  # 服务器名，账户，密码，数据库名
    cursor = connect.cursor()

    cursor.execute("select name from users")
    users = cursor.fetchall()
    nameset = set()     # 建一个set集合用来存所有查询结果
    for i in users:
        nameset.add(i[0])

    print("欢迎注册账户，请输入用户名：")
    username = input()
    while username in nameset:
        print("该用户名已被注册，请重新输入用户名：")
        username = input()

    print("请输入密码：")
    password1 = input()
    print("请确认密码：")
    password2 = input()
    while password1 != password2:
        print("两次密码不同，请重新输入：")
        print("请输入密码：")
        password1 = input()
        print("请确认密码：")
        password2 = input()

    connect.autocommit(True)  # 指令立即执行，无需等待conn.commit()
    cursor.execute("create database "+username +
                   " on(name = "+username+",filename = 'E:\Python\ATM\\"+username+".mdf')")
    connect.autocommit(False)  # 指令关闭立即执行，以后还是等待conn.commit()时再统一执行
    cursor.execute("insert into users values ('"+username+"','"+password2+"')")
    cursor.execute("use "+username +
                   " create table cards(name varchar(20) primary key,value int not null)")

    print("账户创建成功。")
    connect.commit()    # 提交，不提交的话数据库会回滚
    cursor.close()      # 关闭游标
    connect.close()     # 关闭连接（如果不关闭，python会一直占用）

    return


def Reset_Password():
    # 更新密码
    connect = pymssql.connect('LAPTOP-KPQREEGP\SQL2012',
                              'sa', '123456', 'universe')  # 服务器名，账户，密码，数据库名
    cursor = connect.cursor()   # 创建指针

    cursor.execute("select * from users")
    users = cursor.fetchall()
    nameset = set()     # 建一个set集合用来存所有查询结果
    wordset = set()
    for i in users:
        nameset.add(i[0])
        wordset.add(i[1])

    print("准备更新密码，请输入用户名：")
    username = input()
    while username not in nameset:
        print("用户名错误或不存在，请重新输入用户名：")
        username = input()

    cursor.execute("select word from users where name = '"+username+"'")
    test = cursor.fetchall()
    pw = test[0][0]
    print("请输入原始密码：")
    password = input()
    while password != pw:
        print("密码错误，请重新输入：")
        password = input()

    print("请输入新密码：")
    password1 = input()
    print("请确认新密码：")
    password2 = input()
    while password1 != password2:
        print("两次密码不同，请重新输入：")
        print("请输入新密码：")
        password1 = input()
        print("请确认新密码：")
        password2 = input()

    cursor.execute("update users set word = '"+password1 +
                   "' where name = '"+username+"'")
    print("密码更改成功。")

    connect.commit()    # 提交，不提交的话数据库会回滚
    cursor.close()      # 关闭游标
    connect.close()     # 关闭连接（如果不关闭，python会一直占用）
    return


def Delete_Account():
    # 删除账户
    connect = pymssql.connect('LAPTOP-KPQREEGP\SQL2012',
                              'sa', '123456', 'universe')  # 服务器名，账户，密码，数据库名
    cursor = connect.cursor()

    cursor.execute("select * from users")
    users = cursor.fetchall()
    nameset = set()     # 建一个set集合用来存所有查询结果
    wordset = set()
    for i in users:
        nameset.add(i[0])
        wordset.add(i[1])

    print("准备注销账户，请输入用户名：")
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

    connect.autocommit(True)
    cursor.execute("drop database "+username)
    connect.autocommit(False)
    cursor.execute("delete from users where name = '"+username+"'")
    print("账户注销成功。")

    connect.commit()    # 提交，不提交的话数据库会回滚
    cursor.close()      # 关闭游标
    connect.close()     # 关闭连接（如果不关闭，python会一直占用）
    return
