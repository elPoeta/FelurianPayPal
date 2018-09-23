let btnPaypal;
class Compra{
    static verCompra(){
        panel[1].classList.add('hide-panel');
       
        Http.get(URL_CARRITO)
                .then(data =>{
              console.log(data);
                let template='<section class="seccion-compras"> <ul>';
                 for (let clave in  data.items){
                      if (data.items.hasOwnProperty(clave)) {
                            template += 
                                     `<li class="lista-compras"><img src="${data.items[clave].producto.imagen}"/><h2>${data.items[clave].producto.nombre}</h2><p>$ ${data.items[clave].producto.precio} x ${data.items[clave].cantidad} &nbsp; <span class="subtotal">subtotal $${data.items[clave].subtotal}</span></p></li>`;
                     }
                     template += `<hr>`;
                 }
                template +=   `</ul>
                               <div class="contenedor-total-btn">
                                <h2>Total Compra $ ${data.total}</h2>  
                                </div>
                              </section>`;
            document.querySelector('#panel-content').innerHTML = template;         
            document.querySelector('#paypal-button').style.display="inline-block";
        })                 
        .catch(error =>{
           console.log(error); 
        });
    }
    
    static CompraConfirmada(){
           const template =
                   `</section>
                        <div class="modal-finCompra">
                            <div class="modal-contenido">
                                <div class="modal-titulo">
                                    <h2>Gracias por su compra</h2>
                                </div>
                                <div class="modal-cuerpo">
                                    <p>Compra finalizada con exito.</p>
                                    <p>Disfrute de su elección</p>
                                    <p>Gracias por elegirnos!!</p>
                                </div>
                              <div class="modal-pie">
                                <a href="index.html"><h3>Continuar Comprando</h3></a>
                              </div>
                            </div>
                        </div>
                    </section>`;
            vaciarCarrito();        
            document.querySelector('#paypal-button').style.display="none";
            document.querySelector('#panel-content').innerHTML = template;
       }
    
}

const URL_FINALIZAR_COMPRA_SERVER_PRIVADO = 'api/privado/FinalizarCompraServer';
btnFinalizarCompra.addEventListener('click', e =>{
    Http.get(URL_FINALIZAR_COMPRA_SERVER_PRIVADO)
            .then(data => {
             console.log('data >>',data);
             if(data !=='error'){
              Compra.verCompra();
             }else{
                 window.location.replace("login.html");
        }
          })
                  .catch(err =>{
                      console.log(err);
              window.location.replace("login.html");
          }); 
});
function vaciarCarrito(){
    Http.post(URL_FINALIZAR_COMPRA_SERVER_PRIVADO)
                
    }   