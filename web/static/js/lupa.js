class Lupa{
    
    static lupaEventos(){
        let zonaLupa = document.querySelectorAll('.zona-lupa');
        let imgCard = document.querySelectorAll('.img-card');
        for (let i = 0; i < imgCard.length; i++) {
          imgCard[i].addEventListener('mouseenter', ()=> {
            let id = imgCard[i].getAttribute('id');
            Lupa.zoom(id, 3);
          });
        
        }
        removerLupa(zonaLupa);
    }
    
      static zoom(imgID, zoom) {
        let img, glass, w, h, bw;
        img = document.querySelector('#'+imgID);
    
      
        glass = document.createElement("DIV");
        glass.setAttribute("class", "img-magnifier-glass");
        glass.setAttribute("id", "lupa");
       
        img.parentElement.insertBefore(glass, img);
    
        glass.style.backgroundImage = "url('" + img.src + "')";
        glass.style.backgroundRepeat = "no-repeat";
        glass.style.backgroundSize = (img.width * zoom) + "px " + (img.height * zoom) + "px";
        bw = 3;
        w = glass.offsetWidth / 2;
        h = glass.offsetHeight / 2;
      
       
        glass.addEventListener("mousemove", moveMagnifier);
        img.addEventListener("mousemove", moveMagnifier);
      
    
        glass.addEventListener("touchmove", moveMagnifier);
        img.addEventListener("touchmove", moveMagnifier);
        function moveMagnifier(e) {
          let pos, x, y;
       
          e.preventDefault();
        
          pos = getCursorPos(e);
          x = pos.x;
          y = pos.y;
         
          if (x > img.width - (w / zoom)) {x = img.width - (w / zoom);}
          if (x < w / zoom) {x = w / zoom;}
          if (y > img.height - (h / zoom)) {y = img.height - (h / zoom);}
          if (y < h / zoom) {y = h / zoom;}
    
          glass.style.left = (x - w) + "px";
          glass.style.top = (y - h) + "px";
          
          glass.style.backgroundPosition = "-" + ((x * zoom) - w + bw) + "px -" + ((y * zoom) - h + bw) + "px";
        }
      
        function getCursorPos(e) {
          let a, x = 0, y = 0;
          e = e || window.event;
       
          a = img.getBoundingClientRect();
        
          x = e.pageX - a.left;
          y = e.pageY - a.top;
          
          x = x - window.pageXOffset;
          y = y - window.pageYOffset;
          return {x : x, y : y};
        }
      }
}

function removerLupa(zonaLupa){
    if(zonaLupa !== undefined){
    for (let i = 0; i < zonaLupa.length; i++) {
    zonaLupa[i].addEventListener('mouseleave', ()=>{
        lupa = document.querySelector('#lupa')
        if(lupa !== null){
            let parent = lupa.parentElement;
            parent.removeChild(lupa);
        }
    });
    }
    }
    }