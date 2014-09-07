/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction.bean;

import transaction.entity.ContaCorrente;
import transaction.exception.MyException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Francesco
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MyBeanWithCMT implements MyBeanRemote
{

    @PersistenceContext
    private EntityManager em;

    @Override
    public ContaCorrente selecionarContaCorrente(Integer id)
    {
        return em.find(ContaCorrente.class, id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void incluirContaCorrente(ContaCorrente conta) throws MyException, Exception
    {
        em.merge(conta);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void efetuarTransferencia(ContaCorrente origem, ContaCorrente destino, Integer valor) throws MyException, Exception
    {
        try {
            origem.setSaldo(origem.getSaldo()-valor);
            em.merge(origem);
            destino.setSaldo(destino.getSaldo()+valor);
            em.merge(destino);
        } catch (Exception e) {
            throw new MyException(e.getMessage(),"CMT");
        }
    }

}
