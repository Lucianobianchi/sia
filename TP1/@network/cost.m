function err = cost(net, input_pattern_set, expected_set)
    r = rows(input_pattern_set);
    l = rows(expected_set);
    if (r != l)
        error('@network/cost: Input pattern set rows (%d) do not match expected set rows (%d)', r, l);
    end

    distance_set = zeros(1, rows(expected_set));

    for i = 1:r
        input_pattern = input_pattern_set(i, :);
        expected = expected_set(i, :);
        actual = activate(net, input_pattern);
        distance_set(i) = norm(expected - actual);
    end

    err = 1/(2*r) * sum(distance_set .^ 2);
endfunction