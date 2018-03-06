function eachrow (mat, f)
    for i = 1:rows(mat)
        f(mat(i, :))
    end
endfunction
