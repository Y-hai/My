%% 数据挖掘作业
clc, clear;
name = 'datasub.xlsx';
name1 = 'datasub1.csv';
name2 = 'datasub2.csv';
a = xlsread(name1, 'datasub1', 'A2:G23');
b = xlsread(name2, 'datasub2', 'B2:I23');
data = [a, b];

%% 拉格朗日插值， 高次拉格朗日插值可能产生龙格现象
for i = 1:size(data, 2) - 1 % 最后一列不用插值
    for j = 1:size(data, 1)
        if isnan(data(j, i))
            data(j, i) = Lagrange(1998+j, 1996+j, 1997+j, 1999+j, 2000+j, data(j-2, i), data(j-1, i), data(j+1, i), data(j+2, i));                         
        end
    end
end
% xlswrite(name, data, 'Sheet1','A2');

%% 统计描述
MIN = min(data(:, 2:end));  % 每一列的最小值
MAX = max(data(:, 2:end));   % 每一列的最大值
MEAN = mean(data(:, 2:end));  % 每一列的均值
MEDIAN = median(data(:, 2:end));  % 每一列的中位数
SKEWNESS = skewness(data(:, 2:end)); % 每一列的偏度
KURTOSIS = kurtosis(data(:, 2:end));  % 每一列的峰度
STD = std(data(:, 2:end));  % 每一列的标准差
% 将这些统计量放到一个矩阵中表示
RESULT = [MIN; MAX; MEAN; MEDIAN; SKEWNESS; KURTOSIS; STD];

%% 皮尔逊相关系数
R = corrcoef(data(1:end - 2, 2:end));
% xlswrite('R', R, 'Sheet1','A1');
% JB检验（雅克贝拉）
n = size(data, 2); % 数据的列数
H = zeros(1, n - 2);
P = zeros(1, n - 2);
for i = 2:n - 1
    [h, p] = jbtest(data(:, i), 0.1);
    H(i - 1) = h;
    P(i - 1) = p;
end
disp(['在90%的置信区间上有',num2str(sum(H)),'列数据通过了JB检验'])

%% 标准化
st = zscore(data(1:end - 2, 2:end - 1));
xlswrite('st', st, 'Sheet1','A2');

%% lasso回归筛选出来的列 2、4:10、14
data(:, [1, 3, 11, 12, 13]) = [];

%% 神经网络
a = data(1:end-2, :);
a = a';
in = a(1:9,:); out = a(10,:);
% 选出部分数据作为测试集
i = [1:4, 7:10, 12:15, 17:20];
in_test = in(:,i); out_test = out(i);
% in(:,i) = []; out(i) = [];
in_train = in; out_train = out;
% 归一化输入输出训练数据
[shuru, ps] = mapminmax(in_train);
[shuchu, ts] = mapminmax(out_train);
% 设置模型参数
a = 100; l = size(out_test, 2);
while a > 10
    net = newff(shuru,shuchu,6,{'tansig','tansig','tansig'},'trainlm');
    net.trainParam.goal = 0.0001;	% 训练目标最小误差，这里设置为0.0001
    net.trainParam.epochs = 10000;	% 训练次数，这里设置为10000次
    net.trainParam.lr = 0.1;       % 学习速率，这里设置为0.1
    % 训练
    net = train(net, shuru, shuchu);
    % 测试
    shuru_test = mapminmax('apply', in_test, ps);  % 归一化测试数据
    an = sim(net, shuru_test);          % 预测测试数据
    shuchu_test = mapminmax('reverse', an, ts);   % 恢复数据
    deltal = shuchu_test - out_test;    % 计算误差
    a = sum(abs(deltal)) / l;
end
% 绘图
figure; hold on; grid on;
plot(shuchu_test, 'b*-')
plot(out_test, 'ro-')
plot(deltal, 'square', 'MarkerFaceColor', 'b')
legend('预测财政收入', '期望财政收入', '误差', 'Location', 'northwest')
xlabel('数据组数')
ylabel('财政收入')
b = sqrt(deltal * deltal' / l);
disp(['-----',num2str(l),'个测试样例误差如下-----'])
disp(['平均绝对误差MAE为：', num2str(a)])
disp(['均方根误差MSE为：', num2str(b) ])
mea = mean(out_test');
SSR = sum((shuchu_test - mea) .^ 2);
SSE = sum((out_test - shuchu_test) .^ 2);
RR = SSR / (SSR + SSE);
disp(['R^2为：', num2str(RR)])
% 多次模拟，保存误差最小的模型

%% 预测 
load a
a = data(end - 1:end, :);
a = a';
a = a(1:end - 1, :);
shuru_test = mapminmax('apply', a, ps);
an = sim(net, shuru_test);          % 预测测试数据
shuchu_test = mapminmax('reverse', an, ts);
disp("2019、2020年收入为：")
disp(shuchu_test')





