"use strict";

$(function () {

  var mySiema = new Siema({
    selector: '.siema',
    duration: 100,
    loop: true
  });

  document.querySelector('.js-prev').addEventListener('click', function() {mySiema.prev()});
  document.querySelector('.js-next').addEventListener('click', function() {mySiema.next()});

})
