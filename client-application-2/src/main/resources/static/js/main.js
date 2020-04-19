reset=()=>{
	$("#uname").val("");
	$("#fname").val("");
	$("#lname").val("");
	$("#pwd").val("");
}
validation=()=>{
	let un=$("#uname").val().trim();
	let fn=$("#fname").val().trim();
	let ln=$("#lname").val().trim();
	let pd=$("#pwd").val().trim();
	if(un=="" || fn=="" || ln=="" || pd==""){
		alert("Enter all fields");
		return false;
	}else{
		return true;
	}

}