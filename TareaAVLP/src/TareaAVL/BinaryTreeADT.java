/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;

import java.util.Iterator;


/**
 *
 * @author Yosshua Eli Cisneros Villasana 
 * 179889
 */
public interface BinaryTreeADT <T>{
    
    public boolean isEmpty();
    public int size();
    public boolean contains(T elem);
    public T find(T elem);   
    public Iterator<T> inOrder();
    public Iterator<T> preOrder();
    public Iterator<T> postOrder();
    
}
