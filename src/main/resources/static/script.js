const navButtons=document.querySelectorAll(".nav-btn");
const pages=document.querySelectorAll(".page");

navButtons.forEach(button=>{
    button.addEventListener("click",()=>{

        navButtons.forEach(btn=>btn.classList.remove("active"));
        button.classList.add("active");

        pages.forEach(page=>page.classList.remove("active-page"));

        const page=button.dataset.page;

        document.getElementById(page).classList.add("active-page");

        loadPage(page);
    });
});


async function fetchData(url,options={}){

    const response=await fetch(url,options);

    if(!response.ok){

        const errorData=await response.json().catch(()=>({}));

        throw new Error(
            errorData.error || "Request failed"
        );
    }

    if(response.status===204){
        return null;
    }

    return await response.json();
}


function loadPage(page){

    if(page==="dashboard"){
        loadDashboard();
    }

    if(page==="students"){
        loadStudents();
    }

    if(page==="groups"){
        loadGroups();
    }
}


async function loadDashboard(){

    try{

        const students=await fetchData("/api/students");
        const groups=await fetchData("/api/groups");

        document.getElementById("totalStudents").textContent=
            students.length;

        document.getElementById("totalGroups").textContent=
            groups.length;

        let membershipCount=0;

        for(const group of groups){

            const members=await fetchData(
                `/api/memberships/group/${group.id}`
            );

            membershipCount+=members.length;
        }

        document.getElementById("totalMemberships").textContent=
            membershipCount;

        renderDashboardGroups(groups);

        applyCurrentSearch(
            "dashboardGroupSearch",
            "dashboardGroups"
        );

    }catch(error){

        console.error(error);
    }
}


function renderDashboardGroups(groups){

    const container=
        document.getElementById("dashboardGroups");

    if(groups.length===0){

        container.innerHTML=
            `<div class="empty">No study groups available</div>`;

        return;
    }

    let html=`
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Subject</th>
                    <th>Max Members</th>
                    <th>Creator ID</th>
                </tr>
            </thead>
            <tbody>
    `;

    groups.forEach(group=>{

        html+=`
            <tr>
                <td>${group.id}</td>
                <td>${group.subject}</td>
                <td>${group.maxMembers}</td>
                <td>${group.creatorId}</td>
            </tr>
        `;
    });

    html+=`
            </tbody>
        </table>
    `;

    container.innerHTML=html;
}


async function loadStudents(){

    try{

        const students=await fetchData("/api/students");

        const container=
            document.getElementById("studentList");

        if(students.length===0){

            container.innerHTML=
                `<div class="empty">No students found</div>`;

            return;
        }

        let html=`
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
        `;

        students.forEach(student=>{

            html+=`
                <tr>
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student.email}</td>
                    <td>
                        <button
                            class="delete-btn"
                            onclick="deleteStudent(${student.id})">
                            Delete
                        </button>
                    </td>
                </tr>
            `;
        });

        html+=`
                </tbody>
            </table>
        `;

        container.innerHTML=html;

        applyCurrentSearch(
            "studentSearch",
            "studentList"
        );

    }catch(error){

        console.error(error);
    }
}


async function addStudent(){

    const name=
        document.getElementById("studentName")
            .value.trim();

    const email=
        document.getElementById("studentEmail")
            .value.trim();

    if(name===""||email===""){

        alert("Enter student name and email");

        return;
    }

    try{

        await fetchData("/api/students",{

            method:"POST",

            headers:{
                "Content-Type":"application/json"
            },

            body:JSON.stringify({
                name:name,
                email:email
            })
        });

        document.getElementById("studentName").value="";
        document.getElementById("studentEmail").value="";

        await loadStudents();
        await loadDashboard();

    }catch(error){

        alert(error.message);
    }
}


async function deleteStudent(id){

    try{

        await fetch(`/api/students/${id}`,{
            method:"DELETE"
        });

        await loadStudents();
        await loadDashboard();

    }catch(error){

        console.error(error);
    }
}


async function loadGroups(){

    try{

        const groups=await fetchData("/api/groups");

        const container=
            document.getElementById("groupList");

        if(groups.length===0){

            container.innerHTML=
                `<div class="empty">No study groups found</div>`;

            return;
        }

        let html=`
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Subject</th>
                        <th>Max Members</th>
                        <th>Creator ID</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
        `;

        groups.forEach(group=>{

            html+=`
                <tr>
                    <td>${group.id}</td>
                    <td>${group.subject}</td>
                    <td>${group.maxMembers}</td>
                    <td>${group.creatorId}</td>
                    <td>
                        <button
                            class="delete-btn"
                            onclick="deleteGroup(${group.id})">
                            Delete
                        </button>
                    </td>
                </tr>
            `;
        });

        html+=`
                </tbody>
            </table>
        `;

        container.innerHTML=html;

        applyCurrentSearch(
            "groupSearch",
            "groupList"
        );

    }catch(error){

        console.error(error);
    }
}


