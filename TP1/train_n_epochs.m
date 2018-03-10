%% train_iter: trains the net n times with the given input_training_set and expected_set
%% return the trained net and a vector of costs as second argument
function [net costs] = train_n_epochs(net, input_training_set, expected_set, n)
    costs = zeros(1, n + 1);
    for i = 1:n
        costs(i) = cost(net, input_training_set, expected_set);
        net = train(net, input_training_set, expected_set);
    end
    costs(end) = cost(net, input_training_set, expected_set);
endfunction
