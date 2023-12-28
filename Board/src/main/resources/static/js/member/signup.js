let checkEmail = false;
let checkPassword = false;

// 2023.12.27
// 이메일 유효성 검사
document.getElementById("send-email-btn").addEventListener("click", function (){

    // 사용자가 입력한 이메일 정보
    const emailInput = document.getElementById("email");
    const email = emailInput.value;

    // 이메일 정규식
    const regEx = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    // 이메일이 비어 있지 않은 경우
    if (email.trim() !== "") {

        // 이메일이 올바른 형식일때
        if (regEx.test(email)) {

            fetch("/send-certification", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("서버 응답 오류");
                    }
                    return response.text();
                })
                .then(data => {

                    // 인증번호 전송 성공시
                    alert("인증 번호 전송 성공: " + data);
                    // 인증번호 받기 비활성화
                    document.getElementById("send-email-btn").disabled = true;

                })
                .catch(err => {
                    // 서버 응답 실패시
                    console.error("오류 발생", err);
                    alert("서버 요청 중 오류가 발생했습니다.");
                });
        } else {

            // 이메일 형식이 올바르지 않을 때
            alert("유효하지 않은 이메일 형식입니다.");

            emailInput.value = "";

            emailInput.focus();
        }
    } else {
        // 이메일이 비어 있을 때
        alert("이메일을 입력해주세요.");
        emailInput.focus();
    }
});

// 2023.12.27
// 인증번호 서버에서 인증 요청 보내기
document.getElementById("email-auth-btn").addEventListener("click", function (){

    // 이메일
    const email = document.getElementById("email").value;
    // 인증번호
    const authKey = document.getElementById("email-auth-key").value;

    // 서버로 이메일, 인증번호 전송
    fetch("/check-certification", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, authKey })
    })
        .then(response => {
            if(!response.ok){
                throw new Error("서버 응답 오류");
            }
            return response.text();
        })
        .then(data => {
            // 인증번호 인증 응답 성공시
            if (data === 'true') {
                alert("인증 성공!");
                // 버튼 비활성화
                document.getElementById("email-auth-btn").disabled = true;
                checkEmail = true;

            } else {
                alert("인증 실패!");
            }
        })
        .catch(err => {
            // 인증번호 인증 실패시
            console.error("인증 실패", err);
            alert("인증 실패!");
        });

});

// 비밀번호 유효성 검사
const inputPw = document.getElementById("password");
const passBtn = document.getElementById("pass-btn");

passBtn.addEventListener("click", () => {

    const password = inputPw.value.trim();
    const regExp = /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$/;

    // 비밀번호를 입력하지 않았을 때
    if (password.length === 0) {

        inputPw.value = "";
        inputPw.focus();
        alert("특수문자, 숫자, 대문자 포함 8자리 이상 입력해주세요");

    } else if (!regExp.test(password)) {

        // 유효하지 않은 비밀번호 형식일 때
        inputPw.value = "";
        inputPw.focus();
        alert("비밀번호 형식이 올바르지 않습니다");

    } else {

        alert("사용가능한 비밀번호 입니다.");
        passBtn.disabled = true;
        checkPassword = true;
    }
});

// 가입하기 버튼 눌렀을때 check 여부 검사
const signUpButton = document.querySelector(".signup-button");
const signUpForm = document.getElementById("signUpFrm");

signUpButton.addEventListener("click", (e) => {

    // 이벤트의 기본 동작(폼 제출)을 막음
    e.preventDefault();

    if (checkEmail && checkPassword) {

        signUpForm.submit();

    } else {

        alert("이메일 또는 비밀번호가 올바르지 않습니다.");
    }
});

// 페이지 로드 시 실행되는 onload 함수
window.onload = function() {
    // 플래시 메시지가 있는 경우에만 처리
    var flashMessage = document.getElementById('flashMessage');
    if (flashMessage) {
        // 메시지가 3초 동안 표시되고 사라지도록 설정
        setTimeout(function() {
            flashMessage.style.display = 'none';
        }, 3000);
    }
};