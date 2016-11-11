function [result, error] = getAverageTime(velocity, samples)

  h = getData(velocity, samples);
  row = h(end,:);
  error = std(row);
  result = mean(row);

endfunction