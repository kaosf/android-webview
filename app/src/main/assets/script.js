var $countA = 0;
function countUpA() {
  document.getElementById( "sampleOutputA" ).innerHTML = ++$countA;
}

function showAndroidToast( message ) {
  alert( "Toast/" + message );
}

function showConfirmDialog( title, buttonStr, message ) {
  alert( "ConfirmDialog/" + title + "/" + buttonStr + "/" + message );
}

function show2SelectionsDialog( title, posButtonStr, negButtonStr, message ) {
  alert( "2SelectionsDialog/" + title + "/" + posButtonStr + "/" + negButtonStr + "/" + message );
}

function show3SelectionsDialog( title, posButtonStr, neuButtonStr, negButtonStr, message ) {
  alert( "3SelectionsDialog/" + title + "/" + posButtonStr + "/" + neuButtonStr + "/" + negButtonStr + "/" + message );
}
