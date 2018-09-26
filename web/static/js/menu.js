const URL_CATEGORIAS = 'api/CategoriaServer';
const URL_SUBCATEGORIAS = 'api/SubCategoriaServer';
const URL_PRODUCTOS = 'api/ProductoServer?&q=';
const URL_PRODUCTOS_SUBCATEGORIA = 'api/ProductoSubcategoria?&q=';
const URL_LOGIN = 'LoginServer';
const categorias = [];

class Menu{
   static viewMenu(idPanel){
      
        let template = `<li><a href="#" onclick=buscarPorCategoria(0);>Ver Todos</a></li>
                ${categorias.map( categoria =>
                   `<li><a href="#">${categoria.nombre}</a>
                        <ul class="hide-menu">
                          <li><a href="#" class="categoria" id="cat-id-${categoria.id}" onclick=buscarPorCategoria(${categoria.id});>Todos</a>
                          ${categoria.subCategorias.map( sub =>
                          `<li><a href="#" onclick=buscarPorSubCategoria(${sub.id});>${sub.nombre}</a></li>`
                        ).join('')}</ul></li>`
                     ).join('')}`;
                     
        document.querySelector(idPanel).innerHTML = template;
                     //menuEventos();
      }
      
}


const iniciar = () =>{
            Http.get(URL_CATEGORIAS)
	.then(data => {
		 data.map(c => {  
	         return   categorias.push(c);
	     });
            Menu.viewMenu('#panel-side-menu');
            Menu.viewMenu('#menu-template'); 
	 })
          .catch ((error) =>{
              console.log(error);
});
       
       Carrito.consultarCarrito();  
       Usuario.consultar(URL_LOGIN);
           
};



//function menuEventos(){
//    let categoria = document.querySelectorAll('.categoria');
//                     for (let i = 0; i < categoria.length; i++) {
//                        categoria[i].addEventListener('touchstart', ()=> {
//                            eventoBuscar(categoria, i);
//                        });
//                        categoria[i].addEventListener('click', ()=> {
//                            eventoBuscar(categoria,i);
//                        });
//                      }
//
//}

//function eventoBuscar(categoria, i){
//    let id = categoria[i].getAttribute('id');
//    id = id.replace(/\D/g,'');
//    buscarPorCategoria(id);
//  }

function buscarPorCategoria(id){
   let param = {};
    param.id = id;
        loading(true);  
      Http.get(URL_PRODUCTOS+param.id)
      .then(data => {
      Productos.viewProductos(data,'#panel-content');    
           loading(false);
        })
       .catch(error =>{
           console.log(error);
           loading(false);
      });
}

function buscarPorSubCategoria(id){
   
        loading(true);  
      Http.get(URL_PRODUCTOS_SUBCATEGORIA+id)
      .then(data => {
      Productos.viewProductos(data,'#panel-content');    
           loading(false);
               
        })
       .catch(error =>{
           console.log(error);
           loading(false);
      });
}

const btnNavIcon = document.querySelector('#nav-icon-hamburger');
const btnNavPanel = document.querySelector('#btn-nav-panel'); 

btnNavIcon.addEventListener('touchstart', (e)=>{
    e.preventDefault();
    document.querySelector('.nav-panel').style.width='250px';
});

btnNavPanel.addEventListener('touchstart', (e)=>{
    e.preventDefault();
    document.querySelector('.nav-panel').style.width='0px';
});

btnNavIcon.addEventListener('click', (e)=>{
    e.preventDefault();
    document.querySelector('.nav-panel').style.width='250px';
});

btnNavPanel.addEventListener('click', (e)=>{
    e.preventDefault();
    document.querySelector('.nav-panel').style.width='0px';
});

window.addEventListener("load",iniciar,false); 