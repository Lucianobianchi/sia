function net = network(n_inputs, lr) 
    net.f_activation = @sign;
    net.df_activation = @(gh) 1;
    net.weights = rand(1, n_inputs + 1) * 2 - 1;
    net.lr = lr;
    net = class(net, 'network');
endfunction
