function [normalized factor] = normalize(A, lower_bound, upper_bound)
    factor = max(max(abs(A)));
    normalized = double(A) / factor;
endfunction
