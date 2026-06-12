
let nextId = 0

let tasks = [
    {
        id: nextId++,
        taskName: "quản lý",
        per: "Trần Văn A",
        deadline: "2026-11-01",
        status: "Chờ làm",
    },
    {
        id: nextId++,
        taskName: "lễ tân",
        per: "Nguyễn Văn B",
        deadline: "2026-07-01",
        status: "Đang làm",
    },
]
if (localStorage.getItem("tasks")) {
    tasks = JSON.parse(localStorage.getItem("tasks"))
} else {
    localStorage.setItem("tasks", JSON.stringify(tasks));
}

const tbodyEL = document.querySelector("tbody");
const se_ipEL = document.querySelector("#se_ip");
let taskEdit = null

function renderUI(arrTask) {
    let tbodyHtml = ``;
    for (let i = 0; i < arrTask.length; i++) {
        tbodyHtml += `
           <tr>
               <td>${arrTask[i].id}</td>
               <td>${arrTask[i].taskName}</td>
               <td>${arrTask[i].per}</td>
               <td>${arrTask[i].deadline}</td>
               <td>${
                arrTask[i].status === "Chờ làm" ? "Chờ làm" : 
                arrTask[i].status === "Đang làm" ? "Đang làm" : "Hoàn thành"}</td>
               <td>
                   <button onclick="deleteTask(${arrTask[i].id})">Xóa</button>
                   <button onclick="editLoad(${arrTask[i].id})">Sửa</button>
               </td>
           </tr>   
       `
    }
    tbodyEL.innerHTML = tbodyHtml
    if (taskEdit) {
        let formEdit = document.querySelector("form");
        let inputs = formEdit.querySelectorAll("input");

        formEdit.querySelectorAll("input")[0].value = taskEdit.id;
        formEdit.querySelectorAll("input")[1].value = taskEdit.taskName;
        formEdit.querySelectorAll("input")[2].value = taskEdit.per;
        formEdit.querySelectorAll("input")[3].value = taskEdit.deadline;
        formEdit.querySelectorAll("input")[taskEdit.status === "Chờ làm" ? 4 : taskEdit.status === "Đang làm" ? 5 : 6].checked = true;
    }
    localStorage.setItem("tasks", JSON.stringify(tasks));
}

function save(e) {
   e.preventDefault()
   const form = e.target;

   const statusWait = form.querySelector('input[value="Chờ làm"]')
   statusWait.checked = true
   

   let newTask = {
       id: taskEdit ? taskEdit.id : nextId++,
       taskName: form.taskName.value,
       per: form.per.value,
       deadline: form.deadline.value,
       status: form.status.value,
   }
   if (taskEdit) {
       for (let i = 0; i < tasks.length; i++) {
           if (tasks[i].id == taskEdit.id) {
               tasks[i] = newTask;
               break;
           }
       }
       taskEdit = null;
       form.reset()
   } else {
       tasks.push(newTask)
       form.reset()
   }
   renderUI(tasks);
}

function deleteTask(taskId){
    for (let i = 0; i < tasks.length; i++) {
        if (tasks[i].id == taskId) {
            tasks.splice(i, 1);
            break;
        }
    }
    renderUI(tasks)
}

function search() {
    let textSe = se_ipEL.value.toLowerCase();
    let kq = [];

    for (let i = 0; i < tasks.length; i++) {
        if (
            tasks[i].taskName.toLowerCase().includes(textSe) ||
            tasks[i].per.toLowerCase().includes(textSe)
        ) {
            kq.push(tasks[i]);
        }
    }

    renderUI(kq);
}
function sapXep() {
   renderUI(tasks.sort((a, b) => a.taskName.localeCompare(b.taskName)));
}
function editLoad(taskId) { 
   for (let i = 0; i < tasks.length; i++) {
       if (tasks[i].id == taskId) {
           taskEdit = tasks[i]; 
           break; 
       }
   }
   renderUI(tasks);
}

renderUI(tasks);