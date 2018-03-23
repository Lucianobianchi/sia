function [train_set train_expected test_set test_expected] = split_sets(input_set, expected, test_ratio)
    li = rows(input_set);
    le = length(expected);
    if (li != le)
        error('split_sets: length of expected values vector (%d) does not match the number of input patterns (%d)', le, li);
    end

    shuffled = randperm(length(input_set));
    test_limit = round(length(input_set) * test_ratio);

    test_index = shuffled(1:test_limit);
    test_set = input_set(test_index, :);
    test_expected = expected(test_index);

    train_index = shuffled(test_limit + 1:end);
    train_set = input_set(train_index, :);
    train_expected = expected(train_index);
endfunction
