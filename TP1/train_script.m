tic
switch algorithm
    case 'vanilla'
        [net, costs] = train(net, train_set, train_expected, batch_size, epochs);
    case 'momentum'
        [net, costs] = train_momentum(net, train_set, train_expected, batch_size, epochs, alfa);
    case 'adaptive'
        [net, costs] = train_adaptive(net, train_set, train_expected, batch_size, epochs, lr_increase, lr_decrease_factor);
    otherwise
        error('No such algorithm %s', algorithm)
end
toc

subplot(2, 1, 1);
plot(costs);
title('Costs during training');
xlabel('Iteration');
ylabel('Cost');

printf('cost: %d\n', cost(net, train_set, train_expected));
printf('train error rate with tolerance %d: %d\n', tolerance, error_rate(net, train_set, train_expected, tolerance));
printf('test error rate with tolerance %d: %d\n', tolerance, error_rate(net, test_set, test_expected, tolerance));

test_results = activate(net, test_set);
errors = abs(test_results - test_expected);
sorted_errors = sort(errors, 'descend');
bad_errors = sorted_errors(find(sorted_errors > tolerance));
tolerable_errors = sorted_errors(find(sorted_errors <= tolerance));

[_, toperrors_idxs] = ismember(sorted_errors(1:n_toperrors), errors);
pts_toperrors = test_set(toperrors_idxs, :);
pts_toperrors(:, 3) = test_expected(toperrors_idxs);
pts_toperrors(:, 4) = test_results(toperrors_idxs);
pts_toperrors(:, 5) = sorted_errors(1:n_toperrors);

disp('      X           Y      expected     actual    error');
disp(pts_toperrors);

subplot(2, 1, 2);
stem(bad_errors, 'r');
hold on
stem(1+length(bad_errors)+(1:length(tolerable_errors)), tolerable_errors, 'g');
hold off
title('Test error');
xlabel('Pattern');
ylabel('Error');
