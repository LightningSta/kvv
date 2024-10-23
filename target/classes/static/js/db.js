var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

document.addEventListener('DOMContentLoaded', ()=>{
    getList()
})

function getList(){
    fetch('/home/list',{
        method: 'GET'
    }).then(response => response.json())
        .then(users=>{
            addList(users)
        })
}

function deleteUser(Login, elem){
    fetch('/home/delete',{
        method: 'DELETE',
        headers: (_a = {
            'Content-Type': 'application/json'
        },
            _a[csrfHeader] = csrfToken,
            _a),
        body: JSON.stringify({
            login: Login
        })
    }).then(function (resp){
        const sect = document.getElementById('main')
        sect.removeChild(elem)
    })
}

function UpdateUser(old_login,div){
    const login = div.querySelector('#login').value
    const role = div.querySelector('#role').value
    fetch('/home/update',{
        method: 'PATCH',
        headers: (_a = {
            'Content-Type': 'application/json'
        },
            _a[csrfHeader] = csrfToken,
            _a),
        body: JSON.stringify({
            login: login,
            role: role,
            old_login: old_login
        })
    })
}

function deleteAll(){
    fetch('/home/deleteAll',{
        method: 'DELETE',
        headers: (_a = {
            'Content-Type': 'application/json'
        },
            _a[csrfHeader] = csrfToken,
            _a),
    }).then(function (resp){
        const sect = document.getElementById('main')
        sect.replaceChildren([]);
    })
}


function addUserInList(user,tech_role){
    const section= document.getElementById('main')
    var div = document.createElement('div')
    div.classList.add('items')
    div.classList.add('form-home-flex')
    var p = document.createElement('p')
    p.textContent=user.login;
    var p2 = document.createElement('p')
    p2.textContent=user.role;
    if(tech_role=='Admin'&& user.role!='Admin'){
        p2=document.createElement('select')
        userr = document.createElement('option')
        userr.value='User'
        userr.textContent='User';
        admin = document.createElement('option')
        admin.value='Admin'
        admin.textContent='Admin';
        moderator = document.createElement('option')
        moderator.value='Moderator'
        moderator.textContent='Moderator';
        if(user.role=='User'){
            user.selected=true;
        }else{
            moderator.selected=true;
        }
        p2.appendChild(userr)
        p2.appendChild(moderator)
        p2.appendChild(admin)

        p=document.createElement('input')
        p.value=user.login
    }
    p.id='login'
    p2.id='role'
    div.appendChild(p)
    div.appendChild(p2)

    if(tech_role!='User'){
        if(user.role!='Admin'){
            var deleteB = document.createElement('button')
            deleteB.textContent='Удалить пользователя';
            deleteB.addEventListener('mousedown', function(){
                deleteUser(user.login,div)
            })
            var update = document.createElement('button')
            update.textContent='Обновить информацию';
            update.addEventListener('mousedown',function (){
                UpdateUser(user.login,div)
            })
            div.appendChild(update)
            div.appendChild(deleteB)
        }
    }
    section.appendChild(div)
}

function addList(resp){
    for (let i = 1; i < resp.users.length; i++) {
        addUserInList(resp.users[i],resp.users[0].role);
    }
}