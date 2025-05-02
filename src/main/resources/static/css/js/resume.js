
const plusBtn = document.querySelectorAll('.plus figure');
const addEduForm = document.querySelectorAll('.add-edu-form');
const cancelBtn = document.querySelectorAll('.btn-del');
const sec03BtnSave = document.querySelector('.sec03 .btn-save');
const sec04BtnSave = document.querySelector('.sec04 .btn-save');
const education = document.querySelectorAll('.education');
const schoolList = document.querySelector(".school-list");
const comList = document.querySelector(".com-list");
const pencil = document.querySelectorAll(".pencil");

// 추가 버튼 클릭 시 해당 섹션의 폼만 열기
plusBtn.forEach((btn) => {
    btn.addEventListener('click', () => {
        const section = btn.closest('section'); // 해당 버튼이 속한 섹션
        const form = section.querySelector('.add-edu-form');
        form.classList.add('add');
        form.style.display = 'block'; // 또는 'flex', 상황에 따라
    });
});

pencil.forEach((btn) => {
    btn.addEventListener('click', () => {
        const section = btn.closest('section'); // 해당 버튼이 속한 섹션
        const form = section.querySelector('.edit-edu-form');
        form.classList.add('add');

        const eduDiv = btn.closest('.education');

        if (section.classList.contains('sec03')) {
            // 학력 수정
            const schoolName = eduDiv.querySelector('.school h4')?.innerText || '';
            const major = eduDiv.querySelector('.school p')?.innerText || '';
            const part = eduDiv.querySelector('.edu-info span:nth-child(1)')?.innerText || '';
            const gradType = eduDiv.querySelector('.edu-info span:nth-child(2)')?.innerText || '';
            const grad = eduDiv.querySelector('.edu-info span:nth-child(3)')?.innerText || '';
            const gradDate = eduDiv.querySelector('.graduration')?.innerText.replace(' 졸업', '') || '';

            form.querySelector('.input-school').value = schoolName;
            form.querySelector('.input-major').value = major;
            form.querySelector('.completion').value = part;
            form.querySelector('.level').value = gradType;
            form.querySelector('.grad').value = grad; // 디테일하게 조정 가능
            form.querySelector('input[name="graduationDate"]').value = gradDate;

        } else if (section.classList.contains('sec04')) {
            // 경력 수정
            const companyName = eduDiv.querySelector('.school h4')?.innerText || '';
            const department = eduDiv.querySelector('.school p')?.innerText || '';
            const dates = eduDiv.querySelector('.edu-info span:nth-child(1)')?.innerText || '';
            const position = eduDiv.querySelector('.edu-info span:nth-child(2)')?.innerText || '';
            const jobDescription = eduDiv.querySelector('.job_description')?.innerText || '';

            const [joinDate, quitDate] = dates.split(' ~ '); // 입사일과 퇴사일 나누기

            form.querySelector('.company-name').value = companyName;
            form.querySelector('.department').value = department;
            form.querySelector('input[name="joinDate"]').value = joinDate?.trim() || '';
            form.querySelector('input[name="quitDate"]').value = quitDate?.trim() || '';
            form.querySelector('input[name="position"]').value = position;
            form.querySelector('textarea[name="jobDescription"]').value = jobDescription;


        }
        // else if (section.classList.contains('sec05')) {
        //     // 자격증 수정
        //     const licenseName = eduDiv.querySelector('.sec05 .name')?.innerText || '';
        //     const licenseOrg = eduDiv.querySelector('.sec05 .org')?.innerText || '';
        //     const dateRange = eduDiv.querySelector('.sec05 .date')?.innerText || '';
        //     const [acquisition, expiration] = dateRange.split(' ~ ');
        //
        //     form.querySelector('input[name="licenseName"]').value = licenseName;
        //     form.querySelector('input[name="licenseOrg"]').value = licenseOrg;
        //     form.querySelector('input[name="acquisition"]').value = acquisition?.trim() || '';
        //     form.querySelector('input[name="expiration"]').value = expiration?.trim() || '';
        // }
    });
});

// 취소 버튼 클릭 시 해당 섹션의 폼만 닫기
cancelBtn.forEach((btn) => {
    btn.addEventListener('click', () => {
        const form = btn.closest('.add-edu-form');
        form.classList.remove('add');
        form.style.display = 'none';
    });
});


sec03BtnSave.addEventListener('click', function () {
    let schoolName = document.querySelector(".add-edu-form input[name='schoolName']").value;
    let major = document.querySelector(".add-edu-form input[name='major']").value;
    let part = document.querySelector(".add-edu-form .part").value;
    let draduation = document.querySelector(".add-edu-form .graduation").value;
    let graduationdate = document.querySelector(".add-edu-form input[name='graduationDate']").value;

    console.log(schoolName);
    console.log(major);
    console.log(part);
    console.log(draduation);
    console.log(graduationdate);

    if (!schoolName || !major || !part || !draduation) {
        alert('학교명, 이수형태, 학력구분, 졸업여부를 모두 입력해주세요.');
        return;
    }


    const div = document.createElement('div');
    div.classList.add('education', 'flex');
    div.innerHTML = `
     <div class="school">
                        <h4>${schoolName}</h4>
                        <p>${major}</p>
                    </div>
                    <div class="edu-info">
                        <div>
                            <span>${part}</span>
                            <span>${draduation}</span>
                        </div>
                        <p class="graduration">${graduationdate} 졸업</p>
                    </div>
                    <img src="../image/pencil.png" alt="">
`;

    console.log(schoolList);

    if (schoolList) { // schoolList가 존재하는 경우에만 appendChild 실행
        schoolList.appendChild(div);
    } else {
        console.log("schoolList 요소를 찾을 수 없습니다.");
    }

    document.querySelector(".add-edu-form input[name='schoolName']").value = '';
    document.querySelector(".add-edu-form input[name='major']").value = '';
    document.querySelector(".add-edu-form .part").value = '';
    document.querySelector(".add-edu-form .graduation").value = '';
    document.querySelector(".add-edu-form input[name='graduationDate']").value = ''

    // 폼 숨기기
    addEduForm.forEach(form => {
        form.classList.remove('add');
        form.style.display = 'none';
    });
})


