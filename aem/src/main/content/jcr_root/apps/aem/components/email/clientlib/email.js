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

  function handleSuccess() {
    displayMessage("Your question has been sent. We will contact you soon.")
  }

  function handleError(jqXHR, status) {
    console.log('Error: ' + status);
    displayMessage(
      "We are sorry. We cannot sent your question. Please try again in an hour.")
  }

  function enableButton() {
    $("#sendbutton").removeAttr("disabled")
  }

  function disableButton() {
    $("#sendbutton").attr("disabled", "disabled")
  }

  function displayMessage(msg) {
    $("#message").html(msg)
  }

  function sendEmail() {
    disableButton()
    var input = {
      emailFrom: $("#emailFrom").val(),
      question: $("#question").val()
    }
    var path = $(".email-submit").data("resource-path") + ".send"
    post(path, input)
    .done(handleSuccess)
    .fail(handleError)
    .always(function () {
      enableButton();
    });
  }

  $(".email-submit").click(sendEmail);

})
