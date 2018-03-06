function output = activate(net, input_pattern)
    ipl = length(input_pattern);
    inl = length(net.weights) - 1;
    if (ipl != inl)
        error('@network/get: Input pattern length (%d) and input neurons length (%d) do not match', ipl, inl);
    end

    input_pattern = [-1 input_pattern]; % revisar performance de crear nuevo arreglo
    output = net.f_activation(net.weights * input_pattern');
endfunction
