/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TareaAVL;

import TareaAVL.ArbolAVL;
import java.util.Iterator;


/**
 *
 * @author Yosshua Eli Cisneros Villasana 
 * 179889
 */
public class PruebaAVL {
    public static void main(String[] args){
        ArbolAVL miA = new ArbolAVL();
        miA.insert(100);
        miA.insert(200);
        miA.insert(300);
        miA.insert(10);
        miA.insert(5);
        miA.insert(4);
        miA.insert(50);
        miA.insert(110);        
        miA.insert(115);  
        miA.insert(37);  
        miA.insert(12);  
        miA.insert(29);  
        miA.insert(1000);  
        miA.insert(73);  
        miA.insert(666);  
        miA.insert(1);  
        miA.insert(82);                 
        Iterator a = miA.levelOrder();
        while(a.hasNext()){
            System.out.println(a.next());
        }        
        
    }
    
}
