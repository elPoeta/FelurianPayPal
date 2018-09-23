const URL_CARRITO = 'api/CarritoServer';
const cartCantidad = document.querySelector('#cantidadEnCarritoCompras');
const carritoLogo = document.querySelector('.carrito-logo');
const panelCarrito = document.querySelector('#panel-carrito');
const totalCarrito = document.querySelector('#total-carrito');
const  panel = document.querySelectorAll('.panel-carroCompras');
const  btnFinalizarCompra = document.querySelector('#btn-Comprar');
const carritoVacio = document.querySelector('#carritoVacio');
let cantidadItems=0;
let carroCompras = {};
carroCompras.cantidadItems =0;

class Carrito{

    static agregarProducto(id){

       let carritoItem ={};
        carritoItem.producto = {};
        carritoItem.producto.id = id;
        
         if(document.querySelector('#rango-'+id)){
           carritoItem.cantidad = Number(document.querySelector('#rango-'+id).value) + Number(1);
        }else{
           carritoItem.cantidad = 1;
        }
        Http.put(URL_CARRITO, carritoItem)
                .then(() =>{
                  this.consultarCarrito();
        })
        .catch(error => {
           console.log('error ',error); 
        });
      
    }
    static quitarProducto(event,id){
        
        Http.delete(URL_CARRITO+`?&q=${id}`)
                .then(response => response.json())
                .then(data =>{
                if (event.target.className ==='eliminarProducto-panel-carrito') {
                event.target.parentElement.remove();
                document.querySelector(`#hr-${id}`).remove(); 
                   carroCompras = data;
                   carroCompras.cantidadItems = (carroCompras.cantidadItems == undefined) ? 0 : carroCompras.cantidadItems; 
                  
                   if(carroCompras.cantidadItems == 0){
                            panelCarritoVacio();
                   }
                   cartCantidad.innerHTML = carroCompras.cantidadItems;
                   this.consultarCarrito();
                   
            }
        })
        .catch(error=>{
            console.log(error);
        });
         

    }
    
    static actualizarCantidad(id, cantidad){
        
        let carritoItem ={};
        carritoItem.producto = {}
        carritoItem.producto.id = id;
        carritoItem.cantidad = cantidad;
        
        Http.put(URL_CARRITO, carritoItem)
                .then(response => response.json())
                .then(data =>{
                   carroCompras = data;
                   cartCantidad.innerHTML = carroCompras.cantidadItems;
                   this.consultarCarrito();   
                  
        })
        .catch(error => {
           console.log('error ',error); 
        });
    
    }
    
    static finalizarCompra(){
        
    }
    
    static consultarCarrito(){
        Http.get(URL_CARRITO)
                .then(data =>{
                   
                if(data.cantidadItems != 0){
                    carroCompras = data;
                    cartCantidad.innerHTML = carroCompras.cantidadItems;
                    document.querySelector('#total-carrito').innerText = carroCompras.total;
                    panelCarritoConProductos();
                }
        })
        .catch(error =>{
           console.log(error); 
        });

    }

}

carritoLogo.addEventListener('click', (e)=>{
    
     e.preventDefault();
    let i;
    if(carroCompras.cantidadItems == 0){
         i = 0;
        panelCarritoVacio();
       
    }else{
        i=1;
      panelCarritoConProductos();
    }

  if(panel[i].classList.contains('hide-panel')){
       
    panel[i].classList.remove('hide-panel');
  }
   else{
     panel[i].classList.add('hide-panel');
  
 }
});

function panelCarritoConProductos(){
   let template='';
    for (let clave in  carroCompras.items){
       if (carroCompras.items.hasOwnProperty(clave)) {
            template += 
                  `<li class="lista-panel-carrito">
                    <img class="img-panel-carrito" src="${carroCompras.items[clave].producto.imagen}"/>
                    <h5 class="nombreProducto-panel-carrito">${carroCompras.items[clave].producto.nombre} &nbsp;<span class="simboloMoneda-panel-carrito">$</span>${carroCompras.items[clave].producto.precio}</h5> 
                    <input class="spinner-panel-carrito" id="rango-${carroCompras.items[clave].producto.id}" type= "number" name="quantity" min="1" max="15" value="${carroCompras.items[clave].cantidad}">
                    <a href="#" class="cambiarCantidad-panel-carrito" id="addcart-${carroCompras.items[clave].producto.id}" onclick="Carrito.actualizarCantidad(${carroCompras.items[clave].producto.id}, document.querySelector('#rango-${carroCompras.items[clave].producto.id}').value)">&#10004;</a>
                    <a href="#" class="eliminarProducto-panel-carrito" id="delcart-${carroCompras.items[clave].producto.id}" onclick="Carrito.quitarProducto(event,${carroCompras.items[clave].producto.id})">&times;</a>
                    </li>
                    <hr id="hr-${carroCompras.items[clave].producto.id}">`;
     
       }
       document.querySelector('.total-panel-carrito').style.visibility = "visible";
       btnFinalizarCompra.style.visibility = "visible";
       panel[0].classList.add('hide-panel');
       panelCarrito.innerHTML = template;
    
  }
}
     

function panelCarritoVacio(){
    document.querySelector('.total-panel-carrito').style.visibility = "hidden";
    btnFinalizarCompra.style.visibility = "hidden";
    panel[1].classList.add('hide-panel');
    panel[0].style.height = "65px";
}
