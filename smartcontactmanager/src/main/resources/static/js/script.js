
const toggleSidebar=()=>{


if ($(".sidebar").is(":visible")) {


$(".sidebar").css("display","none");
$(".content").css("margin-left","0%")


	
} else {


	$(".sidebar").css("display","block");
    $(".content").css("margin-left","20%")
	
}








};



// javascript for the client side valiadtion





// define a function to validate the phone number input
function validatePhoneNumber() {
  // get the phone number input element
  const phoneInput = document.getElementById("phone");

  // get the value of the input and remove any non-digit characters
  const phoneNumber = phoneInput.value.replace(/\D/g, "");

  // check if the phone number is at least 10 digits long
  if (phoneNumber.length < 10) {
    // if the phone number is too short, display an error message
    phoneInput.setCustomValidity("Phone number must be at least 10 digits long. otherwise fuck you");
  } 
  else if(phoneNumber.length>=11){
	  phoneInput.setCustomValidity("Phone number cannot be greator than 10 fuck you");
  }
  
  else {
    // if the phone number is valid, clear the error message
    phoneInput.setCustomValidity("");
  }
}

// add an event listener to the phone number input to trigger validation on input
const phoneInput = document.getElementById("phone");
phoneInput.addEventListener("input", validatePhoneNumber);








