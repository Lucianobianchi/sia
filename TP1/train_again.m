config

net = set_lr(net, lr);

max_epochs += epochs;

tic;
while (current_error > test_error && try_count < max_tries)
    [net costs] = training(algorithm, net, train_set, train_expected, batch_size, 1, alfa, cost_interval, inc_steps, lr_increase, lr_decrease_factor);
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

    pause(1);
    figure(1);
    subplot(2,1,1);
    x = 1:length(train_cost);
    iterations = length(costs);
    plot(x / iterations, train_cost);
    title('Cost');
    xlabel('Epochs');
    ylabel('Cost');

    subplot(2,1,2);
    plot(test_rates);
    title('Test error rate');
    xlabel('Epochs');
    ylabel('Error rate');
    refreshdata();

    if (stop_early && epoch_count >= max_epochs)
        break;
    end
endwhile
toc;
