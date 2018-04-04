function rate = error_rate(net, input_pattern_set, expected_set, tolerance)
    r = rows(input_pattern_set);
    l = rows(expected_set);
    if (r != l)
        error('@network/cost: Input pattern set rows (%d) do not match expected set rows (%d)', r, l);
    end

    actual_set = activate(net, input_pattern_set);
    distance_set = sqrt(sum((expected_set - actual_set) .^ 2, 2));

    rate = mean(distance_set > tolerance) * 100;
endfunction
