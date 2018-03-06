function answ = get(obj,varargin)

  accepted = fieldnames(obj);

  tf = ismember(tolower (accepted), tolower (varargin));

  retrieve = {accepted{tf}};
  n = numel (retrieve);
  if n == 1
    answ = obj.(retrieve{1});
  else
    for i=1:n
      answ.(retrieve{i}) = obj.(retrieve{i});
    end
  end

  unknown = {varargin{!ismember(varargin,accepted)}};
  cellfun (@(x) warning ('Unknown field %s\n', x) , unknown);

endfunction