async function addGroup(){

    const subject=
        document.getElementById("groupSubject")
            .value.trim();

    const maxMembers=
        document.getElementById("groupMaxMembers")
            .value;

    const creatorId=
        document.getElementById("groupCreatorId")
            .value;

    if(subject===""||maxMembers===""||creatorId===""){

        alert("Enter all group details");

        return;
    }

    try{

        await fetchData("/api/groups",{

            method:"POST",

            headers:{
                "Content-Type":"application/json"
            },

            body:JSON.stringify({

                subject:subject,

                maxMembers:parseInt(maxMembers),

                creatorId:parseInt(creatorId)
            })
        });

        document.getElementById("groupSubject").value="";
        document.getElementById("groupMaxMembers").value="";
        document.getElementById("groupCreatorId").value="";

        await loadGroups();
        await loadDashboard();

    }catch(error){

        alert(error.message);
    }
}


async function deleteGroup(id){

    try{

        await fetch(`/api/groups/${id}`,{
            method:"DELETE"
        });

        await loadGroups();
        await loadDashboard();

    }catch(error){

        console.error(error);
    }
}


async function joinGroup(){

    const groupId=
        document.getElementById("joinGroupId")
            .value;

    const studentId=
        document.getElementById("joinStudentId")
            .value;

    const message=
        document.getElementById("membershipMessage");

    if(groupId===""||studentId===""){

        message.className="error-message";

        message.textContent=
            "Enter Group ID and Student ID";

        return;
    }

    try{

        await fetchData(
            "/api/memberships/join",
            {
                method:"POST",

                headers:{
                    "Content-Type":"application/json"
                },

                body:JSON.stringify({

                    groupId:parseInt(groupId),

                    studentId:parseInt(studentId)
                })
            }
        );

        message.className="success-message";

        message.textContent=
            "Student joined successfully";

        document.getElementById("joinGroupId").value="";
        document.getElementById("joinStudentId").value="";

        await loadDashboard();

    }catch(error){

        message.className="error-message";

        if(error.message==="GROUP_FULL"){

            message.textContent="Group is full";

        }else if(error.message==="ALREADY_JOINED"){

            message.textContent=
                "Student already joined this group";

        }else if(error.message==="GROUP_NOT_FOUND"){

            message.textContent=
                "Group not found";

        }else{

            message.textContent=
                error.message;
        }
    }
}


async function viewGroupMembers(){

    const groupId=
        document.getElementById("viewGroupId")
            .value;

    const container=
        document.getElementById("membershipList");

    if(groupId===""){

        container.innerHTML=
            `<div class="error-message">
                Enter Group ID
            </div>`;

        return;
    }

    try{

        const members=await fetchData(
            `/api/memberships/group/${groupId}`
        );

        if(members.length===0){

            container.innerHTML=
                `<div class="empty">
                    No members found
                </div>`;

            return;
        }

        let html=`
            <table>
                <thead>
                    <tr>
                        <th>Membership ID</th>
                        <th>Student ID</th>
                        <th>Group ID</th>
                        <th>Joined At</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
        `;

        members.forEach(member=>{

            html+=`
                <tr>
                    <td>${member.id}</td>
                    <td>${member.studentId}</td>
                    <td>${member.groupId}</td>
                    <td>${member.joinedAt}</td>
                    <td>
                        <button
                            class="leave-btn"
                            onclick="leaveGroup(${member.groupId},${member.studentId})">
                            Remove
                        </button>
                    </td>
                </tr>
            `;
        });

        html+=`
                </tbody>
            </table>
        `;

        container.innerHTML=html;

        applyCurrentSearch(
            "membershipSearch",
            "membershipList"
        );

    }catch(error){

        container.innerHTML=
            `<div class="error-message">
                ${error.message}
            </div>`;
    }
}


async function leaveGroup(groupId,studentId){

    await fetch(
        `/api/memberships/leave?groupId=${groupId}&studentId=${studentId}`,
        {
            method:"DELETE"
        }
    );

    await viewGroupMembers();

    await loadDashboard();
}


function filterTable(inputId,containerId){

    const input=
        document.getElementById(inputId);

    const container=
        document.getElementById(containerId);

    if(!input||!container){
        return;
    }

    const searchText=
        input.value
            .toLowerCase()
            .trim();

    const rows=
        container.querySelectorAll("tbody tr");

    let visibleRows=0;

    rows.forEach(row=>{

        const rowText=
            row.textContent
                .toLowerCase()
                .trim();

        if(rowText.includes(searchText)){

            row.style.display="";

            visibleRows++;

        }else{

            row.style.display="none";
        }
    });

    let message=
        container.querySelector(".no-results");

    if(rows.length>0 && visibleRows===0){

        if(!message){

            message=
                document.createElement("div");

            message.className="no-results";

            message.textContent=
                "No matching records found";

            container.appendChild(message);
        }

    }else if(message){

        message.remove();
    }
}


function clearSearch(inputId,containerId){

    const input=
        document.getElementById(inputId);

    if(!input){
        return;
    }

    input.value="";

    filterTable(
        inputId,
        containerId
    );

    input.focus();
}


function applyCurrentSearch(inputId,containerId){

    const input=
        document.getElementById(inputId);

    if(!input){
        return;
    }

    filterTable(
        inputId,
        containerId
    );
}


loadDashboard();