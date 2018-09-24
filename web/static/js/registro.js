let signNombre = document.querySelector('#sign-nombre');
let signApellido = document.querySelector('#sign-apellido');
let signEmail = document.querySelector('#sign-email');
let signPass = document.querySelector('#sign-pass');
let signConfirm = document.querySelector('#sign-confirm');
let btnRegistrar = document.querySelector('#btn-registrar');

btnRegistrar.addEventListener('click', (e)=>{
    console.log('reg >',e);
   let registroUsuario ={};
   registroUsuario.nombre = signNombre.value;
   registroUsuario.apellido = signApellido.value;
   registroUsuario.email = signEmail.value;
   registroUsuario.password = signPass.value;
   registroUsuario.confirmPassword = signConfirm.value;
  console.log(registroUsuario);
   Http.post('RegistroServer',registroUsuario)
           .then(response => response.json())
        .then( data => {
           if(data !== null && data !== 'error'){
            window.location.replace("index.html");
   
           }else{
                      errorLogueo("Error al crear cuenta");

           }
                    
    }).catch (err => {
       console.log("error",err);
       errorLogueo("Error al crear cuenta "+err);
});
   
});