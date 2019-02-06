"use strict";

$(function () {
  function post(url, data) {
    return $.ajax({
      url: url,
      method: "POST",
      data: JSON.stringify(data),
      contentType: 'application/json'
    })
  }

  function handleSuccess(data) {
    console.log(data.status);
  }

  function handleError (response) {
    alert('Error: ' + response.responseText);
  }

  var handleScoreUpdate = function (inScore, path) {
    var par = {
      score: inScore,
      pagePath: path
    }
    post('/bin/induction/rating', par).then(handleSuccess,handleError);
  }


  //handling Stars
  // Listen for form submissions
  document.addEventListener('submit', function (event) {
    // Only run our code on .rating forms
    if (!event.target.matches('.rating')) {
      return;
    }
    // Prevent form from submitting
    event.preventDefault();
    // Get the selected star
    var selected = document.activeElement;
    if (!selected) {
      return;
    }
    var selectedIndex = parseInt(selected.getAttribute('data-star'), 10);
    // Get all stars in this form (only search in the form, not the whole document)
    // Convert them from a node list to an array
    // https://gomakethings.com/converting-a-nodelist-to-an-array-with-vanilla-javascript/
    var stars = Array.from(event.target.querySelectorAll('.star'));
    // Loop through each star, and add or remove the `.selected` class to toggle highlighting
    stars.forEach(function (star, index) {
      if (index < selectedIndex) {
        // Selected star or before it
        // Add highlighting
        star.classList.add('selected');
      } else {
        // After selected star
        // Remove highlight
        star.classList.remove('selected');
      }
    });

    handleScoreUpdate(selectedIndex,
      event.target.getAttribute('data-current-page'));

    // Remove aria-pressed from any previously selected star
    var previousRating = event.target.querySelector(
      '.star[aria-pressed="true"]');
    if (previousRating) {
      previousRating.removeAttribute('aria-pressed');
    }
    // Add aria-pressed role to the selected button
    selected.setAttribute('aria-pressed', true);
  }, false);
  // Highlight the hovered or focused star
  var highlight = function (event) {
    // Only run our code on .rating forms
    var star = event.target.closest('.star');
    var form = event.target.closest('.rating');
    if (!star || !form) {
      return;
    }
    // Get the selected star
    var selectedIndex = parseInt(star.getAttribute('data-star'), 10);
    // Get all stars in this form (only search in the form, not the whole document)
    // Convert them from a node list to an array
    // https://gomakethings.com/converting-a-nodelist-to-an-array-with-vanilla-javascript/
    var stars = Array.from(form.querySelectorAll('.star'));
    // Loop through each star, and add or remove the `.selected` class to toggle highlighting
    stars.forEach(function (star, index) {
      if (index < selectedIndex) {
        // Selected star or before it
        // Add highlighting
        star.classList.add('selected');
      } else {
        // After selected star
        // Remove highlight
        star.classList.remove('selected');
      }
    });
  };
  // Listen for hover and focus events on stars
  document.addEventListener('mouseover', highlight, false);
  document.addEventListener('focus', highlight, true);

})
