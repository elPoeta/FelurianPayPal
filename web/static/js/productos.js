class Productos{
  
    static viewProductos(productos, idPanel){
     
        let template =  `<section class="contenedor-productos text-center">
                         ${productos.map(producto =>
           `<div class="card">
            <div class="zona-lupa">
              <img src="${producto.imagen}" class="img-card" id="img-${producto.id}">
            </div>
            <h2 class="titulo-card">${producto.nombre}</h2>
            <h3 class="precio-card">$ ${producto.precio}</h3>
            <hr>
            <button class="btn-agregar" id="btn-agregar-${producto.id}" onclick="Carrito.agregarProducto(${producto.id})">Agregar</button>
            </div>`).join('')}</section>`;  
             document.querySelector('#paypal-button').style.display="none";
            document.querySelector(idPanel).innerHTML = template;
 
            Lupa.lupaEventos();
            
    }
}
