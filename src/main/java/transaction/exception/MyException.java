/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author Francesco
 */
@ApplicationException(rollback = true)
public class MyException extends Exception
{
    private String method;
    
    public MyException(final String msg, final String method)
    {
        super(msg);
        this.method = method;
    }

    public String getMethod()
    {
        return method;
    }
    
}
