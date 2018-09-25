class Usuario{
    static consultar(url){
        Http.get(url)
                .then(data =>{
                    console.log('User>> ',data);
                 if(data !== null && data!=='error'){
                    const login = document.querySelector('.login')
                           login.innerText = data.nombre;
                           login.removeAttribute('id');
                           login.setAttribute('id','logout-header');
                }
        })
                .catch(err =>{
                    console.error(err);
        });
    }
    static panelUser(){
    const div = document.createElement('div');
    const ul = document.createElement('ul');
    const liCuenta = document.createElement('li');
    const liLogout = document.createElement('li');
    const linkCuenta = document.createElement('a');
    const linkLogout = document.createElement('a');
    div.className= 'panel-usuario hide-panel-usuario';
    let t = document.createTextNode("Mi cuenta"); 
    linkCuenta.setAttribute('href','#');
    linkCuenta.appendChild(t);
    liCuenta.appendChild(linkCuenta);
    t = document.createTextNode("Cerrar sesion"); 
    linkLogout.setAttribute('href','#');
    linkLogout.setAttribute('id', `logout`);
    linkLogout.setAttribute("onclick", "cerrarSesion();");
    linkLogout.appendChild(t);
    liLogout.appendChild(linkLogout);
    ul.appendChild(liCuenta);
    ul.appendChild(liLogout);
    div.appendChild(ul);
    let template = 
            `<div class="panel-usuario">
                <ul>
                    <li><a href="#">Mi cuenta</a></li>
                    <li><a href="#">Cerrar sesion</a></li>
                </ul>
             <div>`;
    document.querySelector('.contenedor').appendChild(div);
}
}


function cerrarSesion(){
    console.log("OUT!!!");
    Http.get('api/privado/LogOut')
            .then( data =>{
                console.log('LogOut ',data);
                location.replace('login.html');
    }).catch(err =>{
        console.log('Error ',err);
    });
}

