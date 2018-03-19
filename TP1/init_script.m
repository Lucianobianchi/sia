net = build_network(n_inputs, n_outputs, hidden_layers, lr, f_activation_name, slope);
[inputs, outputs] = open_file(filename);
normalized_inputs = normalize(inputs);
normalized_outputs = normalize(outputs);
[train_set train_expected test_set test_expected] = split_sets(normalized_inputs, normalized_outputs, test_ratio, seed);
