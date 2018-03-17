function [normalized factor] = normalize(A)
    factor = max(max(abs(A)));
    normalized = double(A) / factor;
endfunction
