zpts = activate(net, pts);
subplot(2,1,2);
x = inputs(:,1);
y = inputs(:, 2);
z = outputs;

plot3(map(pts(:,1), [min(x)], [max(x)]), map(pts(:,2), [min(y)], [max(y)]), map(zpts, [min(z)], [max(z)]), '.');
title('Neural net terrain');

subplot(2,1,1);
plot3(x, y, z, '.');
title('Original terrain');
