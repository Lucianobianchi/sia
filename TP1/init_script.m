net = build_network(2, 1, [40 20], 0.01, 'tanh', 1);
[inputs, outputs] = open_file('terrain02.data');
normalized_inputs = normalize(inputs);
normalized_outputs = normalize(outputs);
[train_set train_expected test_set test_expected] = split_sets(normalized_inputs, normalized_outputs, 0.75, time);
