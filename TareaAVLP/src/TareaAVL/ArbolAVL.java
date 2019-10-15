package TareaAVL;

import TareaAVL.BSTAVLADT;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Yosshua Eli Cisneros Villasana 
 * 179889
 */
public class ArbolAVL <T extends Comparable <T>> implements BSTAVLADT<T> {
    
    protected NodoAVL root;
    protected int cont;            
    
    public ArbolAVL(T ele){
        root = new NodoAVL((Comparable) ele);        
        cont = 1;
    }
    public ArbolAVL(){                
        root = null;
        cont = 0;
    }                       
    
    @Override
    public boolean isEmpty() {
        return cont ==0;
    }

    @Override
    public int size() {
        return cont;
    }               
    
    public int getHeight(){
        return getHeight(root);
    }    

    public int getHeight(NodoAVL r) {
        if(r  == null) return 0;                        
            return 1+Math.max(getHeight(r.getIzq()), getHeight(r.getDer()));                  
    }
    
    public void insert(T elem){        
        NodoAVL<T> act = addI(elem);
        NodoAVL<T> pa = act.getPadre();
        boolean termine = false;
        while(!termine && pa != null){            
            if(act == act.getPadre().getIzq())
                act.getPadre().setFacEq(act.getPadre().getFacEq()-1);                
            else if(act == act.getPadre().getDer())
                act.getPadre().setFacEq(act.getPadre().getFacEq()+1);
            else
                termine = true;
            act = pa;
            pa = act.getPadre();                                    
            if(act.getFacEq() == 0 || Math.abs(act.getFacEq())== 2)
                termine = true;              
        }
        if(Math.abs(act.getFacEq()) == 2 ){
                rotacion(act);
            }
        
                    
    }
        
    @Override
    public void remove(T elem) {        
        //si es la root  
        NodoAVL nodo =  (NodoAVL) find(elem);        
        if(nodo != null){
            this.cont--;            
            //si no tiene hijos
            if(nodo.getDer() == null && nodo.getIzq() == null)
            {
                if(nodo == root)
                    root = null;                                    
                else
                {
                    if(nodo.getElem().compareTo(nodo.getPadre().getElem()) < 0){                                                
                        balancear(nodo);
                        nodo.getPadre().setIzq(null);                        
                        nodo.setPadre(null);
                    }
                    else{
                        balancear(nodo);
                        nodo.getPadre().setDer(null);                                                            
                        
                    }
                    
                }                
            }
            //si solo tiene hijo der
            else if(nodo.getIzq() == null)
            {
                if(nodo == root)
                {
                    root = nodo.getDer();
                    root.setPadre(null);                    
                }
                else
                {
                    balancear(nodo);
                    nodo.getPadre().cuelga(nodo.getDer());                                        
                }
            }
            //si solo tiene hijo izq
            else if(nodo.getDer() == null)
            {
               if(nodo == root)
                {
                    root = nodo.getIzq();
                    root.setPadre(null);
                }
                else
                {
                    balancear(nodo);
                    nodo.getPadre().cuelga(nodo.getIzq());                    
                } 
            }
            //si tiene dos hijos
            else
            {
                NodoAVL sI = sucesorInorder(nodo);
                balancear(sI);
                nodo.setElem(sI.getElem());
                if(sI == nodo.getDer()){
                    nodo.setDer(sI.getDer());
                    if(nodo.getDer() != null)
                        nodo.getDer().setPadre(nodo);
                }
                else{
                    sI.getPadre().setIzq(sI.getDer());
                    if(sI.getDer()!= null)
                        sI.getDer().setPadre(sI.getPadre());
                }
                
            }
                        
        }        
    }
    private void balancear(NodoAVL nodo) {
        NodoAVL<T> act = nodo;
        NodoAVL<T> pa = act.getPadre();
        boolean termine = false;
        while(!termine && pa != null){            
            if(act == act.getPadre().getIzq())
                act.getPadre().setFacEq(act.getPadre().getFacEq()+1);                
            else if(act == act.getPadre().getDer())
                act.getPadre().setFacEq(act.getPadre().getFacEq()-1);
            else
                termine = true;
            act = pa;
            pa = act.getPadre();                        
            if(Math.abs(act.getFacEq()) == 2)
                termine = true;
        }        
        if(Math.abs(act.getFacEq()) == 2 ){  
            if(nodo.getPadre().getElem().compareTo(nodo.getElem()) < 0)
                nodo.getPadre().setDer(null);
            else
                nodo.getPadre().setIzq(null);
            rotacion(act);
        }        
    }
    private NodoAVL sucesorInorder(NodoAVL actual) throws NullPointerException{
        if(actual == null)
            throw new NullPointerException();
        if(actual.getDer() == null)
            return actual.getPadre();
        else
        {
            actual = actual.getDer();
            while(actual.getIzq()!=null)
                actual = actual.getIzq();
        } 
        return actual;
    }
   
    
    private void rotacion(NodoAVL<T> N){
                
        // Izq - Izq
        if(N.getFacEq() == -2 && N.getIzq().getFacEq() == -1 ){                        
            izqIzq(N);                                                                                
        }
        // Izq - Der
        else if(N.getFacEq() == -2 && N.getIzq().getFacEq() >= 0){                                                   
            izqDer(N);
        }
        //Der - Der
        else if(N.getFacEq() == 2 && N.getDer().getFacEq() == 1){
            derDer(N);
        }
        //Der - Izq
        else{  ///N.getFacEq() == 2 && N.getIzq().getFacEq() <= 0
            derIzq(N);
        }        
    }
    
