// 눈 모양 아이콘 (열림/닫힘)
const eyeOpen = `<path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7-11-7-11-7z"></path><circle cx="12" cy="12" r="3"></circle>`;
const eyeClosed = `<path d="M17.94 17.94A10.94 10.94 0 0 1 12 20c-7 0-11-8-11-8a21.62 21.62 0 0 1 5.06-6.06M9.9 4.24A10.94 10.94 0 0 1 12 4c7 0 11 8 11 8a21.6 21.6 0 0 1-3.22 4.44M14.12 14.12a3 3 0 1 1-4.24-4.24"></path><line x1="1" y1="1" x2="23" y2="23"></line>`;

// 비밀번호 보기/숨기기 토글 (공통 함수)
function setupToggle(toggleBtnId, inputId) {
    const toggleBtn = document.getElementById(toggleBtnId);
    const input = document.getElementById(inputId);
    const eyeIcon = toggleBtn.querySelector('.eyeIcon');

    toggleBtn.addEventListener('click', () => {
        const isPassword = input.type === 'password';
        input.type = isPassword ? 'text' : 'password';
        eyeIcon.innerHTML = isPassword ? eyeClosed : eyeOpen;
        toggleBtn.setAttribute('aria-label', isPassword ? '비밀번호 숨기기' : '비밀번호 보기');
    });
}
setupToggle('togglePassword', 'password');
setupToggle('togglePasswordConfirm', 'passwordConfirm');

// 폼 요소 참조
const usernameInput = document.getElementById('username');
const nameInput = document.getElementById('name');
const passwordInput = document.getElementById('password');
const passwordConfirmInput = document.getElementById('passwordConfirm');
const submitBtn = document.getElementById('submitBtn');
const passwordMatchMsg = document.getElementById('passwordMatchMsg');
const idCheckMsg = document.getElementById('idCheckMsg');

// 아이디 중복 확인 상태 (null: 미확인, true: 사용가능, false: 이미 존재)
let idAvailable = null;
let idCheckTimer = null;

async function checkIdDuplicate() {
    const id = usernameInput.value.trim();

    if (id.length === 0) {
        idAvailable = null;
        idCheckMsg.textContent = '';
        idCheckMsg.classList.remove('error', 'success');
        checkFormValid();
        return;
    }

    try {
        const res = await fetch(`/check-id?id=${encodeURIComponent(id)}`);
        const data = await res.json();

        if (data.exists) {
            idAvailable = false;
            idCheckMsg.textContent = '이미 아이디가 있습니다.';
            idCheckMsg.classList.remove('success');
            idCheckMsg.classList.add('error');
        } else {
            idAvailable = true;
            idCheckMsg.textContent = '';
            idCheckMsg.classList.remove('error', 'success');
        }
    } catch (err) {
        // 네트워크 오류 시에는 서버 최종 검증에 맡기고 클라이언트에서는 막지 않음
        idAvailable = null;
        idCheckMsg.textContent = '';
    }

    checkFormValid();
}

// 입력 중에는 매 타이핑마다 요청하지 않도록 디바운스 처리 (400ms)
// 아이디 입력: 영문 대소문자, 숫자만 허용
const idAllowedPattern = /[^A-Za-z0-9]/g;
usernameInput.addEventListener('input', (e) => {
    const filtered = e.target.value.replace(idAllowedPattern, '');
    if (filtered !== e.target.value) {
        e.target.value = filtered;
    }

    idAvailable = null; // 재입력 중에는 이전 확인 결과 무효화
    idCheckMsg.textContent = '';
    idCheckMsg.classList.remove('error', 'success');

    clearTimeout(idCheckTimer);
    idCheckTimer = setTimeout(checkIdDuplicate, 400);
});

// 숫자, 영문, 특수문자만 입력 가능 (비밀번호 필드 전용)
const allowedPattern = /[^A-Za-z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~`]/g;
function restrictPasswordInput(inputId) {
    const input = document.getElementById(inputId);
    input.addEventListener('input', (e) => {
        const filtered = e.target.value.replace(allowedPattern, '');
        if (filtered !== e.target.value) {
            e.target.value = filtered;
        }
        checkFormValid();
    });
}
restrictPasswordInput('password');
restrictPasswordInput('passwordConfirm');

// 이름 입력에도 유효성 검사 연결 (아이디는 위에서 중복확인과 함께 별도 처리)
nameInput.addEventListener('input', checkFormValid);

// 엔터키 입력 시 다음 항목으로 이동
const focusOrder = ['username', 'name', 'password', 'passwordConfirm'];
focusOrder.forEach((id, idx) => {
    const el = document.getElementById(id);
    el.addEventListener('keydown', (e) => {
        if (e.key !== 'Enter') return;
        e.preventDefault();

        const nextId = focusOrder[idx + 1];
        if (nextId) {
            document.getElementById(nextId).focus();
        } else if (!submitBtn.disabled) {
            submitBtn.focus();
            submitBtn.click();
        }
    });
});

// 전체 폼 유효성 검사: 모든 필드 입력 + 비밀번호 일치 시에만 버튼 활성화
function checkFormValid() {
    const username = usernameInput.value.trim();
    const name = nameInput.value.trim();
    const password = passwordInput.value;
    const passwordConfirm = passwordConfirmInput.value;

    // 비밀번호 일치 여부 메시지 표시
    if (passwordConfirm.length === 0) {
        passwordMatchMsg.textContent = '';
        passwordMatchMsg.classList.remove('error', 'success');
    } else if (password === passwordConfirm) {
        passwordMatchMsg.textContent = '비밀번호가 일치합니다.';
        passwordMatchMsg.classList.remove('error');
        passwordMatchMsg.classList.add('success');
    } else {
        passwordMatchMsg.textContent = '비밀번호가 일치하지 않습니다.';
        passwordMatchMsg.classList.remove('success');
        passwordMatchMsg.classList.add('error');
    }

    // 모든 필드가 채워지고, 비밀번호가 일치하고, 아이디 중복확인까지 통과해야 버튼 활성화
    const allFilled = username.length > 0 && name.length > 0 && password.length > 0 && passwordConfirm.length > 0;
    const passwordMatch = password === passwordConfirm;

    submitBtn.disabled = !(allFilled && passwordMatch && idAvailable === true);
}

// 제출 직전 최종 안전장치 (더블 체크)
document.getElementById('signupForm').addEventListener('submit', (e) => {
    const password = passwordInput.value;
    const passwordConfirm = passwordConfirmInput.value;
    if (password !== passwordConfirm) {
        e.preventDefault();
    }
});