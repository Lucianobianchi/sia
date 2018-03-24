config
init_script

current_error = 100;
last_cost = cost(net, train_set, train_expected);
train_cost = [last_cost];
test_cost = [cost(net, test_set, test_expected)];
test_rates = [];
epoch_count = 0;
try_count = 0;
tic;
while (current_error > test_error && try_count < max_tries)
    [net costs] = training(algorithm, net, train_set, train_expected, batch_size, 1, alfa, lr_increase, lr_decrease_factor);
    train_cost = [train_cost costs];
    test_cost = [test_cost cost(net, test_set, test_expected)];
    current_cost = costs(end);
    current_error = error_rate(net, test_set, test_expected, tolerance);
    
    if (last_cost - current_cost < min_cost_change)
        try_count++;
    else
        try_count = 0;
    end
    
    test_rates = [test_rates current_error];
    last_cost = current_cost;
    epoch_count++;
    epoch_count
    current_error
    last_cost
    fflush(stdout);
endwhile
toc;

figure(1);
subplot(2,1,1);
x = 1:length(train_cost);
iterations = length(costs);
plot(x / iterations, train_cost, 0:(length(test_cost)-1), test_cost);
title('Cost');
xlabel('Epochs');
ylabel('Cost');
legend('Train', 'Test');

subplot(2,1,2);
plot(test_rates);
title('Test error rate');
xlabel('Epochs');
ylabel('Error rate');

figure(2);
subplot(1,1,1);
x = 1:length(train_cost);
iterations = length(costs);
plot(x / iterations, train_cost, 0:(length(test_cost)-1), test_cost);
title('Cost');
xlabel('Epochs');
ylabel('Cost');
legend('Train', 'Test');
axis([0 20]);
