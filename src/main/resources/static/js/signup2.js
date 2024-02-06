const email = document.querySelector(".email");
const emailNum = document.querySelector(".email-num");
const password = document.querySelector(".password");
const emailCheckBtn = document.querySelector(".btn1");
const passwordCheckBtn = document.querySelector(".btn2");
const submitBtn = document.querySelector(".btn3");
emailCheckBtn.onclick = () => {
	console.log("dddddddd");
	if(email.value == ""){
		alert("이메일을 입력하세요!");
	}
	$.ajax({
		type : "get",
		url : "/email/check",
		data : {
			email : email.value
		},
		success : function(data){
			if(data == true){
				alert("인증번호를 보냈습니다. 확인하시고 인증을 해주세요!");
				email.style.backgroundColor = "yellow";
				email.readOnly = true;
			}else{
				alert("이메일이 중복입니다.");
			}
		},
		error : function(){
			alert("실패");
		}
	});
}

passwordCheckBtn.onclick = () => {
	$.ajax({
		type : "post",
		url : "/email/password",
		data : {
			email : email.value,
			password : emailNum.value
		},
		success : function(data){
			if(data == true){
				alert("인증되었습니다.");
				emailNum.style.backgroundColor = "yellow";
				emailNum.readOnly = true;
			}else{
				alert("인증번호를 확인해주세요!")
			}
		},
		error : function(){
			alert("실패");
		}
	});
}

submitBtn.onclick = () => {
	if(email.readOnly == false && emailNum.readOnly == false){
		alert("이메일 인증을 해주세요!");
	}
	alert("실제 회원가입은 안함");
}