    private void derDer(NodoAVL<T> N){
        NodoAVL<T> alfa, beta, papa, gamma, A,B,C, D;
        // Izq - Izq        
            papa = N.getPadre();
            
            alfa = N;            
            beta = N.getDer();
            gamma = beta.getDer();
            A = alfa.getIzq();
            B = beta.getIzq();
            C = gamma.getIzq();
            D = gamma.getDer();                                                
            
            alfa.setIzq(A);            
            colgar(alfa, A);
            alfa.setDer(B);
            colgar(alfa, B);
            
            gamma.setIzq(C);
            colgar(gamma, C);
            gamma.setDer(D);
            colgar(gamma, D);
            
            beta.cuelga(alfa);
            beta.cuelga(gamma);
            
            if(papa != null)
                papa.cuelga(beta);
            else{
                beta.setPadre(null);
                root = beta;
            }            
            
            
            alfa.setFacEq(getHeight(alfa.getDer()) - getHeight(alfa.getIzq()));
            beta.setFacEq(getHeight(beta.getDer()) - getHeight(beta.getIzq()));
            gamma.setFacEq(getHeight(gamma.getDer()) - getHeight(gamma.getIzq()));
    }        
    private void izqIzq(NodoAVL<T> N){
         NodoAVL<T> alfa, beta, papa, gamma, A,B,C, D;
         
        papa = N.getPadre();

        alfa = N;            
        beta = N.getIzq();
        gamma = beta.getIzq();
        A = gamma.getIzq();
        B = gamma.getDer();
        C = beta.getDer();
        D = alfa.getDer();

        gamma.setIzq(A);
        colgar(gamma, A);
        gamma.setDer(B);
        colgar(gamma, B);

        alfa.setIzq(C);
        colgar(alfa, C);
        alfa.setDer(D);
        colgar(alfa, D);

        beta.cuelga(alfa);
        beta.cuelga(gamma);

        if(papa != null)
            papa.cuelga(beta);            
        else{
            beta.setPadre(null);
            root = beta;
        }
        
        alfa.setFacEq(getHeight(alfa.getDer()) - getHeight(alfa.getIzq()));
        beta.setFacEq(getHeight(beta.getDer()) - getHeight(beta.getIzq()));
        gamma.setFacEq(getHeight(gamma.getDer()) - getHeight(gamma.getIzq()));                               
            
    }
    private void izqDer(NodoAVL<T> N){
        NodoAVL<T> alfa, beta, papa, gamma, A,B,C, D;        
        papa = N.getPadre();

        alfa = N;            
        beta = N.getIzq();
        gamma = beta.getDer();
        A = beta.getIzq();
        B = gamma.getIzq();
        C = gamma.getDer();
        D = alfa.getDer();

        beta.setIzq(A);
        colgar(beta, A);
        beta.setDer(B);
        colgar(beta, B);

        alfa.setIzq(C);
        colgar(alfa, C);
        alfa.setDer(D);
        colgar(alfa, D);

        gamma.cuelga(beta);
        gamma.cuelga(alfa);

        if(papa != null)            
            papa.cuelga(gamma);
        else{
            gamma.setPadre(null);
            root = gamma;
        }
        
        alfa.setFacEq(getHeight(alfa.getDer()) - getHeight(alfa.getIzq()));
        beta.setFacEq(getHeight(beta.getDer()) - getHeight(beta.getIzq()));
        gamma.setFacEq(getHeight(gamma.getDer()) - getHeight(gamma.getIzq()));
    }
    private void derIzq(NodoAVL<T> N) {
        NodoAVL<T> alfa, beta, papa, gamma, A,B,C, D;        
        papa = N.getPadre();

        alfa = N;            
        beta = N.getDer();
        gamma = beta.getIzq();
        A = alfa.getIzq();
        B = gamma.getIzq();
        C = gamma.getDer();
        D = beta.getDer();

        beta.setIzq(C);
        colgar(beta, C);
        beta.setDer(D);
        colgar(beta, D);

        alfa.setIzq(A);
        colgar(alfa, A);
        alfa.setDer(B);
        colgar(alfa, B);

        gamma.cuelga(beta);
        gamma.cuelga(alfa);

        if(papa != null){
            papa.cuelga(gamma);            
        }else{
            gamma.setPadre(null);
            root = gamma;
        }
        alfa.setFacEq(getHeight(alfa.getDer()) - getHeight(alfa.getIzq()));
        beta.setFacEq(getHeight(beta.getDer()) - getHeight(beta.getIzq()));
        gamma.setFacEq(getHeight(gamma.getDer()) - getHeight(gamma.getIzq()));
    }
    
