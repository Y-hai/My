import sign_up
import log_in

print("—————————————————————————————————————————————————————————————————————————————————————————")
print("                                 欢迎使用ATM自动柜员机")
print("—————————————————————————————————————————————————————————————————————————————————————————")

ok = 1
dic = {"1", "2", "3", "4", "0"}

while ok != 0:
    print("请选择服务：")
    print("1.注册  2.更改账户密码  3.注销账户  4.登录  0.退出系统")
    ok = input()
    while ok not in dic:
        print("输入格式错误，请重新输入：")
        print("1.注册  2.更改账户密码  3.注销账户  4.登录  0.退出系统")
        ok = input()

    ok = int(ok)

    if ok == 1:
        sign_up.sign()
    elif ok == 2:
        sign_up.Reset_Password()
    elif ok == 3:
        sign_up.Delete_Account()
    elif ok == 4:
        log_in.log()

print("End")
