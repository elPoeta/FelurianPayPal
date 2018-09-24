
let logEmail = document.querySelector('#log-email');
let logPass = document.querySelector('#log-pass');
let btnLogin = document.querySelector('#btn-login');
const URL_LOGIN_SERVER = 'LoginServer';


const  loginMsg = document.querySelector('.loginMsg');
const  login = document.querySelector('.login');
const  signupMsg = document.querySelector('.signupMsg');
const  signup = document.querySelector('.signup');
const  frontbox = document.querySelector('.frontbox');
const  switch1 = document.querySelector('#switch1');
const  switch2 = document.querySelector('#switch2'); 

switch1.addEventListener('click',sw1);

function sw1() {
    loginMsg.classList.toggle("visibility");
    frontbox.classList.add("moving");
    signupMsg.classList.toggle("visibility");
  
    signup.classList.toggle('hide');
    login.classList.toggle('hide');
}

switch2.addEventListener('click', sw2);

function sw2() {
    loginMsg.classList.toggle("visibility");
    frontbox.classList.remove("moving");
    signupMsg.classList.toggle("visibility");
  
    signup.classList.toggle('hide');
    login.classList.toggle('hide');
  }
//
//setTimeout(function(){
//  sw1();
//},1000);
//
//setTimeout(function(){
//  sw2();
//},3000);


btnLogin.addEventListener('click', (e)=>{
     console.log('log >',e);
   let loginUsuario ={};
   loginUsuario.email = logEmail.value;
   loginUsuario.password = logPass.value;
   console.log(loginUsuario);

   Http.post(URL_LOGIN_SERVER,loginUsuario)
           .then(response => response.json())
  .then( data => {
       console.log('log >>> ',data);
           if(data !== null && data !== 'error'){
            window.location.replace("index.html");
           }else{
                      msgLogueo("Error al iniciar sesion");

           }
                    
    }).catch (err => {
       console.log("error",err);
       msgLogueo("Error al iniciar sesion "+err);
});
  
});

function msgLogueo(msg){
    let template = 
            `<div class="mensaje-login">
                 <p>${msg}</p>
             </div>`;
    document.querySelector('#panel-login-msg').innerHTML=template;
    
}