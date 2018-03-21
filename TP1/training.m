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
