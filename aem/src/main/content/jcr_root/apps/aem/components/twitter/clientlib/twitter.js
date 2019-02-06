"use strict";

$(function () {

  function retrieveTwitts(container) {
    var feed = container.data('feed')
    $.get( "/bin/induction/twitter?feed="+feed, function( data ) {

      var arrayLength = data.length;
      for (var i = 0; i < arrayLength; i++) {
        container.append("<div>" + data[i].text + "</div>")
      }
    });
  }

  var container = $("#twitter-feed-container")
  retrieveTwitts(container)

})
