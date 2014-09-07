/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction.bean;

import transaction.entity.ContaCorrente;
import transaction.exception.MyException;
import javax.ejb.Remote;

/**
 *
 * @author Francesco
 */
@Remote
public interface MyBeanRemote
{

    ContaCorrente selecionarContaCorrente(final Integer id);

    void incluirContaCorrente(ContaCorrente conta) throws MyException, Exception;

    void efetuarTransferencia(ContaCorrente origem, ContaCorrente destino, Integer valor) throws MyException, Exception;
}
