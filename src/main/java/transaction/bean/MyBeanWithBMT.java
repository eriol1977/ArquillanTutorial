/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction.bean;

import javax.annotation.Resource;
import transaction.entity.ContaCorrente;
import transaction.exception.MyException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Francesco
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class MyBeanWithBMT implements MyBeanRemote
{

    @PersistenceContext
    private EntityManager em;
    
    @Resource
    private UserTransaction ut;

    @Override
    public ContaCorrente selecionarContaCorrente(Integer id)
    {
        return em.find(ContaCorrente.class, id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void incluirContaCorrente(ContaCorrente conta) throws MyException, Exception
    {
        ut.begin();
        em.merge(conta);
        ut.commit();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void efetuarTransferencia(ContaCorrente origem, ContaCorrente destino, Integer valor) throws MyException, Exception
    {
        ut.begin();
        try {
            origem.setSaldo(origem.getSaldo()-valor);
            em.merge(origem);
            destino.setSaldo(destino.getSaldo()+valor);
            em.merge(destino);
        } catch (Exception e) {
            ut.rollback();
            throw new MyException(e.getMessage(), "BMT");
        }
        ut.commit();
    }

}