    private void colgar(NodoAVL<T> N, NodoAVL<T> M){        
        if(M != null){
            M.setPadre(N);
        }
    }  
    
    public NodoAVL addI(T elem){
        if(root == null){ 
            root = new NodoAVL((Comparable) elem);
            return root;
        }
        NodoAVL aux = root, pa =null;
        while(aux != null){
            pa = aux;
            if(aux.getElem().compareTo(elem) < 0)
                aux = aux.getDer();
            else
                aux = aux.getIzq();
        }
        aux = new NodoAVL((Comparable) elem);
        pa.cuelga(aux);        
        return aux;
    }

    @Override
    public void add(T elem) {
        addI(elem);
    }

    @Override
    public boolean contains(Object elem) {
        NodoAVL a = (NodoAVL) find(elem);
        return a != null;
    }

    @Override
    public Object find(Object elem) {
        NodoAVL N = root;
        boolean encontre = false;
        while(!encontre && N != null){
            if(N.getElem().compareTo(elem) == 0)
                encontre = true;
            else if(N.getElem().compareTo(elem) < 0)
                N = N.getDer();
            else
                N = N.getIzq();
        }
        return N;
    }
    
    @Override
    public Iterator<T> preOrder() {
        ArrayList<T> lista = new ArrayList<T>();
        preOrder(root, lista);
        return lista.iterator();
    }    

    private void preOrder(NodoAVL r, ArrayList<T> lista) {
        if(r == null) return;
        lista.add((T) r.getElem());
        preOrder(r.getIzq(), lista);
        preOrder(r.getDer(), lista);
        
    }
    
    private void preOrderPila(NodoAVL r, ArrayList<T> lista) {
        Stack<NodoAVL> pila = new Stack();
        pila.push(r);
        while(!pila.isEmpty()){
            NodoAVL act = pila.pop();
            lista.add((T) act.getElem());
            if(act.getDer() != null)
                pila.push(act.getDer());
            if(act.getIzq() != null)
                pila.push(act.getIzq());            
        }
        
    }
    
    @Override
    public Iterator<T> postOrder() {
        ArrayList<T> lista = new ArrayList<T>();
        postOrder(root, lista);
        return lista.iterator();
    }
    private void postOrder(NodoAVL r, ArrayList<T> lista) {
        if(r == null) return;        
        postOrder(r.getIzq(), lista);
        postOrder(r.getDer(), lista);
        lista.add((T) r.getElem());
    }
    
    @Override
    public Iterator<T> inOrder() {
        ArrayList<T> lista = new ArrayList<T>();
        inOrder(root, lista);
        return lista.iterator();        
    }
    
    private void inOrder(NodoAVL r, ArrayList<T> lista) {
        if(r == null) return;        
        inOrder(r.getIzq(), lista);
        lista.add((T) r.getElem());
        lista.add((T)(Integer)r.getFacEq());
        inOrder(r.getDer(), lista);        
    }
    
    public Iterator<T> levelOrder(){
        ArrayList<T> lista = new ArrayList<T>();
        levelOrder(root, lista);
        return lista.iterator();        
    }        

    private void levelOrder(NodoAVL r, ArrayList<T> lista) {
        Queue<NodoAVL> cola = new LinkedList();
        cola.add(r);
        while(!cola.isEmpty()){
            NodoAVL act = cola.remove();
            String res = "Elem: " + act.getElem().toString() + " Fac. Eq: " + act.getFacEq();
            lista.add((T) res);
            if(act.getIzq() != null)
                cola.add(act.getIzq()); 
            if(act.getDer() != null)
                cola.add(act.getDer());                       
        }
    }

    
    
    
}
