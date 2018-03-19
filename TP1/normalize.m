function [normalized factor] = normalize(A)
    factor = max(abs(A));
    normalized = double(A) ./ factor;
endfunction
