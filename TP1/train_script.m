[net, costs] = train_momentum(net, train_set, train_expected, 25, 0.9);
tolerance = 0.1;
subplot(2, 1, 1);
plot(costs);
title('Costs during training');
xlabel('Iteration');
ylabel('Cost');
printf('train error rate with tolerance %d: %d\n', tolerance, error_rate(net, train_set, train_expected, tolerance));
printf('test error rate with tolerance %d: %d\n', tolerance, error_rate(net, test_set, test_expected, tolerance));

test_results = activate(net, test_set);

subplot(2, 1, 2);
stem(abs(test_results - test_expected), 'r');
title('Test error');
xlabel('Pattern');
ylabel('Error');
