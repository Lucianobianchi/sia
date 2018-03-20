config
init_script

function [net, costs] = training(algo_name, net, train_set, train_expected, batch_size, epochs, alfa, lr_increase, lr_decrease_factor)
    switch algo_name
        case 'vanilla'
            [net, costs] = train(net, train_set, train_expected, batch_size, epochs);
        case 'momentum'
            [net, costs] = train_momentum(net, train_set, train_expected, batch_size, epochs, alfa);
        case 'adaptive'
            [net, costs] = train_adaptive(net, train_set, train_expected, batch_size, epochs, lr_increase, lr_decrease_factor);
        otherwise
            error('No such algorithm %s', algorithm)
    end
endfunction

test_error = 100;
train_rates = [];
test_rates = [];
epoch_count = 0;
plot_count = 1;
while (test_error > 10)
    [net costs] = training(algorithm, net, train_set, train_expected, batch_size, 1, alfa, lr_increase, lr_decrease_factor);
    train_error = error_rate(net, train_set, train_expected, tolerance);
    test_error = error_rate(net, test_set, test_expected, tolerance);
    test_rates = [test_rates test_error];
    train_rates = [train_rates train_error];
    epoch_count++;
    epoch_count
    test_error
    fflush(stdout);
endwhile

plot_layer_activations(net, get(net, 'weights'), test_set)


% plot(test_rates);
% title('Error rates during training');
% xlabel('Epochs');
% ylabel('Error rate');

% hold on
% plot(train_rates);
% hold off
