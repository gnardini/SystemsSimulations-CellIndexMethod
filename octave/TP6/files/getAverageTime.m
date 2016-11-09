function result = getAverageTime(velocity)

  h = getData(velocity, 5);
  average = mean(h,2);

  result = average(end);

endfunction