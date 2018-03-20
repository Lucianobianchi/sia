net = build_network(n_inputs, n_outputs, hidden_layers, initialization, lr, f_activation_name, slope);
[inputs, outputs] = open_file(filename);

switch (f_activation_name)
    case 'tanh'
        output_lower_bound = -1;
        output_upper_bound = 1;
    case 'logistic'
        output_lower_bound = 0;
        output_upper_bound = 1;
endswitch

normalized_inputs = map(inputs, [input_lower_bound input_lower_bound], [input_upper_bound input_upper_bound]);
normalized_outputs = map(outputs, [output_lower_bound], [output_upper_bound]);
[train_set train_expected test_set test_expected] = split_sets(normalized_inputs, normalized_outputs, test_ratio, seed);
