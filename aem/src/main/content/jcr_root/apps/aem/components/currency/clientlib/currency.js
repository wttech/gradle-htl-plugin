"use strict";

$(function () {

  function updateRateAndTrend() {
    $.get( "/bin/induction/currency", function( data ) {
      $("#eur-currency-rate").html(data.rate)
      $("#eur-currency-trend").html(data.trend)

    });
  }

  updateRateAndTrend()
  setInterval(updateRateAndTrend(), 1000 * 60);


})
