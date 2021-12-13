function y = Lagrange(x, x1, x2, x3, x4, y1, y2, y3, y4)
% 构造三次拉格朗日插值多项式
a1 = (x-x1)*(x-x2)*(x-x3)/(x4-x1)/(x4-x2)/(x4-x3)*y4;
a2 = (x-x1)*(x-x2)*(x-x4)/(x3-x1)/(x3-x2)/(x3-x4)*y3;
a3 = (x-x1)*(x-x3)*(x-x4)/(x2-x1)/(x2-x3)/(x2-x4)*y2;
a4 = (x-x2)*(x-x3)*(x-x4)/(x1-x2)/(x1-x3)/(x1-x4)*y1;
y = a1+a2+a3+a4;
end

