function result = getData(velocity, muestras)
  generic_file_name = mat2str(velocity);

  if velocity < 10
    generic_file_name = strcat("0", generic_file_name);
  endif


  for i = 1 : muestras
    toAppend = "_";
    if i < 10
      toAppend = strcat(toAppend, "0");
    endif

    file_name = strcat(generic_file_name, toAppend, mat2str(i), ".txt");
    x(:,i) = load(strcat(file_name));
  endfor

  result = x;

endfunction