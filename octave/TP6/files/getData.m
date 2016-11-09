function result = getData(velocity, muestras)
  generic_file_name = mat2str(velocity);

  for i = 1 : muestras
    toAppend = "_";
    file_name = strcat(generic_file_name, toAppend, mat2str(i), ".txt");
    x(:,i) = load(strcat(file_name));
  endfor

  result = x;

endfunction