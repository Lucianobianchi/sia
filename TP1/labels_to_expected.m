function expected = labels_to_expected(labels)
    labels = labels + 1;
    expected = zeros(length(labels), 10);
    for i = 1:length(expected)
        expected(i, labels(i)) = 1;
    end
endfunction
