function [inputs outputs] = open_file(filename)
    data = dlmread(filename);
    inputs = data(2:end, 1:2);
    outputs = data(2:end, 3);
endfunction