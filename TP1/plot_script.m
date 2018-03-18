zpts = activate(net, pts);
subplot(2,1,2);
plot3(pts(:,1), pts(:,2), zpts, '.');
title('Neural net terrain');

subplot(2,1,1);
plot3(normalized_inputs(:,1), normalized_inputs(:,2), normalized_outputs, '.');
title('Original terrain');
