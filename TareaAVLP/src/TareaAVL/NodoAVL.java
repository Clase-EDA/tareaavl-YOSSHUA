/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;


/**
 *
 * @author Yosshua Eli Cisneros Villasana 
 * 179889
 */
public class NodoAVL <T extends Comparable <T>>{
    private T elem;
    private NodoAVL<T> izq, der, padre;
    private int facEq;
    
    public NodoAVL(T ele){
        elem = ele;
        izq = null;
        der = null;
        facEq = 0;
    }

    public void setFacEq(int facEq) {
        this.facEq = facEq;
    }
    
    public T getElem(){
        return elem;
    }
    public NodoAVL<T> getIzq(){
        return izq;
    }
    public NodoAVL<T> getDer(){
        return der;
    }

    public NodoAVL<T> getPadre() {
        return padre;
    }

    public int getFacEq() {
        return facEq;
    }
    

    public void setElem(T elem) {
        this.elem = elem;
    }
    

    public void setPadre(NodoAVL<T> padre) {
        this.padre = padre;
    }
    
    public void setIzq(NodoAVL<T> a){
         izq = a;
    }
    public void setDer(NodoAVL<T> a){
         der = a;
    }   
    public int numD(){        
        int r = 0;
        if(der != null)
            r = 1 + der.numD();
        if(izq != null)
            r = 1 + izq.numD();
        return r;
    }
    
    public void cuelga(NodoAVL<T> n){
        if(n ==null) return;
        if(n.getElem().compareTo(elem)<0)
            izq = n;
        else
            der = n;
       n.setPadre(this);                   
    }
     
    
    
}