function net = train(net, input_pattern_set, expected_set)
    r = rows(input_pattern_set);
    l = length(expected_set);
    if (r != l)
        error('@network/train: Input pattern set rows (%d) do not match expected set length (%d)', r, n);
    end

    for i = 1:r
        input_pattern = input_pattern_set(i, :); % ojo con la eficiencia, copia la fila
        expected = expected_set(i);
        net = refine(net, input_pattern, expected);
    end
endfunction