sec04BtnSave.addEventListener('click', function () {
    let companyName = document.querySelector(".companyName").value;
    let department = document.querySelector(".department").value;
    let joinDate = document.querySelector("input[name='joinDate']").value;
    let quitDate = document.querySelector("input[name='quitDate']").value;
    let position = document.querySelector("input[name='position']").value;

    console.log(companyName);
    console.log(department);
    console.log(joinDate);
    console.log(quitDate);
    console.log(position);


    if (!department || !joinDate || !quitDate || !position) {
        alert('부서, 입사일, 퇴사일, 직급 모두 입력해주세요.');
        return;
    }

    const div = document.createElement('div');
    div.classList.add('education', 'flex');
    div.innerHTML = `
     <div class="school">
                        <h4>${companyName}</h4>
                        <p>${department}</p>
                    </div>
                    <div class="edu-info">
                        <span>${joinDate} ~ ${quitDate}</span>
                        <span>${position}</span>
                    </div>
                    <img src="../image/pencil.png" alt="">
`;

    comList.appendChild(div);

    document.querySelector('.companyName').value = '';
    document.querySelector('.department').value = '';
    document.querySelector('input[name="joinDate"]').value = '';
    document.querySelector('input[name="quitDate"]').value = '';
    document.querySelector('input[name="position"]').value = '';
    document.querySelector('.sec04 textarea').value = '';
    document.querySelector('#charCount').textContent = '0';

    // 폼 숨기기
    addEduForm.forEach(form => {
        form.classList.remove('add');
        form.style.display = 'none';
    });

})



function updateCount() {
    const textarea = document.getElementById('myText');
    const count = textarea.value.length;
    document.getElementById('charCount').textContent = count;

    const textarea2 = document.getElementById('myText2');
    const count6 = textarea2.value.length;
    document.getElementById('charCount6').textContent = count6;

    const growth = document.getElementById("growth");
    const count2 = growth.value.length;
    document.getElementById("charCount2").textContent = count2;

    const studentDay = document.getElementById("student-day");
    const count3 = studentDay.value.length;
    document.getElementById("charCount3").textContent = count3;

    const prosAndCons = document.getElementById("pros-and-cons");
    const count4 = prosAndCons.value.length;
    document.getElementById("charCount4").textContent = count4;

    const aspiration = document.getElementById("aspiration");
    const count5 = aspiration.value.length;
    document.getElementById("charCount5").textContent = count5;

}



const plusBtn05 = document.querySelector('.sec05 .plus figure');
const licenseForm = document.querySelector('.sec05 .add-license-form');
const licenseEditForm = document.querySelector('.sec05 .edit-license-form');
const licenseSaveBtn = document.querySelector('.sec05 .btn-license-save');
const licenseCancelBtn = document.querySelector('.sec05 .addCancel');
const editDel = document.querySelector('.sec05 .editDel');
const licenseList = document.querySelector('.sec05 .license-list');

// 추가
plusBtn05.addEventListener('click', () => {

    licenseForm.classList.add('add');
});

// 취소
licenseCancelBtn.addEventListener('click', () => {

    licenseForm.classList.remove('add');
});

// 취소
editDel.addEventListener('click', () => {

    licenseEditForm.classList.remove('add');
});

// '저장' 버튼 누르면 리스트에 카드형태로 추가
licenseSaveBtn.addEventListener('click', () => {
    const name = document.querySelector('.license-name').value.trim();
    const org = document.querySelector('.license-org').value.trim();
    const start = document.querySelector('input[name="acquisition"]').value.trim();
    const end = document.querySelector('input[name="expiration"]').value.trim();

    console.log(name);
    console.log(org);
    console.log(start);
    console.log(end);

    if (!name || !org || !start) {
        alert('자격증명, 발급기관, 취득일을 모두 입력해주세요.');
        return;
    }

    // 카드형식 li 만들기
    const li = document.createElement('li');
    li.classList.add('license-card');
    li.innerHTML = `
        <h3 class="name">${name}</h3>
        <span class="org">${org}</span>
        <p class="date">${start} ${end ? `~ ${end}` : ''}</p>
         <img src="../image/pencil.png" alt="" class="licensePencil">
    `;

    licenseList.appendChild(li);

    // 입력값 초기화
    document.querySelector('.license-name').value = '';
    document.querySelector('.license-org').value = '';
    document.querySelector('input[name="acquisition"]').value = '';
    document.querySelector('input[name="expiration"]').value = '';

    // 폼 숨기기
    licenseForm.style.display = 'none';
    licenseForm.classList.remove('show');
});




document.addEventListener("DOMContentLoaded", function () {
    flatpickr(".custom-date", {
        dateFormat: "Y-m-d",
        disableMobile: true
    });
});

let modifyPencil=document.querySelector(".modifyPencil");
modifyPencil.addEventListener('click',function (){
    location.herf="/user/modify  "
})

var licensePencil =document.querySelector(".licensePencil");

licensePencil.addEventListener('click',function (){
    var editLicenseForm = document.querySelector(".edit-license-form");
    editLicenseForm.classList.add("add");


})


document.querySelectorAll('.tag').forEach(tag => {
    tag.addEventListener('click', () => {
        const input = document.getElementById('resume-title');
        input.value = tag.textContent;
        input.focus();
    });
});