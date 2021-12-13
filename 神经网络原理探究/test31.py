import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.linear_model import Lasso

df = pd.read_excel("R.xls")
data = df.values
plt.rcParams['font.sans-serif'] = ['SimHei']  # 加入一行，解决中文不显示问题，对应字号选择如下图
plt.rcParams['axes.unicode_minus'] = False  # 解决负号不显示问题
plt.figure(figsize=(10, 10))
sns.heatmap(data, annot=True, vmax=1, square=True, cmap="Blues")
plt.title('相关性热力图')
plt.show()
plt.close


data = pd.read_excel("datasub.xls")  # 读取数据
lasso = Lasso(1000)  # 调用Lasso()函数， 设置入的值为1000
lasso. fit(data.iloc[:, 1:14], data['y'])
print('相关系数为: ', np.round(lasso.coef_, 5))  # 输出结果，保留五位小数
print('相关系数非零个数为: ', np.sum(lasso.coef_ != 0))  # 计算相关系数非零的个数

mask = (lasso.coef_ != 0)  # 返回一个相关系数是否为零的布尔数组
print('相关系数是否非零: ', mask)
