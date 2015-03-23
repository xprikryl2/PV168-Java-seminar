/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moviemanager;

/**
 * Class indicating internal application exception.
 * @author Lukáš Šrom
 * @date 2015 3 6
 */
public class ServiceFailureException extends RuntimeException {
    public ServiceFailureException (){
        super();
    }
    
    public ServiceFailureException (String msg){
        super(msg);
    }
    
    public ServiceFailureException (Throwable cause){
        super(cause);
    }
    
    public ServiceFailureException (String msg, Throwable cause){
        super(msg, cause);
    }
}
